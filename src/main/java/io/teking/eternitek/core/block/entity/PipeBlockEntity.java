package io.teking.eternitek.core.block.entity;

import io.teking.eternitek.core.block.transfer.pipe.connection.Connectible;
import io.teking.eternitek.core.block.transfer.pipe.connection.Source;
import io.teking.eternitek.core.block.transfer.pipe.connection.Target;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class PipeBlockEntity extends BlockEntity implements Connectible<ItemVariant> {
    private final SingleVariantStorage<ItemVariant> storage;
    private static final long MAX_AMOUNT = 64; // One stack
    private static final long TRANSFER_RATE = 8; // Items per tick

    public PipeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        this.storage = new SingleVariantStorage<>() {
            @Override
            protected ItemVariant getBlankVariant() {
                return ItemVariant.blank();
            }

            @Override
            protected long getCapacity(ItemVariant variant) {
                return MAX_AMOUNT;
            }

            @Override
            protected void onFinalCommit() {
                markDirty();
            }
        };
    }

    @Override
    public boolean canExtract(ItemVariant variant) {
        return !isEmpty() && storage.getAmount() > 0 &&
                (variant.isBlank() || variant.equals(storage.getResource()));
    }

    @Override
    public boolean canInsert(ItemVariant variant) {
        return storage.getAmount() < MAX_AMOUNT &&
                (storage.getResource().isBlank() || storage.getResource().equals(variant));
    }

    @Override
    public long extract(long amount) {
        try (Transaction transaction = Transaction.openOuter()) {
            long extracted = storage.extract(storage.getResource(), Math.min(amount, TRANSFER_RATE), transaction);
            transaction.commit();
            return extracted;
        }
    }

    @Override
    public long insert(long amount) {
        try (Transaction transaction = Transaction.openOuter()) {
            long inserted = storage.insert(storage.getResource(), Math.min(amount, TRANSFER_RATE), transaction);
            transaction.commit();
            return inserted;
        }
    }

    @Override
    public boolean isPushing() {
        return !isEmpty();
    }

    @Override
    public boolean isEmpty() {
        return storage.getAmount() <= 0;
    }

    public static void tick(World world, BlockPos pos, BlockState state, PipeBlockEntity pipe) {
        if (world.isClient) return;

        // First try to output items if we have any
        if (!pipe.isEmpty()) {
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.offset(direction);
                BlockEntity neighbor = world.getBlockEntity(neighborPos);
                if (neighbor == null) continue;

                Storage<ItemVariant> outputStorage = null;

                // Get the appropriate storage for output
                if (neighbor instanceof InventoryProvider provider) {
                    SidedInventory inventory = provider.getInventory(world.getBlockState(neighborPos), world, neighborPos);
                    if (inventory.canInsert(0, pipe.storage.getResource().toStack(), direction.getOpposite())) {
                        outputStorage = InventoryStorage.of(inventory, direction.getOpposite());
                    }
                } else if (neighbor instanceof Inventory inventory) {
                    outputStorage = InventoryStorage.of(inventory, direction.getOpposite());
                }

                if (outputStorage != null) {
                    try (Transaction transaction = Transaction.openOuter()) {
                        long moved = StorageUtil.move(
                                pipe.storage,
                                outputStorage,
                                variant -> true,
                                TRANSFER_RATE,
                                transaction
                        );
                        if (moved > 0) {
                            transaction.commit();
                            return; // Exit after successful transfer
                        }
                        transaction.abort();
                    }
                }
            }
        }
        // Try to input items only if we're empty
        else {
            for (Direction direction : Direction.values()) {
                BlockPos neighborPos = pos.offset(direction);
                BlockEntity neighbor = world.getBlockEntity(neighborPos);
                if (neighbor == null) continue;

                Storage<ItemVariant> inputStorage = null;

                // Get the appropriate storage for input
                if (neighbor instanceof InventoryProvider provider) {
                    SidedInventory inventory = provider.getInventory(world.getBlockState(neighborPos), world, neighborPos);
                    if (inventory.canExtract(0, inventory.getStack(0), direction.getOpposite())) {
                        inputStorage = InventoryStorage.of(inventory, direction.getOpposite());
                    }
                } else if (neighbor instanceof Inventory inventory) {
                    inputStorage = InventoryStorage.of(inventory, direction.getOpposite());
                }

                if (inputStorage != null) {
                    try (Transaction transaction = Transaction.openOuter()) {
                        // Only move if the source has items
                        for (StorageView<ItemVariant> view : inputStorage) {
                            if (!view.isResourceBlank() && view.getAmount() > 0) {
                                long moved = StorageUtil.move(
                                        inputStorage,
                                        pipe.storage,
                                        variant -> true,
                                        TRANSFER_RATE,
                                        transaction
                                );
                                if (moved > 0) {
                                    transaction.commit();
                                    return; // Exit after successful transfer
                                }
                                break; // Break if we couldn't move from this slot
                            }
                        }
                        transaction.abort();
                    }
                }
            }
        }
    }

    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);
        if (nbt.contains("Storage")) {
            SingleVariantStorage.readNbt(
                    storage,
                    ItemVariant.CODEC,
                    ItemVariant::blank,
                    nbt.getCompound("Storage"),
                    registryLookup
            );
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);
        NbtCompound storageNbt = new NbtCompound();
        SingleVariantStorage.writeNbt(
                storage,
                ItemVariant.CODEC,
                storageNbt,
                registryLookup
        );
        nbt.put("Storage", storageNbt);
    }

    public SingleVariantStorage<ItemVariant> getStorage() {
        return storage;
    }

}