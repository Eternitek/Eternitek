package io.teking.eternitek.core.data;

import io.teking.eternitek.core.EternitekCore;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import static io.teking.eternitek.core.registry.EternitekItems.*;
import static io.teking.eternitek.core.registry.EternitekBlocks.*;

public class EternitekLanguageProvider extends FabricLanguageProvider {

    public EternitekLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder translationBuilder) {
        translationBuilder.add(BlAST_BRICK, "Blast Brick");
        translationBuilder.add(BLAST_BRICKS, "Blast Bricks");
        translationBuilder.add(CODEX, "Codex");
        translationBuilder.add(CRUDE_STEEL_INGOT, "Crude Steel Ingot");
        translationBuilder.add(EternitekCore.ETERNITEK_CORE, "Eternitek Core");
        translationBuilder.add(PRIMITIVE_FURNACE, "Primative Furnace");
    }

}
