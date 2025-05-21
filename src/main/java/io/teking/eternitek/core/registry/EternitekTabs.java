package io.teking.eternitek.core.registry;

import io.teking.eternitek.core.EternitekCore;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;

import java.util.function.Supplier;

import static io.teking.eternitek.core.registry.EternitekBlocks.BLAST_BRICKS;
import static io.teking.eternitek.core.registry.EternitekBlocks.PRIMITIVE_FURNACE;
import static io.teking.eternitek.core.registry.EternitekItems.*;

public class EternitekTabs {

    public static final RegistryKey<ItemGroup> ETERNITEK_CORE = createKey("core_group");
    public static final ItemGroup ETERNITEK_CORE_GROUP = createTab(CODEX, Text.translatable("itemGroup.eternitek_core"));

    public static void register() {

        registerTab(ETERNITEK_CORE, ETERNITEK_CORE_GROUP);
        ItemGroupEvents.modifyEntriesEvent(ETERNITEK_CORE).register(EternitekTabs::addItemsToEternitekCore);

    }

    public static void registerTab(RegistryKey<ItemGroup> key, ItemGroup tab) {
        Registry.register(Registries.ITEM_GROUP, key, tab);
    }

    public static RegistryKey<ItemGroup> createKey(String name) {
        return RegistryKey.of(Registries.ITEM_GROUP.getKey(), EternitekCore.id(name));
    }

    public static ItemGroup createTab(Item icon, Text name) {
        return createTab(icon::getDefaultStack, name);
    }

    public static ItemGroup createTab(Supplier<ItemStack> icon, Text name) {
        return FabricItemGroup.builder().icon(icon).displayName(name).build();
    }

    public static void addItemsToEternitekCore(FabricItemGroupEntries group) {

        group.add(BLAST_BRICK);
        group.add(BLAST_BRICKS);
        group.add(CODEX);
        group.add(CRUDE_STEEL_INGOT);
        group.add(PRIMITIVE_FURNACE);

    }

}
