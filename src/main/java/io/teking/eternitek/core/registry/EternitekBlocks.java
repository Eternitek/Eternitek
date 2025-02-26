package io.teking.eternitek.core.registry;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.block.MachineCoreBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class EternitekBlocks {

    public static final Block MACHINE_CORE = new MachineCoreBlock(AbstractBlock.Settings.create());
    public static final Block BLAST_BRICKS = new Block(AbstractBlock.Settings.create());

    public static void register() {

        registerBlockWithItem("machine_core", MACHINE_CORE);
        registerBlockWithItem("blast_bricks", BLAST_BRICKS);
    }

    public static void registerBlock(String name, Block block) {
        Registry.register(Registries.BLOCK, EternitekCore.id(name), block);
    }

    public static void registerBlockWithItem(String name, Block block) {
        EternitekItems.registerItem(name, new BlockItem(block, new Item.Settings()));
        registerBlock(name, block);
    }

}
