package net.mercury.eternitek.core.registry;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.codex.research.Tree;
import net.mercury.eternitek.core.multiblock.Multiblock;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.HashMap;
import java.util.Map;

public class EternitekRegistries {

    public static final Map<Identifier, Tree> RESEARCH = new HashMap<>();

    public static final Map<Identifier, Multiblock> MULTIBLOCK = new HashMap<>();

    public static void register() {

    }

    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(EternitekCore.id(name));
    }

}
