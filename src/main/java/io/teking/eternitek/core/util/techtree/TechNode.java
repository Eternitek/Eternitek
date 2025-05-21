package io.teking.eternitek.core.util.techtree;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.teking.eternitek.core.EternitekCore;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Identifier;
import org.joml.Vector2i;

import java.util.List;
import java.util.Optional;

public record TechNode(Identifier identifier, List<Text> tooltip, List<Identifier> connections, Optional<Identifier> quest, Identifier icon, Vector2i position) {

    private static final Codec<Vector2i> POSITION_CODEC = Codec.pair(Codec.INT.fieldOf("x").codec(), Codec.INT.fieldOf("y").codec()).xmap(
            (intPair) -> new Vector2i(intPair.getFirst(), intPair.getSecond()),
            (vector) -> new Pair<>(vector.x(), vector.y())
    );

    public static final Codec<TechNode> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("node").forGetter(TechNode::identifier),
            TextCodecs.CODEC.listOf().fieldOf("tooltip").orElse(List.of(Text.empty())).forGetter(TechNode::tooltip),
            Identifier.CODEC.listOf().fieldOf("connections").orElse(List.of(EternitekCore.id("root"))).forGetter(TechNode::connections),
            Identifier.CODEC.optionalFieldOf("quest").forGetter(TechNode::quest),
            Identifier.CODEC.fieldOf("icon").forGetter(TechNode::icon),
            POSITION_CODEC.fieldOf("position").forGetter(TechNode::position)
    ).apply(instance, TechNode::new));

    public int x() {
        return position.x();
    }

    public int y() {
        return position.y();
    }

}