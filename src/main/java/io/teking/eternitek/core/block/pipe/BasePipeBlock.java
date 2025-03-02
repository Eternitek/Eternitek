package io.teking.eternitek.core.block.pipe;

import io.teking.eternitek.core.block.entity.PipeTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class BasePipeBlock extends Block implements BlockEntityProvider {
    public BasePipeBlock(Settings settings) {
        super(settings);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PipeTileEntity(pos, state);
    }
}

