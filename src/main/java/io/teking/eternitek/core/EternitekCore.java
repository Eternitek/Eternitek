package io.teking.eternitek.core;

import io.teking.eternitek.core.registry.*;
import io.teking.eternitek.core.resource.MultiblockReloadListener;
import io.teking.eternitek.core.resource.TechTreeReloadListener;
import io.teking.eternitek.core.util.fun.Facts;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
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
        EternitekRegistries.register();
        EternitekTabs.register();

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(
                EternitekCore.id("multiblock"),
                MultiblockReloadListener::new
        );

        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(
                EternitekCore.id("techtree"),
                TechTreeReloadListener::new
        );

        LOGGER.info("Successfully loaded");

    }

    public static Identifier id(String path) {
        return Identifier.of(MOD_ID, path);
    }

}
