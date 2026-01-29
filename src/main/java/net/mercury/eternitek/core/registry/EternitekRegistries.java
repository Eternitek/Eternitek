package net.mercury.eternitek.core.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.codex.research.Tree;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class EternitekRegistries {

    public static final ResourceKey<Registry<Tree>> RESEARCH_TREE_KEY = key("research_tree");
    public static final Registry<Tree> RESEARCH_TREE = FabricRegistryBuilder
            .create(RESEARCH_TREE_KEY)
            .buildAndRegister();

    public static void register() {

    }

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(EternitekCore.id(name));
    }

}
