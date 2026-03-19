package net.mercury.eternitek.core.item;

import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.codex.gui.CodexScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

public class CodexItem extends Item {

    public CodexItem(Properties properties) {
        super(properties
                .stacksTo(1)
        );
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (level.isClientSide()) {
            Minecraft.getInstance().setScreen(new CodexScreen(EternitekCore.id("keycards")));
        }
        return InteractionResult.SUCCESS;
    }

}
