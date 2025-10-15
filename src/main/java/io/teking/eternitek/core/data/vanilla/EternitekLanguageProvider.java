package io.teking.eternitek.core.data.vanilla;

import io.teking.eternitek.core.registry.EternitekTabs;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

import static io.teking.eternitek.core.registry.EternitekBlocks.*;
import static io.teking.eternitek.core.registry.EternitekItems.*;

public class EternitekLanguageProvider extends FabricLanguageProvider {

    public EternitekLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, "en_us", registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup wrapperLookup, TranslationBuilder builder) {

        builder.add(BLAST_BRICK, "Blast Brick");
        builder.add(CODEX, "Codex");
        builder.add(CRUDE_STEEL_INGOT, "Crude Steel Ingot");

        builder.add(BLAST_BRICKS, "Blast Bricks");
        builder.add(FACILITY_TILES, "Facility Tiles");
        builder.add(MACHINE_CORE, "Machine Core");
        builder.add(PRIMITIVE_FURNACE, "Primitive Furnace");

        builder.add(EternitekTabs.ETERNITEK_CORE, "Eternitek Core");

        builder.add("texts.eternitek.invalid_structure", "Invalid structure");
        builder.add("texts.eternitek.no_tree", "Failed to load tech tree for identifier '%s'");

    }

}
