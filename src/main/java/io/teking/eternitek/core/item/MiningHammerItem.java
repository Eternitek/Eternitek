package io.teking.eternitek.core.item;

import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class MiningHammerItem extends PickaxeItem {
    private final int rangeX;
    private final int rangeY;
    private final int rangeZ;

    public MiningHammerItem(ToolMaterial material, Settings settings, int rangeX, int rangeY, int rangeZ) {
        super(material, settings);
        this.rangeX = rangeX;
        this.rangeY = rangeY;
        this.rangeZ = rangeZ;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (!world.isClient && state.getHardness(world, pos) != 0.0F) {
            breakBlocks(world, pos, miner, stack);
        }
        return super.postMine(stack, world, state, pos, miner);
    }

    private void breakBlocks(World world, BlockPos pos, LivingEntity miner, ItemStack stack) {
        if (miner instanceof PlayerEntity player) {
            // Get the face the player is looking at, not just horizontal direction
            Direction facing = getFacingDirection(player, pos);

            // Define the mining area based on the facing direction
            for (int x = -rangeX; x <= rangeX; x++) {
                for (int y = -rangeY; y <= rangeY; y++) {
                    for (int z = -rangeZ; z <= rangeZ; z++) {
                        // Skip the original block
                        if (x == 0 && y == 0 && z == 0) continue;

                        BlockPos newPos;
                        // Properly adjust the mining area based on the player's facing direction
                        switch (facing) {
                            case UP:
                            case DOWN:
                                // If looking up/down, mine in the X-Z plane
                                newPos = pos.add(x, 0, z);
                                break;
                            case NORTH:
                            case SOUTH:
                                // If looking north/south, mine in the X-Y plane
                                newPos = pos.add(x, y, 0);
                                break;
                            case EAST:
                            case WEST:
                                // If looking east/west, mine in the Y-Z plane
                                newPos = pos.add(0, y, z);
                                break;
                            default:
                                // Should never happen, but fallback
                                newPos = pos.add(x, y, z);
                                break;
                        }
                        breakExtraBlock(world, newPos, miner, stack);
                    }
                }
            }
        }
    }

    /**
     * Determines the face the player is mining on relative to the block.
     */
    private Direction getFacingDirection(PlayerEntity player, BlockPos pos) {
        double dx = player.getX() - (pos.getX() + 0.5);
        double dy = player.getY() - (pos.getY() + 0.5);
        double dz = player.getZ() - (pos.getZ() + 0.5);
        double absX = Math.abs(dx);
        double absY = Math.abs(dy);
        double absZ = Math.abs(dz);
        // Prioritize the axis with the greatest distance
        if (absY >= absX && absY >= absZ) {
            return dy > 0 ? Direction.UP : Direction.DOWN;
        } else if (absX >= absZ) {
            return dx > 0 ? Direction.EAST : Direction.WEST;
        } else {
            return dz > 0 ? Direction.SOUTH : Direction.NORTH;
        }
    }

    private void breakExtraBlock(World world, BlockPos pos, LivingEntity miner, ItemStack stack) {
        BlockState state = world.getBlockState(pos);
        // Only break blocks that are not instantly breakable and are suitable for the tool
        if (state.getHardness(world, pos) != 0.0F && stack.isSuitableFor(state)) {
            world.breakBlock(pos, true);
            if (miner instanceof PlayerEntity player) {
                stack.damage(1, player, EquipmentSlot.MAINHAND);
            }
        }
    }
}