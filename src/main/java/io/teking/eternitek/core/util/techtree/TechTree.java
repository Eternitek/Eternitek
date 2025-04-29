package io.teking.eternitek.core.util.techtree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

import java.util.List;

public record TechTree(String name, Identifier id, List<TechNode> nodes) {

    public static final Codec<TechTree> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(TechTree::name),
            Identifier.CODEC.fieldOf("id").forGetter(TechTree::id),
            TechNode.CODEC.listOf().fieldOf("nodes").forGetter(TechTree::nodes)
    ).apply(instance, TechTree::new));

}
