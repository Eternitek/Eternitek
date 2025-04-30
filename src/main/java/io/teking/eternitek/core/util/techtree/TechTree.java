package io.teking.eternitek.core.util.techtree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public record TechTree(String name, Identifier id, List<TechNode> nodes) {

    public static final Codec<TechTree> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(TechTree::name),
            Identifier.CODEC.fieldOf("id").forGetter(TechTree::id),
            TechNode.CODEC.listOf().fieldOf("nodes").forGetter(TechTree::nodes)
    ).apply(instance, TechTree::new));

    public Map<Identifier, TechNode> getNodeMap() {
        Map<Identifier, TechNode> map = new HashMap<>();
        for(TechNode node : nodes) {
            map.put(node.identifier(), node);
        }
        return map;
    }

}
