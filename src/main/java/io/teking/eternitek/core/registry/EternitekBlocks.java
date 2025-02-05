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

    public static void register() {

        registerBlockWithItem("machine_core", MACHINE_CORE);

    }

    public static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, EternitekCore.id(name), block);
    }

    public static Block registerBlockWithItem(String name, Block block) {
        EternitekItems.registerItem(name, new BlockItem(block, new Item.Settings()));
        return registerBlock(name, block);
    }

}
