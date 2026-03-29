package net.mercury.eternitek.core.resource;

import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.v1.reloader.SimpleReloadListener;
import net.mercury.eternitek.core.EternitekCore;
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

public class ResearchResourceListener extends SimpleReloadListener<List<Tree>> {

    @Override
    protected List<Tree> prepare(SharedState state) {
        FileToIdConverter converter = FileToIdConverter.json("research");
        try {
            return CompletableFuture
                    .supplyAsync(() -> converter.listMatchingResources(state.resourceManager()))
                    .thenCompose(ResearchResourceListener::load)
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private static CompletableFuture<List<Tree>> load(Map<Identifier, Resource> resources) {

        List<CompletableFuture<Tree>> trees = new ArrayList<>();

        resources.forEach((id, resource) -> trees.add(CompletableFuture.supplyAsync(() -> {

            try (InputStream stream = resource.open()) {

                JsonObject json = GsonHelper.parse(new InputStreamReader(stream));
                DataResult<Tree> result = Tree.CODEC.parse(JsonOps.INSTANCE, json);

                EternitekCore.LOGGER.info("Successfully loaded research tree {}", id);

                return result.resultOrPartial(EternitekCore.LOGGER::error).get();

            } catch (Exception error) {
                EternitekCore.LOGGER.error("Failed to load research tree {}: {}", id, error);
            }

            return null;

        })));

        return Util.sequence(trees);

    }

    @Override
    protected void apply(List<Tree> prepared, PreparableReloadListener.SharedState state) {
        EternitekCore.LOGGER.info("Preparing {} research tree(s)", prepared.size());
        for (Tree tree : prepared) {
            EternitekRegistries.RESEARCH.put(tree.id(), tree);
            EternitekCore.LOGGER.info("Research tree {} applied", tree.id());
        }
    }

}
