package io.teking.eternitek.core.util.techtree;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.teking.eternitek.core.EternitekCore;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public record TechNode(Identifier identifier, Identifier parent, @Nullable Identifier quest, Identifier icon,
                       ItemStack item, int x, int y) {

    public static final Codec<TechNode> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("node").forGetter(TechNode::identifier),
            Identifier.CODEC.fieldOf("parent").orElse(EternitekCore.id("root")).forGetter(TechNode::parent),
            Identifier.CODEC.fieldOf("quest").orElse(null).forGetter(TechNode::quest),
            Identifier.CODEC.fieldOf("icon").forGetter(TechNode::icon),
            ItemStack.CODEC.fieldOf("item").forGetter(TechNode::item),
            Codec.INT.fieldOf("x").forGetter(TechNode::x),
            Codec.INT.fieldOf("y").forGetter(TechNode::y)
    ).apply(instance, TechNode::new));

}