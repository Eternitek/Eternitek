package io.teking.eternitek.core.util.render;

import com.mojang.blaze3d.systems.RenderSystem;
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
import org.joml.Vector2i;

@Environment(EnvType.CLIENT)
public class RenderHelper {

    private DrawContext context;

    public RenderHelper(DrawContext context) {
        this.context = context;
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

            matrices.translate(startX, startY, 0);
            matrices.translate(windowWidthCenter, windowHeightCenter, 0);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotation(angle));

            context.fill(0, -(width / 2), lineLength, (width / 2), color);

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
