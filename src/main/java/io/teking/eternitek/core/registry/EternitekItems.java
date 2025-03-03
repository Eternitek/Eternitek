package io.teking.eternitek.core.registry;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.item.CodexItem;
import io.teking.eternitek.core.item.MiningHammerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class EternitekItems {

    public static final Item CODEX = new CodexItem(new Item.Settings());

    public static final Item BLAST_BRICK = new Item(new Item.Settings());
    public static final Item CRUDE_STEEL_INGOT = new Item(new Item.Settings());

    public static final Item DIAMOND_HAMMER = new MiningHammerItem(
            ToolMaterials.DIAMOND,
            new Item.Settings(),
            1, 1, 0 // 3x3x1 mining area
    );

    public static final Item NETHERITE_HAMMER = new MiningHammerItem(
            ToolMaterials.NETHERITE,
            new Item.Settings().fireproof(),
            1, 1, 1 // 3x3x3 mining area
    );

    public static void register() {

        registerItem("codex", CODEX);

        registerItem("blast_brick", BLAST_BRICK);
        registerItem("crude_steel_ingot", CRUDE_STEEL_INGOT);
        registerItem("diamond_mining_hammer", DIAMOND_HAMMER);
        registerItem("netherite_mining_hammer", NETHERITE_HAMMER);

    }

    public static void registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, EternitekCore.id(name), item);
    }

}
