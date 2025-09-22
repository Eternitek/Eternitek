package io.teking.eternitek.core.client.screen.codex;

import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.framebuffer.AdvancedFbo;
import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.client.screen.codex.widget.NodeWidget;
import io.teking.eternitek.core.resource.TechTreeReloadListener;
import io.teking.eternitek.core.util.render.RenderHelper;
import io.teking.eternitek.core.util.techtree.TechNode;
import io.teking.eternitek.core.util.techtree.TechTree;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Drawable;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Identifier;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class TechTreeScreen extends CodexScreen {

    private static final Identifier COG = EternitekCore.id("textures/gui/icon/cog.png");

    private final TechTree tree;

    private boolean isDragging;
    private int offsetX;
    private int offsetY;

    public TechTreeScreen(Identifier treeId) {

        super();

        this.isDragging = false;
        this.tree = TechTreeReloadListener.TREES.get(treeId);

    }

    @Override
    protected void init() {

        super.init();

        for(TechNode node : this.tree.nodes()) {
            int x = (this.width / 2) - 14 + node.x();
            int y = (this.height / 2) - 14 + (-1 * node.y());
            this.addDrawableChild(new NodeWidget(x, y, node));
        }

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        super.render(context, mouseX, mouseY, delta);

        context.enableScissor(
                100 + 1, 27 + 1,
                this.width, this.height
        );

        for(Drawable drawable : this.drawables) {
            if(drawable instanceof NodeWidget node) node.updateOffset(offsetX, offsetY);
            drawable.render(context, mouseX, mouseY, delta);
        }

        context.disableScissor();

        context.fill(
                10, 37,
                110, 54,
                0xFF23674E
        );

        context.drawTexture(
                COG,
                15, 42,
                0, 0,
                7, 7,
                8, 8
        );

        context.drawText(
                textRenderer,
                "Technology",
                27, 42,
                Colors.WHITE,
                false
        );

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        if(isLeft(button)) this.isDragging = true;

        return super.mouseClicked(mouseX, mouseY, button);

    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {

        if(isLeft(button)) {
            this.isDragging = false;
            return true;
        }

        return super.mouseReleased(mouseX, mouseY, button);

    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {

        double width = this.width;

        if(isDragging && isLeft(button)) {
            this.offsetX = (int) Math.clamp(offsetX + deltaX, -width, width);
            this.offsetY = (int) Math.clamp(offsetY + deltaY, -width, width);
            return true;
        }

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);

    }

    private boolean isLeft(int button) {
        return button == GLFW_MOUSE_BUTTON_LEFT;
    }

}
