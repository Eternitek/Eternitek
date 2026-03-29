package net.mercury.eternitek.core.codex.quest.objectives;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.mercury.eternitek.core.registry.EternitekObjectives;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;

public record KillEntitiesObjective(int count, EntityPredicate predicate) implements Objective {

    public static final MapCodec<KillEntitiesObjective> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("count").forGetter(KillEntitiesObjective::count),
            EntityPredicate.CODEC.fieldOf("predicate").forGetter(KillEntitiesObjective::predicate)
    ).apply(instance, KillEntitiesObjective::new));

    public KillEntitiesObjective(int count, EntityType<?> type) {
        this(count, EntityPredicate.Builder
                .entity()
                .entityType(EntityTypePredicate.of(BuiltInRegistries.ENTITY_TYPE, type)).build()
        );
    }

    @Override
    public Type<?> getType() {
        return EternitekObjectives.KILL_ENTITIES;
    }

}
