package io.teking.eternitek.core.util.techtree;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.teking.eternitek.core.EternitekCore;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

import java.util.Vector;

public class TechNode {

    private final Identifier identifier;
    private final Identifier parent;

    private final @Nullable Identifier quest;

    private final Identifier icon;
    private final ItemStack item;

    private final Vector2i position;

    private static final Codec<Vector2i> POSITION_CODEC = Codec.pair(Codec.INT.fieldOf("x").codec(), Codec.INT.fieldOf("y").codec()).xmap(
            (intPair) -> new Vector2i(intPair.getFirst(), intPair.getSecond()),
            (vector) -> new Pair<>(vector.x(), vector.y())
    );

    public static final Codec<TechNode> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("node").forGetter(TechNode::getIdentifier),
            Identifier.CODEC.fieldOf("parent").orElse(EternitekCore.id("root")).forGetter(TechNode::getParent),
            Identifier.CODEC.fieldOf("quest").orElse(null).forGetter(TechNode::getQuest),
            Identifier.CODEC.fieldOf("icon").forGetter(TechNode::getIcon),
            ItemStack.CODEC.fieldOf("item").forGetter(TechNode::getItem),
            POSITION_CODEC.fieldOf("position").forGetter(TechNode::getPosition)
    ).apply(instance, TechNode::new));

    public TechNode(Identifier identifier, Identifier parent, @Nullable Identifier quest, Identifier icon, ItemStack item, Vector2i position) {
        this.identifier = identifier;
        this.parent = parent;
        this.icon = icon;
        this.item = item;
        this.position = position;
        this.quest = quest;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public Identifier getParent() {
        return parent;
    }

    public @Nullable Identifier getQuest() {
        return quest;
    }

    public Identifier getIcon() {
        return icon;
    }

    public ItemStack getItem() {
        return item;
    }

    public Vector2i getPosition() {
        return position;
    }

}