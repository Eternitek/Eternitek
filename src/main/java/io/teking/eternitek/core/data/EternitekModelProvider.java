package io.teking.eternitek.core.data;

import io.teking.eternitek.core.registry.EternitekItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.item.Item;

import static io.teking.eternitek.core.registry.EternitekItems.*;

public class EternitekModelProvider extends FabricModelProvider {

    public EternitekModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        itemModelGenerator.register(GUIDEBOOK, Models.GENERATED);

        itemModelGenerator.register(CRUDE_STEEL_INGOT, Models.GENERATED);

    }

}
