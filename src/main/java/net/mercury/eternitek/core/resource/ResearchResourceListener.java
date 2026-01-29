package net.mercury.eternitek.core.resource;

import com.mojang.serialization.Codec;
import net.mercury.eternitek.core.codex.research.Tree;
import net.mercury.eternitek.core.registry.EternitekRegistries;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;
import java.util.stream.Stream;

public class ResearchResourceListener extends SimpleJsonResourceReloadListener<Tree> {

    public ResearchResourceListener() {
        super(
                HolderLookup.Provider.create(Stream.of(EternitekRegistries.RESEARCH_TREE)),
                Tree.CODEC,
                EternitekRegistries.RESEARCH_TREE_KEY
        );
    }

    @Override
    protected void apply(Map<Identifier, Tree> preparations, ResourceManager manager, ProfilerFiller profiler) {
        preparations.forEach((id, tree) -> Registry.register(EternitekRegistries.RESEARCH_TREE, id, tree));
    }

}
