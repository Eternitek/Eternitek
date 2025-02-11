package io.teking.eternitek.core.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import static io.teking.eternitek.core.registry.EternitekItems.CODEX;
import static io.teking.eternitek.core.registry.EternitekItems.CRUDE_STEEL_INGOT;

public class EternitekLanguageProvider extends FabricLanguageProvider {

    public EternitekLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {

        translationBuilder.add(CODEX, "Codex");

        translationBuilder.add(CRUDE_STEEL_INGOT, "Crude Steel Ingot");

    }

}
