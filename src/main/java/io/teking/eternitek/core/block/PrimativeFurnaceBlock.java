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
import net.minecraft.screen.ScreenHandlerContext;
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

public class PrimativeFurnaceBlock extends Block {

    private static char[][][] blocks;
    private static Map<Character, Predicate<BlockState>> map;

    static {
        blocks = new char[][][] {
                {
                        { 'b', 'b', 'b' },
                        { 'b', 'b', 'b' },
                        { 'b', 'b', 'b' },
                },
                {
                        { 'b', 'b', 'b' },
                        { 'b', ' ', 'b' },
                        { 'b', '*', 'b' },
                },
                {
                        { 'b', 'b', 'b' },
                        { 'b', ' ', 'b' },
                        { 'b', 'b', 'b' },
                },
                {
                        { 'b', 'b', 'b' },
                        { 'b', ' ', 'b' },
                        { 'b', 'b', 'b' },
                }
        };

        map = new HashMap<>();
        map.put('b', blockState -> blockState.isOf(EternitekBlocks.BLAST_BRICKS));
        map.put('*', blockState -> blockState.isOf(EternitekBlocks.PRIMITIVE_FURNACE));
        map.put(' ', BlockState::isAir);
    }

    public PrimativeFurnaceBlock(Settings settings) {
        super(settings);
    }

    public boolean isValidStructure(World world, BlockPos pos) {
        // Try all four cardinal directions
        return (
                checkStructureInDirection(world, pos, 0) || // North (default)
                        checkStructureInDirection(world, pos, 1) || // East
                        checkStructureInDirection(world, pos, 2) || // South
                        checkStructureInDirection(world, pos, 3)    // West
        );
    }

    private boolean checkStructureInDirection(World world, BlockPos pos, int rotation) {
        // Find the position of the '*' in our pattern
        int centerX = -1, centerY = -1, centerZ = -1;
        for (int y = 0; y < blocks.length; y++) {
            for (int z = 0; z < blocks[y].length; z++) {
                for (int x = 0; x < blocks[y][z].length; x++) {
                    if (blocks[y][z][x] == '*') {
                        centerY = y;
                        centerZ = z;
                        centerX = x;
                        break;
                    }
                }
                if (centerX != -1) break;
            }
            if (centerX != -1) break;
        }

        // Direction names for logging
        String[] dirNames = {"North", "East", "South", "West"};
        System.out.println("Checking structure validity for direction: " + dirNames[rotation]);

        // Calculate the origin based on rotation
        BlockPos origin;
        switch (rotation) {
            case 0: // North (default)
                origin = pos.add(-centerX, -centerY, -centerZ);
                break;
            case 1: // East (rotate 90° clockwise)
                origin = pos.add(-centerZ, -centerY, centerX - (blocks[0][0].length - 1));
                break;
            case 2: // South (rotate 180°)
                origin = pos.add(centerX - (blocks[0][0].length - 1), -centerY, centerZ - (blocks[0].length - 1));
                break;
            case 3: // West (rotate 270° clockwise)
                origin = pos.add(centerZ - (blocks[0].length - 1), -centerY, -centerX);
                break;
            default:
                return false;
        }

        System.out.println("Origin for check: " + origin);

        // Check the structure with rotation
        for (int y = 0; y < blocks.length; y++) {
            for (int z = 0; z < blocks[y].length; z++) {
                for (int x = 0; x < blocks[y][z].length; x++) {
                    char expected = blocks[y][z][x];
                    if (expected == '*') continue; // Skip the furnace position

                    // Calculate rotated position
                    BlockPos checkPos;
                    switch (rotation) {
                        case 0: // North (default)
                            checkPos = origin.add(x, y, z);
                            break;
                        case 1: // East
                            checkPos = origin.add(z, y, blocks[0][0].length - 1 - x);
                            break;
                        case 2: // South
                            checkPos = origin.add(blocks[0][0].length - 1 - x, y, blocks[0].length - 1 - z);
                            break;
                        case 3: // West
                            checkPos = origin.add(blocks[0].length - 1 - z, y, x);
                            break;
                        default:
                            return false;
                    }

                    BlockState state = world.getBlockState(checkPos);

                    // For debugging
                    if (y == 0 && (x == 0 || x == blocks[0][0].length - 1) && (z == 0 || z == blocks[0].length - 1)) {
                        System.out.println("Corner check at " + checkPos + ": expected '" + expected + "', found " + state);
                    }

                    if (expected == ' ') {
                        if (!state.isAir()) {
                            System.out.println("Invalid block at " + checkPos + ". Expected air, found " + state);
                            return false;
                        }
                    } else {
                        Predicate<BlockState> checker = map.get(expected);
                        if (checker == null || !checker.test(state)) {
                            System.out.println("Invalid block at " + checkPos + ". Expected '" + expected + "', found " + state);
                            return false;
                        }
                    }
                }
            }
        }

        System.out.println("Structure is valid for direction: " + dirNames[rotation]);
        return true;
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
                player.sendMessage(Text.literal("Invalid primitive blast furnace structure"), false);
            }
        }
        return ActionResult.SUCCESS;
    }

}
