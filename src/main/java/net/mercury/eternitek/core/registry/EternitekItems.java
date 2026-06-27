package net.mercury.eternitek.core.registry;

import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.item.CodexItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class EternitekItems {

    public static final Item CODEX = registerItem("codex", CodexItem::new);

    public static void register() {

    }

    public static Item registerItem(String name, Function<Item.Properties, Item> factory) {
        return registerItem(name, factory, new Item.Properties());
    }

    public static Item registerItem(String name, Function<Item.Properties, Item> factory, Item.Properties properties) {
        ResourceKey<Item> key = EternitekCore.key(Registries.ITEM, name);
        return Registry.register(
                BuiltInRegistries.ITEM,
                key,
                factory.apply(properties.setId(key))
        );
    }
}
