package io.teking.eternitek.core.block.entity;

import io.teking.eternitek.core.inventory.PipeInventory;
import io.teking.eternitek.core.registry.EternitekBlockEntities;
import io.teking.eternitek.core.util.pipes.ItemTransferHelper;
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
}

