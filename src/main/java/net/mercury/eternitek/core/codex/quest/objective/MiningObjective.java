package net.mercury.eternitek.core.codex.quest.objective;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.mercury.eternitek.core.registry.EternitekObjectives;
import net.minecraft.advancements.criterion.BlockPredicate;

public record MiningObjective(int amount, BlockPredicate predicate) implements Objective<Integer> {

    public static final MapCodec<MiningObjective> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("amount").forGetter(MiningObjective::amount),
            BlockPredicate.CODEC.fieldOf("predicate").forGetter(MiningObjective::predicate)
    ).apply(instance, MiningObjective::new));

    @Override
    public Type<?> getType() {
        return EternitekObjectives.MINING;
    }

    @Override
    public Integer getRequirement() {
        return amount;
    }

}