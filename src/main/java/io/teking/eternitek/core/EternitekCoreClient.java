package io.teking.eternitek.core;

import io.teking.eternitek.core.block.Tooltipped;
import io.teking.eternitek.core.util.render.RenderHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import org.joml.Vector2i;

public class EternitekCoreClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HudRenderCallback.EVENT.register(EternitekCoreClient::renderBlockTooltip);
    }

    private static void renderBlockTooltip(DrawContext context, RenderTickCounter counter) {

        MinecraftClient client = MinecraftClient.getInstance();
        TextRenderer textRenderer = client.textRenderer;

        if(client.currentScreen != null) return;
        if(!(getLookingAt(client.player).getBlock() instanceof Tooltipped block)) return;
        if(!client.player.isSneaking()) return;

        int windowWidth = context.getScaledWindowWidth();
        int windowHeight = context.getScaledWindowHeight();

        int x = (windowWidth / 2) + 20;
        int y = (windowHeight / 2);

        RenderHelper.flipRender();

        context.drawTooltip(
                textRenderer,
                block.getTooltip(getLookingAt(client.player)),
                (screenWidth, screenHeight, tipX, tipY, width, height) -> new Vector2i(x, y - (height / 2)),
                x, y
        );

        RenderHelper.flipRender();

    }

    private static BlockState getLookingAt(PlayerEntity player) {
        HitResult hitResult = player.raycast(player.getBlockInteractionRange(), 0.0F, false);
        if(!(hitResult instanceof BlockHitResult blockHitResult)) return Blocks.AIR.getDefaultState();
        return player.getWorld().getBlockState(blockHitResult.getBlockPos());
    }

}
