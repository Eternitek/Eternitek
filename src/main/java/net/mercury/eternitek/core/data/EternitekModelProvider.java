package net.mercury.eternitek.core.data;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;

import static net.mercury.eternitek.core.registry.EternitekItems.*;

public class EternitekModelProvider extends FabricModelProvider {

    public EternitekModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {

        generator.generateFlatItem(CODEX, ModelTemplates.FLAT_ITEM);

    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {

    }

    @Override
    public String getName() {
        return "Model Provider";
    }

}
