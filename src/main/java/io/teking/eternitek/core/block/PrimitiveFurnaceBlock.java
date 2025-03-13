package io.teking.eternitek.core.block;

import io.teking.eternitek.core.EternitekCore;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.BlastFurnaceScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PrimitiveFurnaceBlock extends BlockWithMultiblock {

    public PrimitiveFurnaceBlock(Settings settings) {
        super(EternitekCore.id("primitive_furnace"), settings);
    }

    @Override
    protected void useMultiblock(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        player.openHandledScreen(new SimpleNamedScreenHandlerFactory(
                (i, playerInventory, playerEntity) -> new BlastFurnaceScreenHandler(i, playerInventory),
                Text.literal("Primitive Blast Furnace")
        ));
    }

}
