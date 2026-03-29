package net.mercury.eternitek.core.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.data.eternitek.ResearchProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;

import java.util.concurrent.CompletableFuture;

public class EternitekResearchProvider extends ResearchProvider {

    public EternitekResearchProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void generateResearch(TreeBuilder builder) {

        builder.name("Keycards").id(EternitekCore.id("keycards"));

        builder.node()
                .id("eternitek:simple")
                .tooltip(Component.literal("Simple").withColor(0xFFA5AFB1))
                .tooltip(Component.literal("The name says it all.").withColor(0xFFA5AFB1))
                .icon("eternitek:textures/item/simple_keycard.png")
                .position(-60, 0)
                .child("eternitek:gelatinous")
                .child("eternitek:reverious")
                .child("eternitek:breezy")
                .build();

        builder.node()
                .id("eternitek:gelatinous")
                .tooltip(Component.literal("Gelatinous").withColor(0xFFA7B580))
                .tooltip(Component.literal("Eugh. It's sticky.").withColor(0xFFA5AFB1))
                .icon("eternitek:textures/item/gelatinous_keycard.png")
                .position(0, -60)
                .child("eternitek:gilded")
                .build();

        builder.node()
                .id("eternitek:reverious")
                .tooltip(Component.literal("Reverious").withColor(0xFFCC4F82))
                .tooltip(Component.literal("Is it... breathing?").withColor(0xFFA5AFB1))
                .icon("eternitek:textures/item/reverious_keycard.png")
                .position(0, 0)
                .child("eternitek:gilded")
                .build();

        builder.node()
                .id("eternitek:breezy")
                .tooltip(Component.literal("Breezy").withColor(0xFF8B97C9))
                .tooltip(Component.literal("Wheeeeeee!").withColor(0xFFA5AFB1))
                .icon("eternitek:textures/item/breezy_keycard.png")
                .position(0, 60)
                .child("eternitek:gilded")
                .build();

        builder.node()
                .id("eternitek:gilded")
                .tooltip(Component.literal("Gilded").withColor(0xFFE9BE6A))
                .tooltip(Component.literal("For executives only.").withColor(0xFFA5AFB1))
                .icon("eternitek:textures/item/gilded_keycard.png")
                .position(100, 0)
                .build();

        builder.build();

    }

    @Override
    public String getName() {
        return "Research Provider";
    }

}
