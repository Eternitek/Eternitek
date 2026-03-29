package net.mercury.eternitek.core.registry;

import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.component.QuestsComponent;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public class EternitekComponents implements EntityComponentInitializer {

    public static final ComponentKey<QuestsComponent> QUESTS =
            ComponentRegistry.getOrCreate(EternitekCore.id("quests"), QuestsComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(QUESTS, QuestsComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    }

}
