package io.teking.eternitek.core.client.screen.codex;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.util.render.RenderHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public class CodexScreen extends Screen {

    public static final Identifier TEXTURE = EternitekCore.id("textures/gui/codex.png");
    private static final int MAX_BAR_Y = 112;

    private Text errorMessage = Text.empty();
    protected int barY = 77;
    protected boolean expanded = false;

    public CodexScreen() {
        super(Text.empty());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        this.renderBackground(context, mouseX, mouseY, delta);

        if(expanded && barY < MAX_BAR_Y) barY++;
        if(!expanded && barY > 77) barY--;

        RenderHelper helper = new RenderHelper(context);

        int bottomWidth = 306;

        int x = (context.getScaledWindowWidth() / 2) - (bottomWidth / 2);
        int y = (context.getScaledWindowHeight() / 2) + barY;

        context.drawTexture(
                TEXTURE,
                x, y, -3,
                0, 226,
                bottomWidth, 42,
                512, 512
        );

        helper.drawTexture(
                TEXTURE,
                RenderHelper.Position.CENTER_CENTER,
                338, 226,
                512, 512
        );

        context.drawText(
                this.textRenderer,
                this.errorMessage,
                5, this.height - 15,
                0xE43B44, true
        );

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        int barWidth = 306;

        int barX = (this.width / 2) - (barWidth / 2);
        int barY = (this.height / 2) + 112;

        if((barX < mouseX && mouseX < barX + barWidth) && (barY < mouseY && mouseY < ((double) this.height / 2) + 112 + (this.barY - 70))) {
            expanded = !expanded;
            return true;
        }

        return super.mouseClicked(mouseX, mouseY, button);

    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return (keyCode == GLFW.GLFW_KEY_E) || super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    protected void setError(Text error) {
        this.errorMessage = error;
    }

}
