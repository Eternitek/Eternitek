package io.teking.eternitek.core.resource;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.util.techtree.TechNode;
import io.teking.eternitek.core.util.techtree.TechTree;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TechTreeReloadListener implements SimpleSynchronousResourceReloadListener {

    private final RegistryWrapper.WrapperLookup wrapperLookup;

    public static final Map<Identifier, TechTree> TREES = new HashMap<>();

    public TechTreeReloadListener(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.wrapperLookup = wrapperLookup;
    }

    @Override
    public Identifier getFabricId() {
        return EternitekCore.id("techtree");
    }

    @Override
    public void reload(ResourceManager manager) {

        for(Identifier id : manager.findResources("techtree", path -> path.getPath().endsWith(".json")).keySet()) {
            try(InputStream stream = manager.getResource(id).get().getInputStream()) {

                JsonObject object = JsonHelper.deserialize(new InputStreamReader(stream, StandardCharsets.UTF_8));
                DataResult<TechTree> result = TechTree.CODEC.parse(JsonOps.INSTANCE, object);

                TechTree tree = result.resultOrPartial(EternitekCore.LOGGER::error).orElseThrow();
                EternitekCore.LOGGER.info("Tech tree id: {}", tree.id());
                TREES.put(tree.id(), tree);

            } catch(Exception error) {
                EternitekCore.LOGGER.error("Error occurred loading tech tree {} from resource: {}", id, error);
                error.printStackTrace();
            }
        }

    }

}
