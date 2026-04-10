package net.mercury.eternitek.core.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.codex.quest.objective.MiningObjective;
import net.mercury.eternitek.core.data.eternitek.QuestProvider;
import net.minecraft.advancements.criterion.BlockPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;

import java.util.concurrent.CompletableFuture;

public class EternitekQuestProvider extends QuestProvider {

    public EternitekQuestProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void generateQuests(QuestBuilder builder, HolderLookup.Provider lookup) {

        builder.name("Test Quest")
                .id(EternitekCore.id("test_quest"))
                .description(Component.literal("Test Quest").withColor(0xFFA5AFB1))
                .objective(new MiningObjective(
                        10,
                        BlockPredicate.Builder
                                .block()
                                .of(lookup.lookupOrThrow(Registries.BLOCK), BlockTags.IRON_ORES).build()
                ));

        builder.build();

    }

    @Override
    public String getName() {
        return "Quest Provider";
    }

}
