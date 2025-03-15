package io.teking.eternitek.core.util.render;

import foundry.veil.api.client.color.ColorTheme;
import io.teking.eternitek.core.block.Tooltipped;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import org.joml.Quaternionf;

import java.util.List;

import static net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer.*;
import static net.minecraft.client.gui.DrawContext.*;

@Environment(EnvType.CLIENT)
public class RenderHelper {

    public static Tooltipped.Palette currentColors;

    public static void renderTooltipBackground(DrawContext context, int x, int y, int width, int height, int z) {

        int leftEdge = x - 3;
        int topEdge = y - 3;
        int trueWidth = width + 3 + 3;
        int trueHeight = height + 3 + 3;

        int lightColor = currentColors.borderLight();
        int darkColor  = currentColors.borderDark();
        int background = currentColors.backgroundColor();

        renderHorizontalLine(context, leftEdge, topEdge - 1, trueWidth, z, background);
        renderHorizontalLine(context, leftEdge, topEdge + trueHeight, trueWidth, z, background);

        renderRectangle(context, leftEdge, topEdge, trueWidth, trueHeight, z, background);

        renderVerticalLine(context, leftEdge - 1, topEdge, trueHeight, z, background);
        renderVerticalLine(context, leftEdge + trueWidth, topEdge, trueHeight, z, background);

        renderBorder(context, leftEdge, topEdge + 1, trueWidth, trueHeight, z, lightColor, darkColor);

    }

    public static void setPalette(Tooltipped.Palette palette) {
        currentColors = palette;
    }

    public static void drawConnectingLine(DrawContext context, int startX, int startY, int endX, int endY, int width, int color) {
        float angle = (float) Math.atan2(endY - startY, endX - startX);
        float lineLength = (float) Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));
        // Save the transformation matrix
        context.getMatrices().push();
        // Perform the transformations
        context.getMatrices().translate(startX, startY, 0);
        context.getMatrices().multiply(new Quaternionf().rotationZ(angle));

        // Draw the rectangle
        context.fill(0, -width / 2, (int) lineLength, width / 2, color);

        // Restore the transformation matrix
        context.getMatrices().pop();
    }

}
