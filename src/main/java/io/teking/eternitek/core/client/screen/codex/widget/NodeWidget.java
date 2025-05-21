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

    public NodeWidget(int x, int y, TechNode node) {
        super(x, y, 28, 28, node.tooltip().getFirst());
        this.visible = true;
        this.node = node;
    }

    @Override
    public void renderWidget(DrawContext context, int mouseX, int mouseY, float delta) {

        int windowWidthCenter = (context.getScaledWindowWidth() / 2) - (this.width / 2);
        int windowHeightCenter = (context.getScaledWindowHeight() / 2) - (this.height / 2);

        int readX = this.node.x();
        int readY = this.node.y() * -1; // We multiply by -1 to make this more consistent with regular rectangular coordinates
                                        // (i.e. (0, -50) is down on the screen rather than up)

        int x = windowWidthCenter + readX;
        int y = windowHeightCenter + readY;

        context.drawTexture(
                CodexScreen.TEXTURE,
                x, y, 4,
                338, 0, // TO-DO: Update once texture is finalized
                this.getWidth(), this.getHeight(),
                512, 512
        );

        context.drawTexture(
                node.icon(),
                x + 6, y + 6, 5,
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

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

}
