package net.mercury.eternitek.core;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.mercury.eternitek.core.data.EternitekLangProvider;
import net.mercury.eternitek.core.data.EternitekModelProvider;

public class EternitekDataGenerator implements DataGeneratorEntrypoint {

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {

		FabricDataGenerator.Pack pack = generator.createPack();

		pack.addProvider(EternitekLangProvider::new);
		pack.addProvider(EternitekModelProvider::new);

	}

}
