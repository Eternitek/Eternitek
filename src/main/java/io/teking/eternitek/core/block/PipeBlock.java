package io.teking.eternitek.core.block;

import com.mojang.serialization.MapCodec;
import io.teking.eternitek.core.block.entity.PipeBlockEntity;
import io.teking.eternitek.core.registry.EternitekBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PipeBlock extends BlockWithEntity {

    public static final MapCodec<PipeBlock> CODEC = createCodec(PipeBlock::new);

    public PipeBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PipeBlockEntity(EternitekBlockEntities.PIPE_BLOCK_ENTITY, pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(type, EternitekBlockEntities.PIPE_BLOCK_ENTITY, PipeBlockEntity::tick);
    }

    @Override
    protected void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {



        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);

    }

}
