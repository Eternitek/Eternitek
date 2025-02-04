package io.teking.eternitek.core.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.teking.eternitek.core.EternitekCore;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> {

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Shadow
    public static void drawEntity(DrawContext context, int x1, int y1, int x2, int y2, int size, float f, float mouseX, float mouseY, LivingEntity entity) {
    }

    @Shadow private float mouseX;
    @Shadow private float mouseY;
    @Unique
    private static final Identifier BACKGROUND = EternitekCore.id("textures/gui/inventory.png");

    @WrapOperation(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/ingame/InventoryScreen;addDrawableChild(Lnet/minecraft/client/gui/Element;)Lnet/minecraft/client/gui/Element;"))
    private Element eternitek$cancelAddWidget(InventoryScreen instance, Element element, Operation<Element> original) {
        return element;
    }

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

        drawEntity(
                context,
                drawX + 32,
                drawY + 9,
                drawX + 86,
                drawY + 81,
                30,
                0.0625F,
                this.mouseX,
                this.mouseY,
                this.client.player
        );

        ci.cancel();

    }

    @Inject(method = "drawForeground", at = @At("HEAD"), cancellable = true)
    private void eternitek$cancelTitleRender(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
        ci.cancel();
    }

}
