package io.teking.eternitek.core.resource;

import io.teking.eternitek.core.EternitekCore;
import net.fabricmc.fabric.api.resource.SimpleResourceReloadListener;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.InputStream;

public class ElementReloadListener implements SimpleSynchronousResourceReloadListener {

    @Override
    public Identifier getFabricId() {
        return EternitekCore.id("element");
    }

    @Override
    public void reload(ResourceManager manager) {

        for(Identifier id : manager.findResources("elements", path -> path.getPath().endsWith(".json")).keySet()) {
            try(InputStream stream = manager.getResource(id).get().getInputStream()) {



            } catch(Exception except) {
                EternitekCore.LOGGER.error("Error occurred loading element {} from resource, error: {}", id, except);
            }
        }

    }

}
