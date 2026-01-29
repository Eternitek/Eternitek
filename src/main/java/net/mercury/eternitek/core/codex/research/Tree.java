package net.mercury.eternitek.core.codex.research;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;

import java.util.List;

public record Tree(
        String name,
        Identifier id,
        List<Node> nodes
) {

    public static final Codec<Tree> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(Tree::name),
            Identifier.CODEC.fieldOf("id").forGetter(Tree::id),
            Node.CODEC.listOf().fieldOf("nodes").forGetter(Tree::nodes)
    ).apply(instance, Tree::new));

}
