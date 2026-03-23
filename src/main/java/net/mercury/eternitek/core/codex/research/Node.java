package net.mercury.eternitek.core.codex.research;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.mercury.eternitek.core.EternitekCore;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.Identifier;
import org.joml.Vector2i;

import java.util.List;

public record Node(
        Identifier id,
        List<Component> tooltip,
        Identifier icon,
        Vector2i position,
        List<Identifier> children
) {

    private static final Codec<Vector2i> POSITION_CODEC = Codec.list(Codec.INT, 2, 2).xmap(
            ints -> new Vector2i(ints.getFirst(), ints.getLast()),
            vec -> List.of(vec.x, vec.y)
    );

    public static final Codec<Node> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(Node::id),
            ComponentSerialization.CODEC.listOf().fieldOf("tooltip").orElse(List.of(Component.empty())).forGetter(Node::tooltip),
            Identifier.CODEC.fieldOf("icon").forGetter(Node::icon),
            POSITION_CODEC.fieldOf("position").forGetter(Node::position),
            Identifier.CODEC.listOf().fieldOf("children").orElse(List.of(EternitekCore.id("root"))).forGetter(Node::children)
    ).apply(instance, Node::new));

}
