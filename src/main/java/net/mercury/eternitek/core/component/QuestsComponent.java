package net.mercury.eternitek.core.component;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.entity.RespawnableComponent;

public class QuestsComponent implements AutoSyncedComponent, RespawnableComponent<QuestsComponent> {

    private final Player player;

    public QuestsComponent(Player player) {
        this.player = player;
    }

    @Override
    public void readData(ValueInput input) {

    }

    @Override
    public void writeData(ValueOutput output) {

    }

}
