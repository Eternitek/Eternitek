package io.teking.eternitek.core.util.techtree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.teking.eternitek.core.EternitekCore;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public record TechNode(Identifier identifier, Identifier parent, Optional<Identifier> quest, Identifier icon, int x, int y) {

    public static final Codec<TechNode> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("node").forGetter(TechNode::identifier),
            Identifier.CODEC.fieldOf("parent").orElse(EternitekCore.id("root")).forGetter(TechNode::parent),
            Identifier.CODEC.optionalFieldOf("quest").forGetter(TechNode::quest),
            Identifier.CODEC.fieldOf("icon").forGetter(TechNode::icon),
            Codec.INT.fieldOf("x").forGetter(TechNode::x),
            Codec.INT.fieldOf("y").forGetter(TechNode::y)
    ).apply(instance, TechNode::new));

}