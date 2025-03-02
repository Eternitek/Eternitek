package io.teking.eternitek.core.block;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.multiblock.Multiblock;
import io.teking.eternitek.core.registry.EternitekBlocks;
import io.teking.eternitek.core.resource.MultiblockReloadListener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.BlastFurnaceScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class PrimitiveFurnaceBlock extends Block {

    public PrimitiveFurnaceBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Multiblock multiblock = MultiblockReloadListener.MULTIBLOCKS.get(EternitekCore.id("primitive_furnace"));
        if (!world.isClient) {
            if (multiblock.canUse(player, pos, world)) {
                // Open custom GUI
                player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> new BlastFurnaceScreenHandler(i, playerInventory), Text.literal("Primitive Blast Furnace")));
            }
        }
        return ActionResult.SUCCESS;
    }

}
