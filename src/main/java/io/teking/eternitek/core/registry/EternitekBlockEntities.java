package io.teking.eternitek.core.registry;

import io.teking.eternitek.core.EternitekCore;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.mixin.object.builder.BlockEntityTypeBuilderMixin;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class EternitekBlockEntities {



    public static void register() {

    }

    public static void register(String name, BlockEntityType<?> blockEntity) {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, EternitekCore.id(name), blockEntity);
    }

}
