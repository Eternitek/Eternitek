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

    protected int offsetX = 0;
    protected int offsetY = 0;
    protected boolean isDragging = false;
    protected int lastMouseX, lastMouseY;

    public BaseTierScreen(Text title, CodexScreen parentScreen) {
        super(title);
        this.parentScreen = parentScreen;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        // Draw the main codex background
        context.drawTexture(MAIN_PAGE_TEXTURE, (width - 384) / 2, (height - 256) / 2, 0, 0, 384, 256, 384, 256);

        // Apply translation for tech tree content
        context.getMatrices().push();
        context.getMatrices().translate(offsetX, offsetY, 0);

        // Render tech tree content here
        renderTechTree(context, mouseX - offsetX, mouseY - offsetY, delta);

        context.getMatrices().pop();
    }

    protected abstract void renderTechTree(DrawContext context, int mouseX, int mouseY, float delta);

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // Left mouse button
            isDragging = true;
            lastMouseX = (int) mouseX;
            lastMouseY = (int) mouseY;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) { // Left mouse button
            isDragging = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isDragging && button == 0) {
            offsetX += mouseX - lastMouseX;
            offsetY += mouseY - lastMouseY;
            lastMouseX = (int) mouseX;
            lastMouseY = (int) mouseY;
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
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
