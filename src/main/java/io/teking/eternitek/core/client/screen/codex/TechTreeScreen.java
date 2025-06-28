package io.teking.eternitek.core.client.screen.codex;

import foundry.veil.api.client.render.VeilRenderSystem;
import foundry.veil.api.client.render.VeilRenderer;
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
import net.minecraft.util.Identifier;

import static org.lwjgl.glfw.GLFW.*;

public class TechTreeScreen extends CodexScreen {

    private static final Identifier PIXELATE_PIPELINE = EternitekCore.id("pixelate");

    private final TechTree tree;

    private boolean isDragging;
    private int offsetX;
    private int offsetY;

    public TechTreeScreen(Identifier treeId) {

        super();

        this.isDragging = false;
        this.tree = TechTreeReloadListener.TREES.get(treeId);
        if(tree == null) setError(Text.translatable("texts.eternitek.no_tree", treeId));

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

        context.getMatrices().push();

            context.getMatrices().translate(offsetX, offsetY, 0);

            RenderHelper helper = new RenderHelper(context);
            AdvancedFbo fbo = VeilRenderSystem.renderer().getFramebufferManager().getFramebuffer(EternitekCore.id("pixelate"));

            if(fbo != null) fbo.bind(true);

            for(TechNode node : this.tree.nodes()) {
                if(node.connections().contains(EternitekCore.id("root"))) continue;
                for(Identifier connect : node.connections()) {
                    if(!this.tree.getNodeMap().containsKey(connect)) continue;
                    helper.drawConnectingLine(node, this.tree.getNodeMap().get(connect), 2, 0xFF27374D);
                }
            }

            AdvancedFbo.unbind();

            VeilRenderSystem.renderer().getPostProcessingManager().add(-100000, PIXELATE_PIPELINE);

            context.getMatrices().translate(-offsetX, -offsetY, 0);

            for(Drawable drawable : this.drawables) {
                if(drawable instanceof NodeWidget node) node.updateOffset(offsetX, offsetY);
                drawable.render(context, mouseX, mouseY, delta);
            }

        context.getMatrices().pop();

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

        if(isDragging && isLeft(button)) {
            this.offsetX = (int) Math.clamp(offsetX + deltaX, -200, 200);
            this.offsetY = (int) Math.clamp(offsetY + deltaY, -100, 100);
            return true;
        }

        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);

    }

    private boolean isLeft(int button) {
        return button == GLFW_MOUSE_BUTTON_LEFT;
    }

    @Override
    public void removed() {
        VeilRenderSystem.renderer().getPostProcessingManager().remove(PIXELATE_PIPELINE);
    }

}
