package io.teking.eternitek.core.client.screen.codex.screens;

import io.teking.eternitek.core.util.screen.handler.PipeControllerScreenHandler;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;

public class PipeControllerScreen extends Screen {
    private final PipeControllerScreenHandler handler;
    private boolean isPulling = true; // Default state: pulling items

    public PipeControllerScreen(PipeControllerScreenHandler handler, PlayerInventory inventory, Text title) {
        super(title);
        this.handler = handler; // Save reference to handler for interaction
    }

    @Override
    protected void init() {
        // Add a button to toggle between "Sucking" and "Emptying"
        this.addDrawableChild(ButtonWidget.builder(Text.literal(isPulling ? "Sucking" : "Emptying"), button -> {
            isPulling = !isPulling; // Toggle the mode
            button.setMessage(Text.literal(isPulling ? "Sucking" : "Emptying"));
        }).dimensions(this.width / 2 - 50, this.height / 2 - 20, 100, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF); // Draw title
        super.render(context, mouseX, mouseY, delta); // Render widgets
    }

    @Override
    public boolean shouldPause() {
        return false; // Keep the game running while the GUI is open
    }
}