package io.teking.eternitek.core.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.datafixers.util.Pair;
import io.teking.eternitek.core.EternitekCore;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(targets = "net.minecraft.screen.PlayerScreenHandler$1")
public class AnonymousSlotMixin {

    @ModifyReturnValue(method = "getBackgroundSprite", at = @At("RETURN"))
    private Pair<Identifier, Identifier> eternitek$modifyBackgroundSprite(Pair<Identifier, Identifier> original) {
        return Pair.of(original.getFirst(), EternitekCore.id("item/slot/offhand"));
    }

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/slot/Slot;<init>(Lnet/minecraft/inventory/Inventory;III)V"))
    private static void eternitek$modifyOffhandSlotPosition(Args args) {
        int x = 77 + 8;
        int y = 62 - 4;
        args.set(2, x);
        args.set(3, y);
    }

}
