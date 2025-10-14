package io.teking.eternitek.core.block;

import com.mojang.serialization.MapCodec;
import io.teking.eternitek.core.block.entity.PipeBlockEntity;
import io.teking.eternitek.core.registry.EternitekBlockEntities;
import io.teking.eternitek.core.util.pipes.ItemTransferHelper;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
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

        Vec3i diff = sourcePos.toImmutable().subtract(pos);
        Direction facing = Direction.fromVector(diff.getX(), diff.getY(), diff.getZ());
        Inventory neighborInventory = ItemTransferHelper.getInventoryAt(world, pos, facing);

        BlockEntity entity = world.getBlockEntity(sourcePos);
        if (!(entity instanceof PipeBlockEntity pipe)) {
            super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);
            return;
        }

        pipe.storages.put(facing, neighborInventory == null ? null : InventoryStorage.of(neighborInventory, facing));

        super.neighborUpdate(state, world, pos, sourceBlock, sourcePos, notify);

    }

}
