package io.teking.eternitek.core.multiblock;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Map;
import java.util.function.Predicate;

public class Multiblock {

    private char[][][] pattern;
    private final Map<Character, BlockState> states;
    private final int width;
    private final int height;
    private final int length;

    public Multiblock(char[][][] pattern, Map<Character, BlockState> states) {
        this.pattern = pattern;
        this.states = states;
        this.width = pattern[0].length;
        this.height = pattern.length;
        this.length = pattern[0][0].length;
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

        MinecraftClient.getInstance().getBlockRenderManager().renderBlock();

        for(int i = 0; i < pattern.length; i++) {
            for(int j = 0; j < pattern[i].length; j++) {
                for(int k = 0; k < pattern[i][j].length; k++) {
                    BlockPos placePos = corner.add(j, i, k);
                    BlockState placeState = states.get(pattern[i][j][k]);

                    world.setBlockState(placePos, placeState);
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
