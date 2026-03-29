package net.mercury.eternitek.core.codex.quest;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.mercury.eternitek.core.codex.quest.objectives.Objective;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.resources.Identifier;

import java.util.List;

public record Quest(
        Identifier id,
        Component name,
        List<Component> description,
        List<Objective> objectives
) {

    public static final Codec<Quest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(Quest::id),
            ComponentSerialization.CODEC.fieldOf("name").forGetter(Quest::name),
            ComponentSerialization.CODEC.listOf().fieldOf("description").forGetter(Quest::description),
            Objective.CODEC.listOf().fieldOf("objectives").forGetter(Quest::objectives)
    ).apply(instance, Quest::new));

}
