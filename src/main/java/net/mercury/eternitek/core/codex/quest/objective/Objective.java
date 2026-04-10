package net.mercury.eternitek.core.codex.quest.objective;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.mercury.eternitek.core.registry.EternitekRegistries;
import net.minecraft.world.entity.player.Player;

public interface Objective<T> {

    Codec<Objective<?>> CODEC = EternitekRegistries.OBJECTIVE.byNameCodec()
            .dispatch("objective", Objective::getType, Type::codec);

    Type<?> getType();

    T getRequirement();

    default void trigger(Player player, Type<?> type, T value) {



    }

    record Type<T extends Objective<?>>(MapCodec<T> codec) {}

}
