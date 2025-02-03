package io.teking.eternitek.core.mixin.client;

import io.teking.eternitek.core.EternitekCore;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class InventoryScreenMixin {

    @Unique
    private static final Identifier BACKGROUND = EternitekCore.id("textures/gui/inventory.png");

    @Inject(method = "drawBackground", at = @At("HEAD"), cancellable = true)
    private void eternitek$changeInventoryBackground(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {

        int spriteWidth = 188;
        int spriteHeight = 178;

        int centerX = context.getScaledWindowWidth() / 2;
        int centerY = context.getScaledWindowHeight() / 2;

        int drawX = centerX - (spriteWidth / 2);
        int drawY = centerY - (spriteHeight / 2);

        context.drawTexture(
                BACKGROUND,
                drawX, drawY,
                0, 0,
                spriteWidth, spriteHeight,
                256, 256
        );

        ci.cancel();

    }

    @Inject(method = "drawForeground", at = @At("HEAD"), cancellable = true)
    private void eternitek$cancelTitleRender(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
        ci.cancel();
    }

}
