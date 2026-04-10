package net.mercury.eternitek.core.codex.gui.widget;

import net.mercury.eternitek.core.EternitekCore;
import net.mercury.eternitek.core.util.RenderHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;

public class QuestWidget extends AbstractWidget {

    public QuestWidget(Component message) {
        super(0, 0, 112, 48, message);
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {

        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                EternitekCore.id("textures/gui/sprites/quest/background.png"),
                getX(), getY(),
                0, 0,
                112, 48,
                112, 48
        );

        RenderHelper.text(
                graphics,
                Component.literal("Mine Iron"),
                getX() + 8, getY() + 8,
                0xFFFFFFFF,
                0xAA000000
        );

        RenderHelper.progressBar(
                graphics,
                getX() + 8, getY() + 32,
                80, 2,
                101F / 150F,
                0xFF92E8C0, 0xFF436B59, 0xAA000000
        );

    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput output) {

    }

}
