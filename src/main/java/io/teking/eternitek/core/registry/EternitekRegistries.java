package io.teking.eternitek.core.registry;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.multiblock.Multiblock;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class EternitekRegistries {

    public static final Registry<Multiblock> MULTIBLOCK = FabricRegistryBuilder.<Multiblock>createSimple(
            RegistryKey.ofRegistry(EternitekCore.id("multiblock"))
    ).buildAndRegister();

}
