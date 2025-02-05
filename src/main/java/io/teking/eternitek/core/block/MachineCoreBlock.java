package io.teking.eternitek.core.block;

import io.teking.eternitek.core.multiblock.Multiblock;
import io.teking.eternitek.core.registry.EternitekBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class MachineCoreBlock extends Block {

    private static char[][][] blocks;
    private static Map<Character, BlockState> map;

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
        map.put('x', Blocks.IRON_BLOCK.getDefaultState());
        map.put('*', EternitekBlocks.MACHINE_CORE.getDefaultState());
        map.put('o', Blocks.OAK_PLANKS.getDefaultState());

        Multiblock multiblock = new Multiblock(blocks, map);
        multiblock.place(pos, world);

        super.onPlaced(world, pos, state, placer, itemStack);

    }

}
