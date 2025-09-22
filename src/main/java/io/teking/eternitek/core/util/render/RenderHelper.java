package io.teking.eternitek.core.util.render;

import io.teking.eternitek.core.util.techtree.TechNode;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

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
        drawConnectingLine(start, end, width, color, color);
    }

    public void drawConnectingLine(TechNode start, TechNode end, int width, int colorStart, int colorEnd) {
        drawConnectingLine(start.x(), start.y(), end.x(), end.y(), width, colorStart, colorEnd);
    }

    public void drawConnectingLine(int startX, int startY, int endX, int endY, int width, int colorStart, int colorEnd) {

        MatrixStack matrices = context.getMatrices();

        startY *= -1;
        endY *= -1;

        int windowWidthCenter = (context.getScaledWindowWidth() / 2);
        int windowHeightCenter = (context.getScaledWindowHeight() / 2);

        int x = endX - startX;
        int y = endY - startY;

        float angle = (float) Math.atan2(y, x);
        int lineLength = (int) MathHelper.hypot(x, y);

        matrices.push();

            matrices.translate(startX, startY, 1);
            matrices.translate(windowWidthCenter, windowHeightCenter, 0);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(angle));
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-90.0F));

            context.fillGradient(-(width / 2), 0, (width / 2), lineLength, colorStart, colorEnd); // TO-DO: Figure out pixelated lines?

        matrices.pop();

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
