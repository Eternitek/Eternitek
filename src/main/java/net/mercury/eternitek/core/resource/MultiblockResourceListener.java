package net.mercury.eternitek.core.resource;

import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.resource.v1.reloader.SimpleReloadListener;
import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.multiblock.Multiblock;
import net.minecraft.resources.FileToIdConverter;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.Util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class MultiblockResourceListener extends SimpleReloadListener<List<Multiblock.Data>> {

    @Override
    protected List<Multiblock.Data> prepare(SharedState state) {
        FileToIdConverter converter = FileToIdConverter.json("multiblock");
        return CompletableFuture
                .supplyAsync(() -> converter.listMatchingResources(state.resourceManager()))
                .thenCompose(MultiblockResourceListener::load)
                .getNow(List.of());
    }

    private static CompletableFuture<List<Multiblock.Data>> load(Map<Identifier, Resource> resources) {

        List<CompletableFuture<Multiblock.Data>> data = new ArrayList<>();

        resources.forEach((id, resource) -> data.add(CompletableFuture.supplyAsync(() -> {

            try (InputStream stream = resource.open()) {

                JsonObject json = GsonHelper.parse(new InputStreamReader(stream));

            } catch (Exception error) {
                EternitekCore.LOGGER.error("Failed to load multiblock {}: {}", id, error);
            }

            return null;

        })));

        return Util.sequence(data);

    }

    @Override
    protected void apply(List<Multiblock.Data> prepared, SharedState state) {

    }

}
