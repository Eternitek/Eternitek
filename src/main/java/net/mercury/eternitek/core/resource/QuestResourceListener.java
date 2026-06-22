package net.mercury.eternitek.core.resource;

import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.v1.reloader.SimpleReloadListener;
import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.codex.quest.Quest;
import net.mercury.eternitek.core.codex.research.Tree;
import net.mercury.eternitek.core.registry.EternitekRegistries;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class QuestResourceListener extends SimpleReloadListener<List<Quest>> {

    @Override
    protected List<Quest> prepare(SharedState state) {
        FileToIdConverter converter = FileToIdConverter.json("quest");
        try {
            return CompletableFuture
                    .supplyAsync(() -> converter.listMatchingResources(state.resourceManager()))
                    .thenCompose(QuestResourceListener::load)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static CompletableFuture<List<Quest>> load(Map<Identifier, Resource> resources) {

        List<CompletableFuture<Quest>> quests = new ArrayList<>();

        resources.forEach((id, resource) -> quests.add(CompletableFuture.supplyAsync(() -> {

            try (InputStream stream = resource.open()) {

                JsonObject json = GsonHelper.parse(new InputStreamReader(stream));
                DataResult<Quest> result = Quest.CODEC.parse(JsonOps.INSTANCE, json);


                EternitekCore.LOGGER.info("Successfully loaded quest {}", id);

                return result.resultOrPartial(EternitekCore.LOGGER::error).get();

            } catch (Exception error) {
                EternitekCore.LOGGER.error("Failed to load quest {}: {}", id, error);
            }

            return null;

        })));

        return Util.sequence(quests);

    }

    @Override
    protected void apply(List<Quest> prepared, PreparableReloadListener.SharedState state) {
        EternitekCore.LOGGER.info("Preparing {} quest(s)", prepared.size());
        for (Quest quest : prepared) {
            EternitekRegistries.QUEST.put(quest.id(), quest);
            EternitekCore.LOGGER.info("Quest {} applied", quest.id());
        }
    }

}
