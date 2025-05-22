package io.teking.eternitek.core.block.entity;

import io.teking.eternitek.core.block.TransferTerminalBlock;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class TransferTerminalBlockEntity extends BlockEntity {
    private final SingleVariantStorage<ItemVariant> storage;
    private static final int TRANSFER_RATE = 8;

    public TransferTerminalBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        this.storage = new SingleVariantStorage<ItemVariant>() {
            @Override
            protected ItemVariant getBlankVariant() {
                return ItemVariant.blank();
            }

            @Override
            protected long getCapacity(ItemVariant variant) {
                return 64;
            }

            @Override
            protected void onFinalCommit() {
                markDirty();
            }
        };
    }

    public static void tick(World world, BlockPos pos, BlockState state, TransferTerminalBlockEntity be) {
        if (world.isClient) return;

        boolean isOutput = state.get(TransferTerminalBlock.MODE);
        Direction facing = state.get(TransferTerminalBlock.FACING);

        if (!isOutput) { // INPUT MODE
            // Try to input items only if we're empty
            if (be.storage.isResourceBlank()) {
                BlockPos targetPos = pos.offset(facing);
                BlockEntity neighbor = world.getBlockEntity(targetPos);
                if (neighbor == null) return;

                Storage<ItemVariant> inputStorage = null;

                // Get the appropriate storage for input
                if (neighbor instanceof InventoryProvider provider) {
                    SidedInventory inventory = provider.getInventory(world.getBlockState(targetPos), world, targetPos);
                    if (inventory.canExtract(0, inventory.getStack(0), facing.getOpposite())) {
                        inputStorage = InventoryStorage.of(inventory, facing.getOpposite());
                    }
                } else if (neighbor instanceof Inventory inventory) {
                    inputStorage = InventoryStorage.of(inventory, facing.getOpposite());
                }

                if (inputStorage != null) {
                    try (Transaction transaction = Transaction.openOuter()) {
                        // Only move if the source has items
                        for (StorageView<ItemVariant> view : inputStorage) {
                            if (!view.isResourceBlank() && view.getAmount() > 0) {
                                long moved = StorageUtil.move(
                                        inputStorage,
                                        be.storage,
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

            // Try to output to pipes if we have items
            if (!be.storage.isResourceBlank()) {
                for (Direction direction : Direction.values()) {
                    if (direction == facing) continue;
                    BlockPos pipePos = pos.offset(direction);
                    BlockEntity pipeEntity = world.getBlockEntity(pipePos);

                    if (pipeEntity instanceof PipeBlockEntity pipe) {
                        try (Transaction transaction = Transaction.openOuter()) {
                            long moved = StorageUtil.move(
                                    be.storage,
                                    pipe.getStorage(),
                                    variant -> true,
                                    TRANSFER_RATE,
                                    transaction
                            );
                            if (moved > 0) {
                                transaction.commit();
                                return;
                            }
                            transaction.abort();
                        }
                    }
                }
            }
        } else { // OUTPUT MODE
            // First try to output items if we have any
            if (!be.storage.isResourceBlank()) {
                BlockPos targetPos = pos.offset(facing);
                BlockEntity neighbor = world.getBlockEntity(targetPos);
                if (neighbor == null) return;

                Storage<ItemVariant> outputStorage = null;

                // Get the appropriate storage for output
                if (neighbor instanceof InventoryProvider provider) {
                    SidedInventory inventory = provider.getInventory(world.getBlockState(targetPos), world, targetPos);
                    if (inventory.canInsert(0, be.storage.getResource().toStack(), facing.getOpposite())) {
                        outputStorage = InventoryStorage.of(inventory, facing.getOpposite());
                    }
                } else if (neighbor instanceof Inventory inventory) {
                    outputStorage = InventoryStorage.of(inventory, facing.getOpposite());
                }

                if (outputStorage != null) {
                    try (Transaction transaction = Transaction.openOuter()) {
                        long moved = StorageUtil.move(
                                be.storage,
                                outputStorage,
                                variant -> true,
                                TRANSFER_RATE,
                                transaction
                        );
                        if (moved > 0) {
                            transaction.commit();
                            return;
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
                    () -> ItemVariant.blank(),
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
}