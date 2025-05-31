package io.teking.eternitek.core.client.screen.codex.widget;

import io.teking.eternitek.core.client.screen.codex.CodexScreen;
import io.teking.eternitek.core.util.techtree.TechNode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.text.Text;

public class NodeWidget extends ClickableWidget {

    private final TechNode node;

    private final int startX;
    private final int startY;

    private int offsetX;
    private int offsetY;

    public NodeWidget(int x, int y, TechNode node) {
        super(x, y, 28, 28, node.tooltip().getFirst());
        this.startX = x;
        this.startY = y;
        this.visible = true;
        this.node = node;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {

        this.setX(startX + offsetX);
        this.setY(startY + offsetY);

        context.drawTexture(
                CodexScreen.TEXTURE,
                getX(), getY(), 4,
                338, 0, // TO-DO: Update once texture is finalized
                this.getWidth(), this.getHeight(),
                512, 512
        );

        context.drawTexture(
                node.icon(),
                getX() + 6, getY() + 6, 5,
                0, 0,
                16, 16,
                16, 16
        );

        if(isMouseOver(mouseX, mouseY) && !node.tooltip().getFirst().equals(Text.empty())) {
            context.drawTooltip(
                    MinecraftClient.getInstance().textRenderer,
                    node.tooltip(),
                    mouseX, mouseY
            );
        }

    }

    public void updateOffset(int offsetX, int offsetY) {
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
