package io.teking.eternitek.core.block.entity;

import com.google.common.base.Predicates;
import io.teking.eternitek.core.block.transfer.pipe.connection.Connectible;
import io.teking.eternitek.core.util.pipes.ItemTransferHelper;
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

import java.util.EnumMap;

import static io.teking.eternitek.core.util.pipes.ItemTransferHelper.tryMove;

public class PipeBlockEntity extends BlockEntity implements Connectible<ItemVariant> {

    public final EnumMap<Direction, Storage<ItemVariant>> storages = new EnumMap<>(Direction.class);

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
        return !isEmpty() && (variant.isBlank() || variant.equals(storage.getResource()));
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

        for (Direction direction : pipe.storages.keySet()) {
            Storage<ItemVariant> storage = pipe.storages.get(direction);
            if (!pipe.isEmpty()) {
                if (ItemTransferHelper.tryMove(pipe.storage, storage)) return;
            } else {
                if (ItemTransferHelper.tryMove(storage, pipe.storage)) return;
            }
        }

    }
    @Override
    protected void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {

        super.readNbt(nbt, registryLookup);
        if (!nbt.contains("Storage")) return;

        SingleVariantStorage.readNbt(
                storage,
                ItemVariant.CODEC,
                ItemVariant::blank,
                nbt.getCompound("Storage"),
                registryLookup
        );

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