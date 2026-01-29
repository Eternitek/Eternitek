package net.mercury.eternitek.core.mixin.client;

import net.mercury.eternitek.core.codex.gui.CodexScreen;
import net.mercury.eternitek.core.registry.EternitekItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Shadow @Final protected Minecraft client;

    @Inject(method = "openItemGui(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;)V", at = @At("TAIL"))
    public void eternitek$openCodex(ItemStack stack, InteractionHand hand, CallbackInfo info) {
        if (stack.is(EternitekItems.CODEX)) {
            this.client.setScreen(new CodexScreen());
        }
    }

}
