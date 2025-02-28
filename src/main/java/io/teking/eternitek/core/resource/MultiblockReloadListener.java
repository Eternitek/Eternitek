package io.teking.eternitek.core.resource;

import io.teking.eternitek.core.EternitekCore;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class MultiblockReloadListener implements SimpleSynchronousResourceReloadListener {

    @Override
    public Identifier getFabricId() {
        return EternitekCore.id("multiblock");
    }

    @Override
    public void reload(ResourceManager manager) {



    }

}
