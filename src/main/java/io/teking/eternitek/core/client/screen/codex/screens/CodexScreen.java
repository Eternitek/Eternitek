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
    private boolean isSidebarExpanded = false;
    private float currentSidebarWidth = SIDEBAR_COLLAPSED_WIDTH;
    private final int mainPageWidth = 384;
    private final int bookHeight = 256;
    private int bookRenderX;
    private int bookRenderY;

    private final List<Tier> tiers = Arrays.asList(
            new Tier("T0: Crude", "Heat Resistant Brick", Identifier.of("minecraft:textures/block/oak_planks.png")),
            new Tier("T1: Makeshift", "Brass", Identifier.of("minecraft:textures/block/stone.png")),
            new Tier("T2: Industrial", "Steel", Identifier.of("minecraft:textures/block/iron_block.png")),
            new Tier("T3: Advanced", "Aluminum", Identifier.of("minecraft:textures/block/gold_block.png")),
            new Tier("T4: Refined", "Stainless Steel", Identifier.of("minecraft:textures/block/diamond_block.png")),
            new Tier("T5: Reclaimed", "Sci-fi BS", Identifier.of("minecraft:textures/block/emerald_block.png")),
            new Tier("T6: Reawakened", "Recharged Power Source", Identifier.of("minecraft:textures/block/netherite_block.png")),
            new Tier("T7: Resonant", "Sci-fi BS", Identifier.of("minecraft:textures/block/obsidian.png")),
            new Tier("T8: Evolved", "Unknown", Identifier.of("minecraft:textures/block/bedrock.png"))
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

        if (isSidebarExpanded) {
            currentSidebarWidth += (float) ((SIDEBAR_EXPANDED_WIDTH - currentSidebarWidth) * 0.1);
            if (currentSidebarWidth > SIDEBAR_EXPANDED_WIDTH - 1) {
                currentSidebarWidth = SIDEBAR_EXPANDED_WIDTH;
            }
        } else {
            currentSidebarWidth += (float) ((SIDEBAR_COLLAPSED_WIDTH - currentSidebarWidth) * 0.1);
            if (currentSidebarWidth < SIDEBAR_COLLAPSED_WIDTH + 1) {
                currentSidebarWidth = SIDEBAR_COLLAPSED_WIDTH;
            }
        }

        context.drawTexture(MAIN_PAGE_TEXTURE, bookRenderX, bookRenderY, 0, 0, mainPageWidth, bookHeight, mainPageWidth, bookHeight);
        context.drawTexture(SIDEBAR_TEXTURE, bookRenderX + mainPageWidth - (int) currentSidebarWidth, bookRenderY, 0, 0, (int) currentSidebarWidth, bookHeight, (int) currentSidebarWidth, bookHeight);

        int bookIconX = bookRenderX + mainPageWidth - SIDEBAR_COLLAPSED_WIDTH + 3;
        if (isSidebarExpanded) bookIconX -= 5;
        int bookIconY = bookRenderY + bookHeight - 30;
        context.drawTexture(BOOK_ICON, bookIconX, bookIconY, 0, 0, 20, 20, 20, 20);

        if (currentSidebarWidth > SIDEBAR_COLLAPSED_WIDTH) {
            int buttonY = bookRenderY + 15;
            int padding = 2;
            int iconSize = 16;
            int buttonHeight = iconSize + padding * 2;
            int sidebarWidth = (int) currentSidebarWidth;

            for (int i = 0; i < tiers.size(); i++) {
                Tier tier = tiers.get(i);
                int buttonX = bookRenderX + mainPageWidth - sidebarWidth;

                int iconX = buttonX + (SIDEBAR_COLLAPSED_WIDTH - iconSize) / 2;

                if (i == selectedTier) {
                    context.fill(buttonX + 6, buttonY, buttonX + sidebarWidth - 6, buttonY + buttonHeight, 0xFF8B0000);
                }

                context.drawTexture(tier.icon, iconX + 4, buttonY + padding, 0, 0, iconSize, iconSize, iconSize, iconSize);

                if (sidebarWidth > SIDEBAR_COLLAPSED_WIDTH + 10) {
                    int textX = buttonX + SIDEBAR_COLLAPSED_WIDTH + 2;
                    context.drawText(textRenderer, tier.name, textX, buttonY + (buttonHeight - textRenderer.fontHeight) / 3, 0xFFFFFF, true);
                }

                buttonY += buttonHeight;

                if (buttonY + buttonHeight > bookRenderY + bookHeight - 40) {
                    break;
                }
            }
        }
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
}
