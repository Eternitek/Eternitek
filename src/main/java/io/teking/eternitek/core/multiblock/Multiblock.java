package io.teking.eternitek.core.multiblock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;
import java.util.function.Predicate;

public class Multiblock {

    private char[][][] pattern;
    private final Map<Character, Predicate<BlockState>> stateCheckers;
    private final int width;
    private final int height;
    private final int length;

    public Multiblock(char[][][] pattern, Map<Character, Predicate<BlockState>> stateCheckers) {
        this.pattern = pattern;
        this.stateCheckers = stateCheckers;
        this.height = pattern.length;
        this.width = pattern[0].length;
        this.length = pattern[0][0].length;
    }

    public boolean isValid(BlockPos pos, World world) {
        for (int y = 0; y < height; y++) {
            for (int z = 0; z < length; z++) {
                for (int x = 0; x < width; x++) {
                    char c = pattern[y][z][x];
                    BlockPos checkPos = pos.add(x, y, z);
                    BlockState state = world.getBlockState(checkPos);

                    System.out.println("Checking block at " + checkPos + ": expected '" + c + "', found " + state);

                    if (c == ' ') {
                        if (!state.isAir()) {
                            System.out.println("Invalid block at " + checkPos + ". Expected air, found " + state);
                            return false;
                        }
                        continue;
                    }

                    Predicate<BlockState> checker = stateCheckers.get(c);
                    if (checker == null || !checker.test(state)) {
                        System.out.println("Invalid block at " + checkPos + ". Expected '" + c + "', found " + state);
                        return false;
                    }
                }
            }
        }
        return true;
    }




    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getLength() {
        return length;
    }

    public void place(BlockPos pos, World world) {
        BlockPos corner = offset(pos);
        if(corner == null) return;

        for(int i = 0; i < pattern.length; i++) {
            for(int j = 0; j < pattern[i].length; j++) {
                for(int k = 0; k < pattern[i][j].length; k++) {
                    BlockPos placePos = corner.add(j, i, k);
                    char c = pattern[i][j][k];
                    Predicate<BlockState> checker = stateCheckers.get(c);

                    if (checker != null) {
                        // Find a matching BlockState
                        for (Block block : Registries.BLOCK) {
                            BlockState state = block.getDefaultState();
                            if (checker.test(state)) {
                                world.setBlockState(placePos, state);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public BlockPos offset(BlockPos pos) {
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                for(int k = 0; k < length; k++) {
                    if(pattern[i][j][k] == '*') {
                        return pos.add(-j, -i, -k);
                    }
                }
            }
        }
        return null;
    }

}
