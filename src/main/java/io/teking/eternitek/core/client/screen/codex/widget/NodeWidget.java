package io.teking.eternitek.core.client.screen.codex.widget;

import io.teking.eternitek.core.client.screen.codex.CodexScreen;
import io.teking.eternitek.core.util.techtree.TechNode;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.item.ItemStack;

public class NodeWidget extends ClickableWidget {

    private final TechNode node;

    public NodeWidget(TechNode node) {
        super(node.x(), node.y(), 28, 28, node.item().getName());
        this.node = node;
    }

    @Override
    protected void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {

        int x = this.getX();
        int y = this.getY();

        ItemStack stack = node.item();

        context.drawTexture(
                CodexScreen.TEXTURE,
                x, y, 0,
                338, 0, // TO-DO: Update once texture is finalized
                this.getWidth(), this.getHeight(),
                512, 256
        );

        context.drawItem(
                stack,
                x + 6, y + 6
        );

    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
