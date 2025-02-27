package io.teking.eternitek.core.block;

import io.teking.eternitek.core.multiblock.Multiblock;
import io.teking.eternitek.core.registry.EternitekBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.BlastFurnaceScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class MachineCoreBlock extends Block {

    private static char[][][] blocks;
    private static Map<Character, Predicate<BlockState>> map;

    public MachineCoreBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {

        blocks = new char[][][] {
                {
                        { 'o', 'o', 'o' },
                        { 'o', 'o', 'o' },
                        { 'x', '*', 'x' },
                },
                {
                        { 'x', 'x', 'x' },
                        { 'x', 'x', 'x' },
                        { 'x', 'x', 'x' },
                }
        };

        map = new HashMap<>();
        map.put('x', blockState -> blockState.isOf(Blocks.IRON_BLOCK));
        map.put('*', blockState -> blockState.isOf(EternitekBlocks.MACHINE_CORE));
        map.put('o', blockState -> blockState.isOf(Blocks.OAK_PLANKS));

        Multiblock multiblock = new Multiblock(blocks, map);

        super.onPlaced(world, pos, state, placer, itemStack);
    }

    public boolean isValidStructure(World world, BlockPos pos) {
        Multiblock multiblock = new Multiblock(blocks, map);
        // Adjust the starting position: move one block north and don't move down
        BlockPos checkPos = pos.north();
        System.out.println("Checking structure validity starting at: " + checkPos);
        boolean isValid = multiblock.isValid(checkPos, world);
        System.out.println("Structure is " + (isValid ? "valid" : "invalid"));
        return isValid;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            if (isValidStructure(world, pos)) {
                // Open custom GUI
                player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> {
                    return new BlastFurnaceScreenHandler(i, playerInventory);
                }, Text.literal("Primitive Blast Furnace")));
            } else {
                // Structure is invalid, send a message to the player
                player.sendMessage(Text.literal("aaaaaaaaaaaaaaaaaaaaaaaaaaaaa"), false);
            }
        }
        return ActionResult.SUCCESS;
    }
}
