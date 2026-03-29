package net.mercury.eternitek.core.codex.quest.objectives;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.mercury.eternitek.core.registry.EternitekRegistries;

public interface Objective {

    Codec<Objective> CODEC = EternitekRegistries.OBJECTIVE_TYPE.byNameCodec()
            .dispatch(Objective::getType, Type::codec);

    Type<?> getType();

    record Type<T extends Objective>(MapCodec<T> codec) {}

}
