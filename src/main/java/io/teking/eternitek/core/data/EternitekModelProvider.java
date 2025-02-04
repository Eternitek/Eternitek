package io.teking.eternitek.core.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

import static io.teking.eternitek.core.registry.EternitekItems.CODEX;
import static io.teking.eternitek.core.registry.EternitekItems.CRUDE_STEEL_INGOT;

public class EternitekModelProvider extends FabricModelProvider {

    public EternitekModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        itemModelGenerator.register(CODEX, Models.GENERATED);

        itemModelGenerator.register(CRUDE_STEEL_INGOT, Models.GENERATED);

    }

}
