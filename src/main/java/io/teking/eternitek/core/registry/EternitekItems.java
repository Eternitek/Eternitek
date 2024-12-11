package io.teking.eternitek.core.registry;

import io.teking.eternitek.core.EternitekCore;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class EternitekItems {

    public static final Item CRUDE_STEEL_INGOT = new Item(new Item.Settings());

    public static void register() {

        registerItem("crude_steel_ingot", CRUDE_STEEL_INGOT);

    }

    public static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, EternitekCore.id(name), item);
    }

}
