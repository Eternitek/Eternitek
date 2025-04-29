package io.teking.eternitek.core.mixin.client;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.client.screen.codex.CodexScreen;
import io.teking.eternitek.core.client.screen.codex.TechTreeScreen;
import io.teking.eternitek.core.registry.EternitekItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Shadow @Final protected MinecraftClient client;

    @Inject(method = "useBook", at = @At("TAIL"))
    public void eternitek$openCodex(ItemStack book, Hand hand, CallbackInfo ci) {
        if(book.isOf(EternitekItems.CODEX)) {
            this.client.setScreen(new TechTreeScreen(EternitekCore.id("tier_0")));
        }
    }

}
