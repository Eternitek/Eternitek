package io.teking.eternitek.core.block;

import io.teking.eternitek.core.multiblock.Multiblock;
import io.teking.eternitek.core.resource.MultiblockReloadListener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class BlockWithMultiblock extends BlockWithEntity {

    protected final Identifier multiblock;

    protected BlockWithMultiblock(Identifier multiblock, Settings settings) {
        super(settings);
        this.multiblock = multiblock;
    }

    public Multiblock getMultiblock() {
        return MultiblockReloadListener.MULTIBLOCKS.get(multiblock);
    }

    protected abstract void useMultiblock(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit);

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if(!world.isClient && this.getMultiblock().canUse(player, pos, world)) useMultiblock(state, world, pos, player, hit);
        return ActionResult.SUCCESS;
    }

    public static class Entity extends BlockEntity {

        public Entity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
            super(type, pos, state);
        }
    }

}
