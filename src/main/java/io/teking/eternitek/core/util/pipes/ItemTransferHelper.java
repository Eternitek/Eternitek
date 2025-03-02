package io.teking.eternitek.core.util.pipes;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class ItemTransferHelper {

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

        if (blockEntity instanceof Inventory) {
            return (Inventory) blockEntity;
        }

        return null;
    }

    public static boolean canCombine(ItemStack stack1, ItemStack stack2) {
        return ItemStack.areItemsAndComponentsEqual(stack1, stack2);
    }
}
