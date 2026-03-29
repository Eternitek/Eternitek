package net.mercury.eternitek.core.registry;

import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.codex.quest.objectives.KillEntitiesObjective;
import net.mercury.eternitek.core.codex.quest.objectives.Objective;
import net.minecraft.core.Registry;

import static net.mercury.eternitek.core.codex.quest.objectives.Objective.Type;

public class EternitekObjectives {

    public static final Type<KillEntitiesObjective> KILL_ENTITIES =
            registerObjective("kill_entities", new Type<>(KillEntitiesObjective.CODEC));

    public static void register() {



    }

    public static <T extends Objective> Type<T> registerObjective(String name, Type<T> type) {
        return Registry.register(EternitekRegistries.OBJECTIVE_TYPE, EternitekCore.id(name), type);
    }

}
