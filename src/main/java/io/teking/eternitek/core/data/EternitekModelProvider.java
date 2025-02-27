package io.teking.eternitek.core.data;

import io.teking.eternitek.core.registry.EternitekBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import static io.teking.eternitek.core.registry.EternitekItems.*;
import static io.teking.eternitek.core.registry.EternitekBlocks.*;

public class EternitekModelProvider extends FabricModelProvider {

    public EternitekModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        blockStateModelGenerator.registerSimpleCubeAll(BLAST_BRICKS);
        // Disabled because I can't be bothered to fix it
//        blockStateModelGenerator.registerSimpleCubeAll(PRIMITIVE_FURNACE);

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        itemModelGenerator.register(CODEX, Models.GENERATED);
        itemModelGenerator.register(CRUDE_STEEL_INGOT, Models.GENERATED);
        itemModelGenerator.register(BlAST_BRICK, Models.GENERATED);

    }

}
