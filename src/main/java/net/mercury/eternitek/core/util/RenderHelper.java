package net.mercury.eternitek.core.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class RenderHelper {

    public static void pixelLine(GuiGraphicsExtractor graphics, int x0, int y0, int x1, int y1, int color) {
        
        int dx = Math.abs(x1 - x0);
        int sx = x0 < x1 ? 1 : -1;
        int dy = -Math.abs(y1 - y0);
        int sy = y0 < y1 ? 1 : -1;

        int x = x0;
        int y = y0;

        int error = dx + dy;
        int error2;

        while (true) {

            graphics.fill(x, y, x + 1, y + 1, color);

            error2 = 2 * error;

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
            int fullColor, int emptyColor, int shadow
    ) {

        graphics.fill(x + 1, y + 1, x + 1 + width, y + 1 + height, shadow);
        graphics.fill(x, y, x + width, y + height, emptyColor);
        graphics.fill(x, y, x + (int) (width * progress), y + height, fullColor);

    }

    public static void text(GuiGraphicsExtractor graphics, Component text, int x, int y, int color, int shadow) {
        Font font = Minecraft.getInstance().font;
        graphics.text(font, text, x + 1, y + 1, shadow, false);
        graphics.text(font, text, x, y, color, false);
    }

}
