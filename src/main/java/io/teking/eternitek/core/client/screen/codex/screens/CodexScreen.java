package io.teking.eternitek.core.client.screen.codex.screens;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.registry.EternitekItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.Font;
import net.minecraft.client.font.FontStorage;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBook;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

import static net.minecraft.util.math.ColorHelper.Argb.*;

@Environment(EnvType.CLIENT)
public class CodexScreen extends Screen {

    private static final Identifier BOOK_TEXTURE = EternitekCore.id("textures/gui/codex.png");

    public CodexScreen() {
        super(Text.of("Codex"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        super.render(context, mouseX, mouseY, delta);

        TextRenderer renderer = MinecraftClient.getInstance().textRenderer;

        int bookHeight = 208;
        int bookWidth = 352;

        int screenHeight = context.getScaledWindowHeight();
        int screenWidth = context.getScaledWindowWidth();

        int centerX = screenWidth / 2;
        int centerY = screenHeight / 2;

        int bookRenderX = centerX - (bookWidth / 2);
        int bookRenderY = centerY - (bookHeight / 2);

        context.drawTexture(
                BOOK_TEXTURE,
                bookRenderX, bookRenderY, 0,
                0, 0,
                bookWidth, bookHeight,
                512, 256
        );

        renderText(renderer, context, Text.of("Crude Steel"), bookRenderX + 12, bookRenderY + 12, 0x171717);

        renderShapedRecipe(
                EternitekItems.CRUDE_STEEL_INGOT.getDefaultStack(),
                List.of(
                        Items.IRON_INGOT.getDefaultStack(), Items.CHARCOAL.getDefaultStack(), ItemStack.EMPTY,
                        Items.CHARCOAL.getDefaultStack(), Items.IRON_INGOT.getDefaultStack()
                ),
                context, bookRenderX, bookRenderY
        );

    }

    private static void renderText(TextRenderer renderer, DrawContext context, Text text, int x, int y, int color) {

        context.drawText(renderer, text, x, y, color, false);

        int lightColor = withAlpha(50, color);
        context.drawText(renderer, text, x - 1, y, lightColor, false);
        context.drawText(renderer, text, x + 1, y, lightColor, false);
        context.drawText(renderer, text, x, y - 1, lightColor, false);
        context.drawText(renderer, text, x, y + 1, lightColor, false);

    }

    private static void renderWrappedText(TextRenderer renderer, DrawContext context, String string, int x, int y, int color) {

        List<String> words = List.of(string.split(" "));

    }

    private static void renderShapedRecipe(ItemStack output, List<ItemStack> inputs, DrawContext context, int bookRenderX, int bookRenderY) {

        int offsetX = 30;
        int offsetY = 72;

        context.drawTexture(
                BOOK_TEXTURE,
                bookRenderX + offsetX, bookRenderY + offsetY, 0,
                352, 0,
                112, 64,
                512, 256
        );

        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                int index = i * 3 + j;
                if(inputs.size() <= index) break;
                ItemStack stack = inputs.get(index);
                context.drawItem(
                        stack,
                        (bookRenderX + offsetX + 5) + (19 * j),
                        (bookRenderY + offsetY + 5) + (19 * i)
                );
            }
        }

        context.drawItem(
                output,
                bookRenderX + offsetX + 91,
                bookRenderY + offsetY + 24
        );

    }

    @Override
    public boolean shouldPause() {
        return false;
    }

}
