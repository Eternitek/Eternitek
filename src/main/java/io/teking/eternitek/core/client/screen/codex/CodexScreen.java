package io.teking.eternitek.core.client.screen.codex;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.client.screen.codex.widget.NodeWidget;
import io.teking.eternitek.core.util.render.RenderHelper;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.util.Identifier;

public class CodexScreen extends Screen {

    public static final Identifier TEXTURE = EternitekCore.id("textures/gui/codex.png");

    private Text errorMessage;

    public CodexScreen() {

        super(Text.empty());

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        super.render(context, mouseX, mouseY, delta);

        RenderHelper helper = new RenderHelper(context);

        helper.drawTexture(
                TEXTURE,
                RenderHelper.Position.CENTER_CENTER,
                338, 226,
                512, 256
        );

        context.drawText(
                this.textRenderer,
                this.errorMessage,
                5, this.height - 15,
                0xE43B44, true
        );

    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    protected void setError(Text error) {
        this.errorMessage = error;
    }

}
