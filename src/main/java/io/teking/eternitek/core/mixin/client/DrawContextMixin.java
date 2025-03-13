package io.teking.eternitek.core.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.teking.eternitek.core.util.render.RenderHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipPositioner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(DrawContext.class)
public class DrawContextMixin {

    @WrapOperation(method = "method_51743", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/tooltip/TooltipBackgroundRenderer;render(Lnet/minecraft/client/gui/DrawContext;IIIII)V"))
    public void eternitek$drawTooltipBackground(DrawContext context, int x, int y, int width, int height, int z, Operation<Void> original) {
        if(RenderHelper.currentColors != null) {
            RenderHelper.renderTooltipBackground(context, x, y, width, height, z);
        } else {
            original.call(context, x, y, width, height, z);
        }
    }

}
