package io.teking.eternitek.core.util.render;

import io.teking.eternitek.core.block.Tooltipped;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class RenderHelper {

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
