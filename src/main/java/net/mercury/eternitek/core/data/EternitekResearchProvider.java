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
                .position(0, 0)
                .connection("eternitek:reverious")
                .connection("eternitek:gilded")
                .build();

        builder.node()
                .id("eternitek:reverious")
                .tooltip(Component.literal("Reverious").withColor(0xFFCC4F82))
                .tooltip(Component.literal("Is it... breathing?").withColor(0xFFA5AFB1))
                .icon("eternitek:textures/item/reverious_keycard.png")
                .position(-60, 60)
                .build();

        builder.node()
                .id("eternitek:gilded")
                .tooltip(Component.literal("Gilded").withColor(0xFFE9BE6A))
                .tooltip(Component.literal("For executives only.").withColor(0xFFA5AFB1))
                .icon("eternitek:textures/item/gilded_keycard.png")
                .position(60, -60)
                .build();

        builder.build();

    }

    @Override
    public String getName() {
        return "Research Provider";
    }

}
