package net.mercury.eternitek.core.util;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2fStack;

public class RenderHelper {

    public static void line(GuiGraphicsExtractor graphics, int x0, int y0, int x1, int y1, int width, int color) {

        Matrix3x2fStack matrices = graphics.pose();

        int dx = x1 - x0;
        int dy = y1 - y0;

        float angle = (float) Math.atan2(dy, dx);
        int length = (int) Mth.length(dx, dy);

        matrices.pushMatrix();

            matrices.translate(x0, y0);

            matrices.rotate(angle);

            int halfWidth = width / 2;
            graphics.fill(0, -halfWidth, length, halfWidth, color);

        matrices.popMatrix();

    }

    public static void pixelLine(GuiGraphicsExtractor graphics, int x0, int y0, int x1, int y1, int color) {

        y0 -= 1;
        y1 -= 1;
        
        int dx = Math.abs(x1 - x0);
        int sx = x0 < x1 ? 1 : -1;
        int dy = -Math.abs(y1 - y0);
        int sy = y0 < y1 ? 1 : -1;

        int error = dx + dy;

        while (true) {

            graphics.fill(x0, y0, x0 + 1, y0 + 1, color);

            int error2 = 2 * error;

            if (error2 >= dy) {
                if (x0 == x1) break;
                error += dy;
                x0 += sx;
            }

            if (error2 <= dx) {
                if (y0 == y1) break;
                error += dx;
                y0 += sy;
            }

        }

    }

}
