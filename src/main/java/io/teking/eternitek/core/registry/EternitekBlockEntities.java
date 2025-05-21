package io.teking.eternitek.core.registry;

import io.teking.eternitek.core.EternitekCore;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;


public class EternitekBlockEntities {



    public static void register() {

    }

    public static void register(String name, BlockEntityType<?> blockEntity) {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, EternitekCore.id(name), blockEntity);
    }

}
