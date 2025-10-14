package io.teking.eternitek.core.client.screen.codex.widget;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ContainerWidget;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.List;

public class BarWidget extends ContainerWidget {

    private List<Element> children;
    private int borderColor;
    private int fillColor;

    public BarWidget(int x, int y, int width, int height, Text message, int borderColor, int fillColor) {
        super(x, y, width, height, message);
        this.borderColor = borderColor;
        this.fillColor = fillColor;
    }

    public static BarWidget createHorizontal(Screen screen, int y, int height, Text message, int borderColor, int fillColor) {
        return new BarWidget(0, y, screen.width, height, message, borderColor, fillColor);
    }

    public static BarWidget createVertical(Screen screen, int x, int width, Text message, int borderColor, int fillColor) {
        return new BarWidget(x, 0, width, screen.height, message, borderColor, fillColor);
    }

    @Override
    public List<? extends Element> children() {
        return children;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {

        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();

        context.fill(
                this.getX(),
                this.getY(),
                this.getX() + this.getWidth(),
                this.getY() + this.getHeight(),
                this.fillColor
        );

        context.drawHorizontalLine(0, width, this.getY() + this.getHeight(), this.borderColor);

    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
