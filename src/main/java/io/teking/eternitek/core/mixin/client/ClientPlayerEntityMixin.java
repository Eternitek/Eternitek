package io.teking.eternitek.core.mixin.client;

import io.teking.eternitek.core.client.screen.codex.screens.CodexScreen;
import io.teking.eternitek.core.registry.EternitekItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Shadow @Final protected MinecraftClient client;

    @Inject(method = "useBook", at = @At("TAIL"))
    public void eternitek$openCodex(ItemStack book, Hand hand, CallbackInfo ci) {
        if(book.isOf(EternitekItems.CODEX)) {
            this.client.setScreen(new CodexScreen());
        }
    }

}
