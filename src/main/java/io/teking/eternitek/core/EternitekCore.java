package io.teking.eternitek.core;

import io.teking.eternitek.core.registry.EternitekBlockEntities;
import io.teking.eternitek.core.registry.EternitekBlocks;
import io.teking.eternitek.core.registry.EternitekItems;
import io.teking.eternitek.core.registry.EternitekTabs;
import io.teking.eternitek.core.resource.MultiblockReloadListener;
import io.teking.eternitek.core.util.fun.Facts;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.resource.ResourceType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EternitekCore implements ModInitializer {

    public static final String NAME = "Eternitek Core";
    public static final String MOD_ID = "eternitek";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    @Override
    public void onInitialize() {

        Facts.register();

        LOGGER.info(Facts.getRandom());

        EternitekBlockEntities.register();
        EternitekBlocks.register();
        EternitekItems.register();
        EternitekTabs.register();


        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(
                EternitekCore.id("multiblock"),
                MultiblockReloadListener::new
        );

        LOGGER.info("Successfully loaded");

    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

}
