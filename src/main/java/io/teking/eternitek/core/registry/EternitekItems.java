package io.teking.eternitek.core.registry;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.item.CodexItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class EternitekItems {

    public static final Item CODEX = new CodexItem(new Item.Settings());

    public static final Item BLAST_BRICK = new Item(new Item.Settings());
    public static final Item CRUDE_STEEL_INGOT = new Item(new Item.Settings());

    public static void register() {

        registerItem("codex", CODEX);

        registerItem("blast_brick", BLAST_BRICK);
        registerItem("crude_steel_ingot", CRUDE_STEEL_INGOT);

    }

    public static void registerItem(String name, Item item) {
        Registry.register(Registries.ITEM, EternitekCore.id(name), item);
    }

}
