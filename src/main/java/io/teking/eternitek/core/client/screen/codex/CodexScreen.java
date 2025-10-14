package io.teking.eternitek.core.client.screen.codex;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.util.render.RenderHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.awt.*;

public class CodexScreen extends Screen {

    public static final Identifier TEXTURE = EternitekCore.id("textures/gui/codex.png");

    public CodexScreen() {
        super(Text.empty());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        RenderHelper helper = new RenderHelper(context);

        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();

        context.fill(0, 0, width, height, 0xCC000000);

        helper.drawBarVertical(
                0, 100,
                Colors.BLACK, Colors.WHITE
        );

        helper.drawBarHorizontal(
                0, 27,
                Colors.BLACK, Colors.WHITE
        );

        context.drawText(
                textRenderer,
                "MIMIR",
                10, 10,
                Colors.WHITE,
                false
        );



    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return (keyCode == GLFW.GLFW_KEY_E) || super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

}
