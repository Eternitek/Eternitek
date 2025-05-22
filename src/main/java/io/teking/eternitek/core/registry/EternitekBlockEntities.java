package io.teking.eternitek.core.registry;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.block.entity.PipeBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;


public class EternitekBlockEntities {

    public static BlockEntityType<PipeBlockEntity> PIPE_BLOCK_ENTITY;


    public static void register() {
        PIPE_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                EternitekCore.id("pipe"),
                BlockEntityType.Builder.create(
                        (pos, state) -> new PipeBlockEntity(PIPE_BLOCK_ENTITY, pos, state),
                        EternitekBlocks.DEV_PIPE_BLOCK
                ).build(null)
        );
    }

    public static void registerEntity(String name, BlockEntityType<?> blockEntity) {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, EternitekCore.id(name), blockEntity);
    }

}
