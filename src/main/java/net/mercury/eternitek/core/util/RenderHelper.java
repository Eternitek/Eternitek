package net.mercury.eternitek.core.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.Mth;
import org.joml.Matrix3x2fStack;

public class RenderHelper {

    public static void text(GuiGraphicsExtractor graphics, String text, int x, int y, int color, int shadow) {
        Font font = Minecraft.getInstance().font;
        graphics.text(font, text, x + 1, y + 1, shadow);
        graphics.text(font, text, x, y, color);
    }

    public static void pixelLine(GuiGraphicsExtractor graphics, int x0, int y0, int x1, int y1, int color) {
        
        float dx = Math.abs(x1 - x0);
        float sx = x0 < x1 ? 1 : -1;
        float dy = -Math.abs(y1 - y0);
        float sy = y0 < y1 ? 1 : -1;

        float x = x0;
        float y = y0;

        float error = dx + dy;

        while (true) {

            graphics.fill((int) x, (int) y, (int) (x + 1), (int) (y + 1), color);

            float error2 = 2.0F * error;

            if (error2 >= dy) {
                if (x == x1) break;
                error += dy;
                x += sx;
            }

            if (error2 <= dx) {
                if (y == y1) break;
                error += dx;
                y += sy;
            }

        }

    }

    public static void progressBar(
            GuiGraphicsExtractor graphics,
            int x, int y,
            int width, int height,
            float progress,
            int fullColor, int emptyColor
    ) {

        graphics.fill(x, y, x + width, y + height, emptyColor);
        graphics.fill(x, y, x + (int) (width * progress), y + height, fullColor);

    }

}
