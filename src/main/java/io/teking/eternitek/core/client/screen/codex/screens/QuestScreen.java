package io.teking.eternitek.core.client.screen.codex.screens;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class QuestScreen extends Screen {

    private final Screen parent;
    private final Identifier questId;

    public QuestScreen(Screen parent, Identifier questId) {
        super(Text.of("Quest: " + questId));
        this.parent = parent;
        this.questId = questId;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(
                this.textRenderer,
                "Quest: " + questId,
                this.width / 2,
                this.height / 2,
                0xFFFFFF
        );
    }

    @Override
    public boolean shouldCloseOnEsc() {
        MinecraftClient.getInstance().setScreen(parent);
        return true;
    }

}