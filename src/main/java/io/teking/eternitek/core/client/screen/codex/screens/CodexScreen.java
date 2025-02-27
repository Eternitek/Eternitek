package io.teking.eternitek.core.client.screen.codex.screens;

import io.teking.eternitek.core.EternitekCore;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;

@Environment(EnvType.CLIENT)
public class CodexScreen extends Screen {

    private static final Identifier BOOK_TEXTURE = EternitekCore.id("textures/gui/codex.png");
    private static final Identifier BOOK_ICON = EternitekCore.id("textures/item/codex.png");
    private static final int SIDEBAR_COLLAPSED_WIDTH = 26;
    private static final int SIDEBAR_EXPANDED_WIDTH = 90;
    private boolean isSidebarExpanded = false;
    private float currentSidebarWidth = SIDEBAR_COLLAPSED_WIDTH;
    private final int bookWidth = 348;
    private final int bookHeight = 236;
    private int bookRenderX;
    private int bookRenderY;

    private String sidebarContent = "Sidebar Content";
    private String displayedSidebarContent = "";
    private int contentIndex = 0;

    private int animationTimer = 0;

    public CodexScreen() {
        super(Text.of("Codex"));
    }

    @Override
    protected void init() {
        super.init();

        // Calculate book rendering position to center it
        bookRenderX = (this.width - bookWidth) / 2;
        bookRenderY = (this.height - bookHeight) / 2;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        super.render(context, mouseX, mouseY, delta);

        int screenWidth = this.width;
        int screenHeight = this.height;

        // Sidebar Animation
        if (isSidebarExpanded) {
            currentSidebarWidth += (SIDEBAR_EXPANDED_WIDTH - currentSidebarWidth) * 0.1;
            if (currentSidebarWidth > SIDEBAR_EXPANDED_WIDTH - 1) {
                currentSidebarWidth = SIDEBAR_EXPANDED_WIDTH;
            }
        } else {
            currentSidebarWidth += (SIDEBAR_COLLAPSED_WIDTH - currentSidebarWidth) * 0.1;
            if (currentSidebarWidth < SIDEBAR_COLLAPSED_WIDTH + 1) {
                currentSidebarWidth = SIDEBAR_COLLAPSED_WIDTH;
            }
        }

        // Draw the Main Book Area
        context.drawTexture(
                BOOK_TEXTURE,
                bookRenderX,
                bookRenderY,
                0,
                0,
                bookWidth,
                bookHeight,
                512,
                512
        );

        int sidebarWidth = SIDEBAR_COLLAPSED_WIDTH;

        context.drawTexture(
                BOOK_TEXTURE,
                bookRenderX + sidebarWidth + 7,
                bookRenderY + 5,
                348, 0,
                7, 226,
                512, 512
        );

//        // Draw the Sidebar Area
//        context.drawTexture(
//                BOOK_TEXTURE,
//                bookRenderX + bookWidth - (int) currentSidebarWidth,
//                bookRenderY,
//                bookWidth,
//                0,
//                (int) currentSidebarWidth,
//                bookHeight,
//                512,
//                512
//        );
//
//
//        // Render Book Icon (clickable area)
//        int bookIconX = bookRenderX + bookWidth - SIDEBAR_COLLAPSED_WIDTH + 5;
//        int bookIconY = bookRenderY + 5;
//        context.drawTexture(BOOK_ICON, bookIconX, bookIconY, 0, 0, 20, 20, 20, 20);
//
//        // Letter by Letter Animation
//        animationTimer++;
//        if (isSidebarExpanded) {
//            if (animationTimer % 7.5 == 0) { // Adjust the speed of the animation
//                if (contentIndex < sidebarContent.length()) {
//                    displayedSidebarContent = sidebarContent.substring(0, contentIndex + 1);
//                    contentIndex++;
//                }
//            }
//        } else {
//            // Reset content when sidebar is collapsed
//            displayedSidebarContent = "";
//            contentIndex = 0;
//        }
//
//        // Sidebar Content
//        if (currentSidebarWidth > SIDEBAR_COLLAPSED_WIDTH) {
//            // The sidebar expanded, so display the animated content
//            int sidebarContentY = bookRenderY + 40;
//
//            // Calculate right-aligned text position
//            int textWidth = MinecraftClient.getInstance().textRenderer.getWidth(displayedSidebarContent);
//            int sidebarContentX = bookRenderX + bookWidth - (int) currentSidebarWidth + (int) currentSidebarWidth - textWidth - 5; // 5 is padding
//
//
//            context.drawText(textRenderer, displayedSidebarContent, sidebarContentX, sidebarContentY, 0xFFFFFF, true);
//        }

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int bookIconX = bookRenderX + bookWidth - SIDEBAR_COLLAPSED_WIDTH + 5;
        int bookIconY = bookRenderY + 5;
        if (mouseX >= bookIconX && mouseX < bookIconX + 20 && mouseY >= bookIconY && mouseY < bookIconY + 20) {
            isSidebarExpanded = !isSidebarExpanded;
            if (!isSidebarExpanded) {
                displayedSidebarContent = "";
                contentIndex = 0;
            }
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
