package io.teking.eternitek.core.block.pipe;

import com.mojang.serialization.MapCodec;
import io.teking.eternitek.core.block.entity.PipeControllerBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PipeControllerBlock extends BlockWithEntity {
    public PipeControllerBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return null;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof NamedScreenHandlerFactory) {
                player.openHandledScreen((NamedScreenHandlerFactory) blockEntity);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PipeControllerBlockEntity(pos, state);
    }
}
