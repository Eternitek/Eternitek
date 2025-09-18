package io.teking.eternitek.core;

import io.teking.eternitek.core.data.vanilla.EternitekLanguageProvider;
import io.teking.eternitek.core.data.vanilla.EternitekModelProvider;
import io.teking.eternitek.core.data.vanilla.EternitekRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class EternitekDataGenerator implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {

        FabricDataGenerator.Pack pack = generator.createPack();

        pack.addProvider(EternitekModelProvider::new);
        pack.addProvider(EternitekLanguageProvider::new);
        pack.addProvider(EternitekRecipeProvider::new);

    }

}
