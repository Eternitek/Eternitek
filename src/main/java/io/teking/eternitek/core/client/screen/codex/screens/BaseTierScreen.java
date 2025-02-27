package io.teking.eternitek.core.client.screen.codex.screens;

import io.teking.eternitek.core.EternitekCore;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public abstract class BaseTierScreen extends Screen {
    protected final CodexScreen parentScreen;
    protected static final Identifier MAIN_PAGE_TEXTURE = EternitekCore.id("textures/gui/codex.png");

    public BaseTierScreen(Text title, CodexScreen parentScreen) {
        super(title);
        this.parentScreen = parentScreen;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawTexture(MAIN_PAGE_TEXTURE, (width - 384) / 2, (height - 256) / 2, 0, 0, 384, 256, 384, 256);
        // Add our other common rendering code here
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.client.setScreen(parentScreen);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}

