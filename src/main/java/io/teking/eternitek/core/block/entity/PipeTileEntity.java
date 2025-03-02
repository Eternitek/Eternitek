package io.teking.eternitek.core.block.entity;

import io.teking.eternitek.core.inventory.PipeInventory;
import io.teking.eternitek.core.registry.EternitekBlockEntities;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class PipeTileEntity extends BlockEntity {
    private final PipeInventory inventory = new PipeInventory();

    public PipeTileEntity(BlockPos pos, BlockState state) {
        super(EternitekBlockEntities.STONE_PIPE_BLOCK_ENTITY, pos, state);
    }

    public void tick() {
        if (world.isClient()) return;

        ItemStack stack = inventory.getStack(0);
        if (!stack.isEmpty()) {
            Direction[] directions = Direction.values();
            for (Direction direction : directions) {
                BlockPos targetPos = pos.offset(direction);
                BlockEntity targetEntity = world.getBlockEntity(targetPos);
                if (targetEntity instanceof Inventory) {
                    Inventory targetInventory = (Inventory) targetEntity;
                    for (int i = 0; i < targetInventory.size(); i++) {
                        if (targetInventory.getStack(i).isEmpty()) {
                            targetInventory.setStack(i, stack.copy());
                            inventory.removeStack(0);
                            markDirty();
                            return;
                        }
                    }
                }
            }
        }
    }
}

