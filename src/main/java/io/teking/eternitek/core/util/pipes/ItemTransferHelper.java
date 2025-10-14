package io.teking.eternitek.core.util.pipes;

import com.google.common.base.Predicates;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageUtil;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ItemTransferHelper {

    public static final long TRANSFER_RATE = 8;

    public static boolean transferItem(Inventory sourceInventory, Inventory targetInventory, int sourceSlot, int maxAmount) {
        ItemStack sourceStack = sourceInventory.getStack(sourceSlot);
        if (sourceStack.isEmpty()) {
            return false;
        }

        ItemStack transferStack = sourceStack.copy();
        transferStack.setCount(Math.min(maxAmount, sourceStack.getCount()));

        for (int i = 0; i < targetInventory.size(); i++) {
            ItemStack targetStack = targetInventory.getStack(i);
            if (targetStack.isEmpty()) {
                targetInventory.setStack(i, transferStack);
                sourceInventory.removeStack(sourceSlot, transferStack.getCount());
                return true;
            } else if (canCombine(targetStack, transferStack)) {
                int space = targetStack.getMaxCount() - targetStack.getCount();
                int transferAmount = Math.min(space, transferStack.getCount());
                targetStack.increment(transferAmount);
                sourceInventory.removeStack(sourceSlot, transferAmount);
                return transferAmount > 0;
            }
        }

        return false;
    }


    public static Inventory getInventoryAt(World world, BlockPos pos, Direction side) {
        BlockPos targetPos = pos.offset(side);
        BlockEntity blockEntity = world.getBlockEntity(targetPos);

        if (blockEntity instanceof Inventory inventory) {
            return inventory;
        } else if (blockEntity instanceof InventoryProvider provider) {
            return provider.getInventory(world.getBlockState(targetPos), world, targetPos);
        }

        return null;
    }

    public static <T> boolean tryMove(Storage<T> from, Storage<T> to) {
        if (from == null || to == null) return false;
        try (Transaction transaction = Transaction.openOuter()) {
            long moved = StorageUtil.move(
                    from,
                    to,
                    Predicates.alwaysTrue(),
                    TRANSFER_RATE,
                    transaction
            );
            if (moved > 0) {
                transaction.commit();
                return true;
            }
            transaction.abort();
        }
        return false;
    }


    public static boolean canCombine(ItemStack stack1, ItemStack stack2) {
        return ItemStack.areItemsAndComponentsEqual(stack1, stack2);
    }

}
