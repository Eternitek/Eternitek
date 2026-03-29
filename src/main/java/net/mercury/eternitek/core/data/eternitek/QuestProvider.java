package net.mercury.eternitek.core.data.eternitek;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.mercury.eternitek.core.codex.quest.Quest;
import net.mercury.eternitek.core.codex.quest.objectives.Objective;
import net.mercury.eternitek.core.codex.research.Tree;
import net.minecraft.advancements.Criterion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public abstract class QuestProvider extends FabricCodecDataProvider<Quest> {

    private final Map<Identifier, Quest> toRegister;

    protected QuestProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(
                output,
                registries,
                PackOutput.Target.DATA_PACK,
                "quest",
                Quest.CODEC
        );
        this.toRegister = new HashMap<>();
    }

    @Override
    protected void configure(BiConsumer<Identifier, Quest> provider, HolderLookup.Provider lookup) {
        QuestBuilder builder = new QuestBuilder(this);
        generateQuests(builder);
        for (Identifier id : toRegister.keySet()) {
            provider.accept(id, toRegister.get(id));
        }
    }

    protected void register(Identifier id, Quest quest) {
        this.toRegister.put(id, quest);
    }

    protected abstract void generateQuests(QuestBuilder builder);

    protected static class QuestBuilder {

        private Identifier id;
        private Component name;
        private List<Component> description;
        private List<Objective> objectives;

        private final QuestProvider parent;

        public QuestBuilder(QuestProvider parent) {
            this.parent = parent;
            this.description = new ArrayList<>();
            this.objectives = new ArrayList<>();
        }

        public QuestBuilder id(Identifier id) {
            this.id = id;
            return this;
        }

        public QuestBuilder id(String id) {
            this.id = Identifier.parse(id);
            return this;
        }

        public QuestBuilder name(Component name) {
            this.name = name;
            return this;
        }

        public QuestBuilder description(List<Component> description) {
            this.description = description;
            return this;
        }

        public QuestBuilder description(Component description) {
            this.description.add(description);
            return this;
        }

        public QuestBuilder objectives(List<Objective> objectives) {
            this.objectives = objectives;
            return this;
        }

        public QuestBuilder objective(Objective objective) {
            this.objectives.add(objective);
            return this;
        }

        public QuestBuilder build() {
            Quest result = new Quest(this.id, this.name, this.description, this.objectives);
            this.parent.register(this.id, result);
            this.id = null;
            this.name = null;
            this.description = new ArrayList<>();
            this.objectives = new ArrayList<>();
            return this;
        }

    }

}
