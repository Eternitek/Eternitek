package net.mercury.eternitek.core.registry;

import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.codex.quest.objective.MiningObjective;
import net.mercury.eternitek.core.codex.quest.objective.Objective;
import net.minecraft.core.Registry;

import static net.mercury.eternitek.core.codex.quest.objective.Objective.Type;

public class EternitekObjectives {

    public static final Type<MiningObjective> MINING =
            registerObjective("mining", new Type<>(MiningObjective.CODEC));

    public static void register() {

    }

    public static <T extends Objective<?>> Type<T> registerObjective(String name, Type<T> objective) {
        return Registry.register(EternitekRegistries.OBJECTIVE, EternitekCore.id(name), objective);
    }

}
