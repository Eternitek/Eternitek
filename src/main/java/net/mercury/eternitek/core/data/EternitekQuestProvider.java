package net.mercury.eternitek.core.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.codex.quest.objectives.KillEntitiesObjective;
import net.mercury.eternitek.core.data.eternitek.QuestProvider;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.KilledTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.ARGB;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.entity.EntityType;

import javax.swing.text.html.parser.Entity;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class EternitekQuestProvider extends QuestProvider {

    public EternitekQuestProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void generateQuests(QuestBuilder builder) {

        builder.name(Component.literal("Kill Slimes")).id("eternitek:kill_slimes")
                .objective(new KillEntitiesObjective(5, EntityType.SLIME));
        builder.build();

    }

    @Override
    public String getName() {
        return "Quest Provider";
    }

}
