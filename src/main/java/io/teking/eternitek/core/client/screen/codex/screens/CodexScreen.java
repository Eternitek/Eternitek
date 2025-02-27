package io.teking.eternitek.core.client.screen.codex.screens;

import io.teking.eternitek.core.EternitekCore;
import io.teking.eternitek.core.client.screen.codex.pages.Tier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;

@Environment(EnvType.CLIENT)
public class CodexScreen extends Screen {

    private static final Identifier MAIN_PAGE_TEXTURE = EternitekCore.id("textures/gui/codex.png");
    private static final Identifier SIDEBAR_TEXTURE = EternitekCore.id("textures/gui/codex_sidebar.png");
    private static final Identifier BOOK_ICON = EternitekCore.id("textures/item/codex.png");

    private static final int SIDEBAR_COLLAPSED_WIDTH = 26;
    private static final int SIDEBAR_EXPANDED_WIDTH = 100;
    private int currentSidebarWidth = SIDEBAR_COLLAPSED_WIDTH;

    private boolean isSidebarExpanded = false;

    private final int mainPageWidth = 385;
    private final int bookHeight = 256;

    private int bookRenderX;
    private int bookRenderY;

    private final List<Tier> tiers = Arrays.asList(
            new Tier("T0: Crude", "Heat Resistant Brick", Identifier.of("minecraft:textures/block/oak_planks.png"), 0),
            new Tier("T1: Makeshift", "Brass", Identifier.of("minecraft:textures/block/stone.png"), 1),
            new Tier("T2: Industrial", "Steel", Identifier.of("minecraft:textures/block/iron_block.png"), 2),
            new Tier("T3: Advanced", "Aluminum", Identifier.of("minecraft:textures/block/gold_block.png"), 3),
            new Tier("T4: Refined", "Stainless Steel", Identifier.of("minecraft:textures/block/diamond_block.png"), 4),
            new Tier("T5: Reclaimed", "Sci-fi BS", Identifier.of("minecraft:textures/block/emerald_block.png"), 5),
            new Tier("T6: Reawakened", "Recharged Power Source", Identifier.of("minecraft:textures/block/netherite_block.png"), 6),
            new Tier("T7: Resonant", "Sci-fi BS", Identifier.of("minecraft:textures/block/obsidian.png"), 7),
            new Tier("T8: Evolved", "Unknown", Identifier.of("minecraft:textures/block/bedrock.png"), 8)
    );

    private String displayedSidebarContent = "";
    private int contentIndex = 0;
    private int selectedTier = -1;
    private int animationTimer = 0;

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

        if(isSidebarExpanded) {
            if(currentSidebarWidth < SIDEBAR_EXPANDED_WIDTH) currentSidebarWidth++;
        } else {
            if(currentSidebarWidth > SIDEBAR_COLLAPSED_WIDTH) currentSidebarWidth--;
        }

        context.drawTexture(
                MAIN_PAGE_TEXTURE,
                bookRenderX, bookRenderY,
                0, 0,
                mainPageWidth, bookHeight,
                512, 256
        );

        context.drawTexture(
                MAIN_PAGE_TEXTURE,
                (bookRenderX + 370) - currentSidebarWidth, bookRenderY + 5,
                385, 0,
                8, 246,
                512, 256
        );

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int bookIconX = bookRenderX + mainPageWidth - SIDEBAR_COLLAPSED_WIDTH + 3;
        if (isSidebarExpanded) bookIconX -= 5;
        int bookIconY = bookRenderY + bookHeight - 30;
        if (mouseX >= bookIconX && mouseX < bookIconX + 20 && mouseY >= bookIconY && mouseY < bookIconY + 20) {
            isSidebarExpanded = !isSidebarExpanded;
            if (!isSidebarExpanded) selectedTier = -1;
            return true;
        }

        if (isSidebarExpanded) {
            int buttonY = bookRenderY + 15;
            int padding = 2;
            int iconSize = 16;
            int buttonHeight = iconSize + padding * 2;
            int sidebarWidth = (int) currentSidebarWidth;

            for (int i = 0; i < tiers.size(); i++) {
                int buttonX = bookRenderX + mainPageWidth - sidebarWidth;
                if (mouseX >= buttonX && mouseX < buttonX + sidebarWidth &&
                        mouseY >= buttonY && mouseY < buttonY + buttonHeight) {
                    selectedTier = i;
                    openTierScreen(i);
                    return true;
                }
                buttonY += buttonHeight;

                if (buttonY + buttonHeight > bookRenderY + bookHeight - 40) {
                    break;
                }
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
            // todo add cases for other tiers
            default:
                return;
        }
        this.client.setScreen(tierScreen);
    }
}
