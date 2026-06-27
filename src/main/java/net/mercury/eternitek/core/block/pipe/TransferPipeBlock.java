package net.mercury.eternitek.core.block.pipe;

import com.mojang.math.OctahedralGroup;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TransferPipeBlock extends RotatedPillarBlock {
    protected TransferPipeBlock(Properties properties) {
        super(properties);
    }

    public static final MapCodec<TransferPipeBlock> CODEC = simpleCodec(TransferPipeBlock::new);

    public MapCodec<TransferPipeBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape shape = Block.boxZ(6.0F, 0.0F, 8.0F);
        return switch (state.getValue(AXIS)) {
            case Direction.Axis.Z -> shape;
            case Direction.Axis.X -> Shapes.rotate(shape, OctahedralGroup.BLOCK_ROT_Y_90);
            case Direction.Axis.Y -> Shapes.rotate(shape, OctahedralGroup.BLOCK_ROT_X_90);
        };
    }
}
