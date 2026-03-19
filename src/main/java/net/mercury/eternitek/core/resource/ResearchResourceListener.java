package net.mercury.eternitek.core.resource;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import net.fabricmc.fabric.api.resource.v1.reloader.SimpleReloadListener;
import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.codex.research.Tree;
import net.mercury.eternitek.core.registry.EternitekRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.*;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Util;
import net.minecraft.util.profiling.ProfilerFiller;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class ResearchResourceListener extends SimpleReloadListener<List<Tree>> {

    @Override
    protected List<Tree> prepare(SharedState state) {
        FileToIdConverter converter = FileToIdConverter.json("research");
        return CompletableFuture
                .supplyAsync(() -> converter.listMatchingResources(state.resourceManager()))
                .thenCompose(ResearchResourceListener::load)
                .getNow(List.of());
    }

    private static CompletableFuture<List<Tree>> load(Map<Identifier, Resource> resources) {

        List<CompletableFuture<Tree>> trees = new ArrayList<>();

        resources.forEach((id, resource) -> trees.add(CompletableFuture.supplyAsync(() -> {

            try (InputStream stream = resource.open()) {

                JsonObject json = GsonHelper.parse(new InputStreamReader(stream));
                DataResult<Tree> result = Tree.CODEC.parse(JsonOps.INSTANCE, json);

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
        for (Tree tree : prepared) {
            EternitekRegistries.RESEARCH.put(tree.id(), tree);
        }
    }

}
