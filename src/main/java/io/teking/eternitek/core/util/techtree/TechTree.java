package io.teking.eternitek.core.util.techtree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TechTree {

    private final String name;
    private final Identifier id;
    private final List<TechNode> nodes;
    private final Map<Identifier, TechNode> nodeMap;

    public static final Codec<TechTree> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(TechTree::getName),
            Identifier.CODEC.fieldOf("id").forGetter(TechTree::getId),
            TechNode.CODEC.listOf().fieldOf("nodes").forGetter(TechTree::getNodes)
    ).apply(instance, TechTree::new));

    private TechTree(String name, Identifier id, List<TechNode> nodes) {
        this.name = name;
        this.id = id;
        this.nodes = nodes;
        this.nodeMap = new HashMap<>();
        for(TechNode node : nodes) {
            this.nodeMap.put(node.getIdentifier(), node);
        }
    }

    public String getName() {
        return name;
    }

    public Identifier getId() {
        return id;
    }

    public Map<Identifier, TechNode> getNodeMap() {
        return nodeMap;
    }

    public List<TechNode> getNodes() {
        return nodes;
    }

}
