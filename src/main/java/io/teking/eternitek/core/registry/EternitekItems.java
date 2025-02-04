package io.teking.eternitek.core.registry;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.item.CodexItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class EternitekItems {

    public static final Item CODEX = new CodexItem(new Item.Settings());

    public static final Item CRUDE_STEEL_INGOT = new Item(new Item.Settings());

    public static void register() {

        registerItem("codex", CODEX);

        registerItem("crude_steel_ingot", CRUDE_STEEL_INGOT);

    }

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, EternitekCore.id(name), item);
    }

}
