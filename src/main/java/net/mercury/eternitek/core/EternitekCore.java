package net.mercury.eternitek.core;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.mercury.eternitek.core.registry.EternitekItems;
import net.mercury.eternitek.core.resource.MultiblockResourceListener;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.PackType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EternitekCore implements ModInitializer {

	public static final String NAME = "Eternitek";
	public static final String ID = "eternitek";
	public static final Logger LOGGER = LogManager.getLogger(NAME);

	@Override
	public void onInitialize() {

		ResourceLoader loader = ResourceLoader.get(PackType.SERVER_DATA);
		loader.registerReloadListener(id("multiblock"), new MultiblockResourceListener());

		EternitekItems.register();

		LOGGER.info("Hello Fabric world!");

	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(ID, path);
	}

	public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registry, String path) {
		return ResourceKey.create(registry, id(path));
	}

}