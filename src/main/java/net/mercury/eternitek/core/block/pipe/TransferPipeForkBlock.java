package net.mercury.eternitek.core.block.pipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.pathfinder.PathComputationType;

public class TransferPipeForkBlock extends PipeBlock {
    public TransferPipeForkBlock(Properties properties) {
        super(6.0f, properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(NORTH, false).setValue(EAST, false).setValue(SOUTH, false).setValue(WEST, false).setValue(UP, false).setValue(DOWN, false));
    }

    public static final MapCodec<TransferPipeForkBlock> CODEC = simpleCodec(TransferPipeForkBlock::new);

    public MapCodec<TransferPipeForkBlock> codec() {
        return CODEC;
    }

    protected void createBlockStateDefinition(final StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
    }

    protected boolean isPathfindable(final BlockState state, final PathComputationType type) {
        return false;
    }
}
