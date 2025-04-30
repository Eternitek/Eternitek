package io.teking.eternitek.core;

import io.teking.eternitek.core.block.BlockWithMultiblock;
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
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import org.joml.Vector2i;

import java.util.ArrayList;
import java.util.List;

public class EternitekCoreClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

    }

}
