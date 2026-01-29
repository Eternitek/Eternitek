package net.mercury.eternitek.core.registry;

import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.item.CodexItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class EternitekItems {

    public static final List<Item> GENERATE = new ArrayList<>();

    public static final Item CODEX = registerItem("codex", CodexItem::new, true);

    public static void register() {

    }

    public static Item registerItem(String name, Function<Item.Properties, Item> factory, boolean model) {
        ResourceKey<Item> key = EternitekCore.key(Registries.ITEM, name);
        Item item = Registry.register(
                BuiltInRegistries.ITEM,
                key,
                factory.apply(new Item.Properties().setId(key))
        );
        GENERATE.add(item);
        return item;
    }

}
