package io.teking.eternitek.core.registry;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.block.entity.PipeBlockEntity;
import io.teking.eternitek.core.block.entity.TransferTerminalBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;


public class EternitekBlockEntities {

    public static BlockEntityType<PipeBlockEntity> PIPE_BLOCK_ENTITY;
    public static BlockEntityType<TransferTerminalBlockEntity> TRANSFER_TERMINAL_ENTITY;

    public static void register() {
        PIPE_BLOCK_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                EternitekCore.id("pipe"),
                BlockEntityType.Builder.create(
                        (pos, state) -> new PipeBlockEntity(PIPE_BLOCK_ENTITY, pos, state),
                        EternitekBlocks.DEV_PIPE_BLOCK
                ).build(null)
        );

        TRANSFER_TERMINAL_ENTITY = Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                EternitekCore.id("pipe_transfer_terminal"),
                BlockEntityType.Builder.create(
                        (pos, state) -> new TransferTerminalBlockEntity(TRANSFER_TERMINAL_ENTITY, pos, state),
                        EternitekBlocks.TRANSFER_TERMINAL
                ).build()
        );
    }

    public static void registerEntity(String name, BlockEntityType<?> blockEntity) {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, EternitekCore.id(name), blockEntity);
    }

}
