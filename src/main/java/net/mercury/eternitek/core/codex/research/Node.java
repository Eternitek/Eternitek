package net.mercury.eternitek.core.codex.research;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.mercury.eternitek.core.EternitekCore;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import org.joml.Vector2i;

import java.util.List;

public record Node(
        Identifier node,
        List<Component> tooltip,
        Identifier icon,
        Vector2i position,
        List<Identifier> connections
) {

    public static final Codec<Node> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("node").forGetter(Node::node),
            ComponentSerialization.CODEC.listOf().fieldOf("tooltip").orElse(List.of(Component.empty())).forGetter(Node::tooltip),
            Identifier.CODEC.fieldOf("icon").forGetter(Node::icon),
            Codec.pair(Codec.INT.fieldOf("x").codec(), Codec.INT.fieldOf("y").codec()).xmap(
                    pair -> new Vector2i(pair.getFirst(), pair.getSecond()),
                    vec -> new Pair<>(vec.x, vec.y)
            ).fieldOf("position").forGetter(Node::position),
            Identifier.CODEC.listOf().fieldOf("connections").orElse(List.of(EternitekCore.id("root"))).forGetter(Node::connections)
    ).apply(instance, Node::new));

}
