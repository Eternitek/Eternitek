package io.teking.eternitek.core.registry;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.block.entity.PipeControllerBlockEntity;
import io.teking.eternitek.core.block.entity.PipeTileEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class EternitekBlockEntities {

    public static final BlockEntityType<PipeTileEntity> STONE_PIPE_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(EternitekCore.MOD_ID, "stone_pipe"),
            FabricBlockEntityTypeBuilder.create(PipeTileEntity::new, EternitekBlocks.STONE_PIPE).build()
    );

    public static final BlockEntityType<PipeControllerBlockEntity> PIPE_CONTROLLER_BLOCK_ENTITY = Registry.register(
            Registries.BLOCK_ENTITY_TYPE,
            Identifier.of(EternitekCore.MOD_ID, "pipe_controller"),
            FabricBlockEntityTypeBuilder.create(PipeControllerBlockEntity::new, EternitekBlocks.PIPE_CONTROLLER).build()
    );

    public static void register() {

    }
}
