package io.teking.eternitek.core.resource;

import com.google.gson.JsonObject;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.multiblock.Multiblock;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class MultiblockReloadListener implements SimpleSynchronousResourceReloadListener {

    private final RegistryWrapper.WrapperLookup wrapperLookup;

    public static final Map<Identifier, Multiblock> MULTIBLOCKS = new HashMap<>();

    public MultiblockReloadListener(RegistryWrapper.WrapperLookup wrapperLookup) {
        this.wrapperLookup = wrapperLookup;
    }

    @Override
    public Identifier getFabricId() {
        return EternitekCore.id("multiblock");
    }

    @Override
    public void reload(ResourceManager manager) {

        for(Identifier id : manager.findResources("multiblocks", path -> path.getPath().endsWith(".json")).keySet()) {
            try(InputStream stream = manager.getResource(id).get().getInputStream()) {

                JsonObject object = JsonHelper.deserialize(new InputStreamReader(stream, StandardCharsets.UTF_8));
                DataResult<Multiblock.Data> result = Multiblock.Data.CODEC.parse(
                        RegistryOps.of(JsonOps.INSTANCE, wrapperLookup),
                        object
                );

                Multiblock.Data data = result.resultOrPartial(EternitekCore.LOGGER::error).get();
                List<List<String>> dataPattern = data.pattern();

                char[][][] pattern = new char[dataPattern.size()][dataPattern.getFirst().size()][dataPattern.getFirst().getFirst().length()];
                for(int y = 0; y < dataPattern.size(); y++) {
                    for(int z = 0; z < dataPattern.getFirst().size(); z++) {
                        for(int x = 0; x < dataPattern.getFirst().getFirst().length(); x++) {
                            pattern[y][z][x] = dataPattern.get(y).get(z).charAt(x);
                        }
                    }
                }

                MULTIBLOCKS.put(data.id(), new Multiblock(pattern, data.key()));

            } catch(Exception error) {
                EternitekCore.LOGGER.error("Error occurred loading multiblock {} from resource: {}", id, error);
            }
        }

    }

}
