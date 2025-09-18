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
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        blockStateModelGenerator.registerSimpleCubeAll(BLAST_BRICKS);
        blockStateModelGenerator.registerSimpleCubeAll(FACILITY_TILES);
        blockStateModelGenerator.registerSimpleCubeAll(PRIMITIVE_FURNACE);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        itemModelGenerator.register(CODEX, Models.GENERATED);

        itemModelGenerator.register(BLAST_BRICK, Models.GENERATED);
        itemModelGenerator.register(CRUDE_STEEL_INGOT, Models.GENERATED);

    }

}
