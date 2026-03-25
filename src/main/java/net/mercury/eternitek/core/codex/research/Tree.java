package net.mercury.eternitek.core.codex.research;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public record Tree(
        String name,
        Identifier id,
        Map<Identifier, Node> nodes
) {

    public static final Codec<Tree> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(Tree::name),
            Identifier.CODEC.fieldOf("id").forGetter(Tree::id),
            Node.CODEC.listOf().fieldOf("nodes").xmap(
                    Tree::nodeMap,
                    map -> map.values().stream().toList()
            ).forGetter(Tree::nodes)
    ).apply(instance, Tree::new));

    public static Map<Identifier, Node> nodeMap(List<Node> nodes) {
        return List.copyOf(nodes).stream().collect(Collectors.toMap(Node::id, Function.identity()));
    }

}
