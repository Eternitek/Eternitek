package io.teking.eternitek.core.registry;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.block.MachineCoreBlock;
import io.teking.eternitek.core.block.PrimativeFurnaceBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.NoteBlockInstrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;


public class EternitekBlocks {

    public static final Block MACHINE_CORE = new MachineCoreBlock(AbstractBlock.Settings.create());
    public static final Block BLAST_BRICKS = new Block(AbstractBlock.Settings.create());
    public static final Block PRIMITIVE_FURNACE = new PrimativeFurnaceBlock(AbstractBlock.Settings.create().mapColor(MapColor.STONE_GRAY).instrument(NoteBlockInstrument.BASEDRUM).requiresTool().strength(3.5F));

    public static void register() {

        registerBlockWithItem("machine_core", MACHINE_CORE);
        registerBlockWithItem("blast_bricks", BLAST_BRICKS);
        registerBlockWithItem("primitive_furnace", PRIMITIVE_FURNACE);
    }

    public static void registerBlock(String name, Block block) {
        Registry.register(Registries.BLOCK, EternitekCore.id(name), block);
    }

    public static void registerBlockWithItem(String name, Block block) {
        EternitekItems.registerItem(name, new BlockItem(block, new Item.Settings()));
        registerBlock(name, block);
    }

}
