package net.mercury.eternitek.core.data;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.mercury.eternitek.core.registry.EternitekItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.world.item.Item;

public class EternitekModelProvider extends FabricModelProvider {

    public EternitekModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {

        for (Item item : EternitekItems.GENERATE) {
            generator.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
        }

    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {

    }

    @Override
    public String getName() {
        return "Model Provider";
    }

}
