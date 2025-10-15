package io.teking.eternitek.core.data.vanilla;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

import static io.teking.eternitek.core.registry.EternitekBlocks.*;
import static io.teking.eternitek.core.registry.EternitekItems.*;

public class EternitekModelProvider extends FabricModelProvider {

    public EternitekModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {

        generator.registerSimpleCubeAll(BLAST_BRICKS);
        generator.registerSimpleCubeAll(FACILITY_TILES);
        generator.registerSimpleCubeAll(PRIMITIVE_FURNACE);

    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {

        generator.register(CODEX, Models.GENERATED);

        generator.register(BLAST_BRICK, Models.GENERATED);
        generator.register(CRUDE_STEEL_INGOT, Models.GENERATED);

    }

}
