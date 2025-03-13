package io.teking.eternitek.core.client.screen.codex.screens;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.machine.Tier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

import static io.teking.eternitek.core.machine.Tier.*;

@Environment(EnvType.CLIENT)
public class CodexScreen extends Screen {

    private static final Identifier CODEX_TEXTURE = EternitekCore.id("textures/gui/codex.png");
    public static final Identifier BOOK_ICON = EternitekCore.id("textures/item/codex.png");

    private static final int SIDEBAR_COLLAPSED_WIDTH = 26;
    private static final int SIDEBAR_EXPANDED_WIDTH = 100;
    private int currentSidebarWidth = SIDEBAR_COLLAPSED_WIDTH;

    private boolean isSidebarExpanded = false;

    private final int mainPageWidth = 385;
    private final int bookHeight = 256;

    private int bookRenderX;
    private int bookRenderY;

    private final List<Tier> tiers = List.of(T0, T1, T2, T3, T4, T5, T6, T7, T8);

    private int selectedTier = -1;

    public CodexScreen() {
        super(Text.of("Codex"));
    }

    @Override
    protected void init() {
        super.init();
        bookRenderX = (this.width - mainPageWidth) / 2;
        bookRenderY = (this.height - bookHeight) / 2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        super.render(context, mouseX, mouseY, delta);

        int textColor = 0xFFCBC6C1;
        int selectColor = 0xFFA69E9A;
        int shadowColor = 0xFF595757;

        if(isSidebarExpanded) {
            if(currentSidebarWidth < SIDEBAR_EXPANDED_WIDTH) currentSidebarWidth += 2;
        } else {
            if(currentSidebarWidth > SIDEBAR_COLLAPSED_WIDTH) currentSidebarWidth -= 2;
        }

        context.drawTexture(
                CODEX_TEXTURE,
                bookRenderX, bookRenderY,
                0, 0,
                mainPageWidth, bookHeight,
                512, 256
        );

        context.drawTexture(
                CODEX_TEXTURE,
                (bookRenderX + 370) - currentSidebarWidth, bookRenderY + 5,
                385, 0,
                8, 246,
                512, 256
        );

        int x = (bookRenderX + 370 - currentSidebarWidth) + 8;

        context.enableScissor(
                x,
                bookRenderY + 7,
                x + currentSidebarWidth,
                bookRenderY + 5 + 244
        );

        for(int i = 0; i < tiers.size(); i++) {

            TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
            int iconSize = 16;
            int renderX = x + 5;

            Tier tier = tiers.get(i);

            int y = bookRenderY + 7 + 5 + (i * 24);

            if(i == selectedTier) {
                context.fill(
                        x - 1, y - 4,
                        x + currentSidebarWidth + 1, y + 20,
                        selectColor
                );
            }

            context.fill(
                    renderX + 1, y + 1,
                    renderX + iconSize + 1, y + iconSize + 1,
                    shadowColor
            );

            context.drawTexture(
                    tier.icon,
                    renderX, y,
                    0, 0,
                    iconSize, iconSize,
                    iconSize, iconSize
            );

            if(currentSidebarWidth != SIDEBAR_COLLAPSED_WIDTH) {
                context.drawTextWithShadow(
                        renderer,
                        tier.name,
                        renderX + 24, y + 4,
                        textColor
                );
            }


        }

        context.disableScissor();

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        int sidebarX = bookRenderX + 370 - currentSidebarWidth;
        int sidebarY = bookRenderY + 5;

        if((sidebarX < mouseX && mouseX < (sidebarX + 8)) && (sidebarY < mouseY && mouseY < (sidebarY + 246))) {
            isSidebarExpanded = !isSidebarExpanded;
            return true;
        }

        for (int i = 0; i < tiers.size(); i++) {

            int x = bookRenderX + 370 - currentSidebarWidth + 8 + 5;
            int y = bookRenderY + 7 + 5 + (i * 24);
            if((x < mouseX && mouseX < (x + currentSidebarWidth)) && ((y - 4) < mouseY && mouseY < (y + 20))) {
                selectedTier = i;
                openTierScreen(i);
            }

            if(y > bookRenderY + bookHeight) {
                break;
            }

        }

        return super.mouseClicked(mouseX, mouseY, button);

    }

    private void openTierScreen(int tierIndex) {
        Screen tierScreen;
        switch (tierIndex) {
            case 0:
                tierScreen = new TierZeroScreen(this);
                break;
            //case 1:
            //    tierScreen = new QuestScreen(this, "test");
            //    break; // debug remove later
            // todo add cases for other tiers
            default:
                return;
        }
        this.client.setScreen(tierScreen);
    }

}
