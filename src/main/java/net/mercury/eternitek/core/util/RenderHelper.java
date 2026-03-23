package net.mercury.eternitek.core.util;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2fStack;

public class RenderHelper {

    public static void line(GuiGraphicsExtractor graphics, int x1, int y1, int x2, int y2, int width, int color) {

        Matrix3x2fStack matrices = graphics.pose();

        int dx = x2 - x1;
        int dy = y2 - y1;

        float angle = (float) Math.atan2(dy, dx);
        int length = (int) Mth.length(dx, dy);

        matrices.pushMatrix();

            matrices.translate(x1, y1);

            matrices.rotate(angle);

            int halfWidth = width / 2;
            graphics.fill(0, -halfWidth, length, halfWidth, color);

        matrices.popMatrix();

    }

}
