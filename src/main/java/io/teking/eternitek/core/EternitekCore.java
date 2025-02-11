package io.teking.eternitek.core;

import io.teking.eternitek.core.registry.EternitekBlocks;
import io.teking.eternitek.core.registry.EternitekItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EternitekCore implements ModInitializer {

    public static final String NAME = "Eternitek Core";
    public static final String ID = "eternitek";
    public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

    @Override
    public void onInitialize() {

        EternitekBlocks.register();
        EternitekItems.register();

        LOGGER.info("Successfully loaded");

    }

    public static Identifier id(String path) {
        return Identifier.of(ID, path);
    }

}
