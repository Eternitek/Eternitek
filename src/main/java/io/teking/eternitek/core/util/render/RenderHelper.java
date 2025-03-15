package io.teking.eternitek.core.util.render;

import foundry.veil.api.client.color.ColorTheme;
import io.teking.eternitek.core.block.Tooltipped;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
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

        MatrixStack matrices = context.getMatrices();

        int x = endX - startX;
        int y = endY - startY;

        float angle = (float) Math.atan2(y, x);
        float lineLength = (float) MathHelper.hypot(x, y);

        matrices.push(); // Push a new matrix to the stack to keep transformations local

            matrices.translate(startX, startY, 0);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(angle));

            context.fill(0, -width / 2, (int) lineLength, width / 2, color);

        matrices.pop();

    }

}
