package io.teking.eternitek.core.registry;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.item.CodexItem;
import io.teking.eternitek.core.item.MiningHammerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.function.Function;

public class EternitekItems {

    public static final Item CODEX = registerItem("codex", CodexItem::new);

    public static final Item BLAST_BRICK = registerItem("blast_brick", Item::new);
    public static final Item CRUDE_STEEL_INGOT = registerItem("crude_steel_ingot", Item::new);

    public static final Item DIAMOND_HAMMER = registerItem("diamond_mining_hammer", settings -> new MiningHammerItem(
            ToolMaterials.DIAMOND,
            settings,
            1, 0, 1 // 3x3x1 mining area
    ));

    public static final Item NETHERITE_HAMMER = registerItem("netherite_mining_hammer", settings -> new MiningHammerItem(
            ToolMaterials.NETHERITE,
            new Item.Settings().fireproof(),
            1, 1, 1 // 3x3x3 mining area
    );

    public static void register() {

    }

    public static Item registerItem(String name, Function<Item.Settings, Item> factory) {
        return Registry.register(Registries.ITEM, EternitekCore.id(name), factory.apply(new Item.Settings()));
    }

}
