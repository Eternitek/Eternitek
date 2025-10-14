package io.teking.eternitek.core.util.render;

import io.teking.eternitek.core.util.techtree.TechNode;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;

@Environment(EnvType.CLIENT)
public class RenderHelper {

    private DrawContext context;

    public RenderHelper(DrawContext context) {
        this.context = context;
    }

    public void drawBarVertical(int x, int barWidth, int barColor, int lineColor) {
        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();
        context.fill(x, 0, x + barWidth, height, barColor);
        if (x > 0) context.drawVerticalLine(x, 0, height, lineColor);
        if (x < width) context.drawVerticalLine(x + barWidth, 0, height, lineColor);
    }

    public void drawBarHorizontal(int y, int barHeight, int barColor, int lineColor) {
        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();
        context.fill(0, y, width, y + barHeight, barColor);
        if (y > 0) context.drawHorizontalLine(0, width, y, lineColor);
        if (y < height) context.drawHorizontalLine(0, width, y + barHeight, lineColor);
    }

    public void drawTexture(Identifier texture, Position pos, int width, int height, int textureWidth, int textureHeight) {

        int windowWidth = context.getScaledWindowWidth();
        int windowHeight = context.getScaledWindowHeight();

        context.drawTexture(
                texture,
                (int) ((windowWidth * pos.multiplierX) - (width * pos.multiplierX)),
                (int) ((windowHeight * pos.multiplierY) - (height * pos.multiplierY)),
                0, 0, 0,
                width, height,
                textureWidth, textureHeight
        );

    }

    public void drawConnectingLine(TechNode start, TechNode end, int width, int color) {
        drawConnectingLine(start.x(), start.y(), end.x(), end.y(), width, color);
    }

    public void drawConnectingLine(int startX, int startY, int endX, int endY, int width, int color) {

        Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR);

        buffer.vertex(startX, startY, 5).color(color);
        buffer.vertex(endX, endY, 5).color(color);



        BufferRenderer.draw(buffer.end());

    }

    public enum Position {

        TOP_LEFT(0.0F, 0.0F),    TOP_CENTER(0.5F, 0.0F),    TOP_RIGHT(1.0F, 0.0F),

        CENTER_LEFT(0.0F, 0.5F), CENTER_CENTER(0.5F, 0.5F), CENTER_RIGHT(1.0F, 0.5F),

        BOTTOM_LEFT(0.0F, 1.0F), BOTTOM_CENTER(0.5F, 1.0F), BOTTOM_RIGHT(1.0F, 1.0F);

        public final float multiplierX;
        public final float multiplierY;

        Position(float multiplierX, float multiplierY) {
            this.multiplierX = multiplierX;
            this.multiplierY = multiplierY;
        }

    }

}
