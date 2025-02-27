package io.teking.eternitek.core.client.screen.codex.screens;

import io.teking.eternitek.core.EternitekCore;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public abstract class BaseTierScreen extends Screen {
    protected final CodexScreen parentScreen;
    protected static final Identifier MAIN_PAGE_TEXTURE = EternitekCore.id("textures/gui/codex.png");

    private static final int SIDEBAR_COLLAPSED_WIDTH = 26;
    private static final int SIDEBAR_EXPANDED_WIDTH = 100;
    private int currentSidebarWidth = SIDEBAR_COLLAPSED_WIDTH;

    protected int offsetX = 0;
    protected int offsetY = 0;
    protected boolean isDragging = false;
    protected int lastMouseX, lastMouseY;

    protected static final int BOOK_WIDTH = 512;
    protected static final int BOOK_HEIGHT = 256;
    protected int bookX, bookY;
    protected int minOffsetX, maxOffsetX, minOffsetY, maxOffsetY;

    public boolean isSidebarExpanded = false;

    public final int mainPageWidth = 385;
    public final int bookHeight = 256;

    public int bookRenderX;
    public int bookRenderY;


    public BaseTierScreen(Text title, CodexScreen parentScreen) {
        super(title);
        this.parentScreen = parentScreen;
    }

    @Override
    protected void init() {
        super.init();
        bookRenderX = (this.width - mainPageWidth) / 2;
        bookRenderY = (this.height - bookHeight) / 2;
        bookX = bookRenderX;
        bookY = bookRenderY;

        // Adjust drag limits
        minOffsetX = -mainPageWidth;
        maxOffsetX = 0;
        minOffsetY = -bookHeight;
        maxOffsetY = 0;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        if(isSidebarExpanded) {
            if(currentSidebarWidth < SIDEBAR_EXPANDED_WIDTH) currentSidebarWidth++;
        } else {
            if(currentSidebarWidth > SIDEBAR_COLLAPSED_WIDTH) currentSidebarWidth--;
        }

        // --- Book area details (relative to the texture) ---
        int bookAreaXOffset = 0;   // X offset of the book area within the texture (starts at 0)
        int bookAreaYOffset = 0;   // Y offset of the book area within the texture
        int bookAreaWidth = 385;  // Actual width of the visible book area (this needs to be determined)
        int bookAreaHeight = 256; // Height of the visible book area


        // --- Sidebar Details ---
        int sidebarXOffset = 385; // X offset of the sidebar within the texture (start)
        int sidebarWidth = (int) currentSidebarWidth;

        int bookScreenX = bookRenderX;
        int bookScreenY = bookRenderY;

        // The middle of the book is the origin for the tech tree.
        int treeOriginX = bookScreenX  + (bookAreaWidth  / 2);
        int treeOriginY = bookScreenY  + (bookAreaHeight / 2);

        // Draw the book background
        context.drawTexture(
                MAIN_PAGE_TEXTURE,
                bookScreenX, // Screen X
                bookScreenY, // Screen Y
                bookAreaXOffset, // Texture X
                bookAreaYOffset, // Texture Y
                bookAreaWidth, // Width to draw
                bookAreaHeight, // Height to draw
                512, // Texture Width
                256  // Texture Height
        );

        // Draw the sidebar, offset to the right of the book area
        context.drawTexture(
                MAIN_PAGE_TEXTURE,
                bookScreenX + bookAreaWidth - sidebarWidth, // X position is at the side of the content
                bookScreenY + 5, // Y position
                sidebarXOffset,   // X start in the texture
                0,               // Y start in the texture
                8,               // Width of the Sidebar
                246,           // Height of the Sidebar
                512,       // Width of the total texture
                256       // Height of the total texture
        );

        // Enable scissor with the correct dimensions
        context.enableScissor(
                bookScreenX,              // X position on the screen
                bookScreenY,              // Y position on the screen
                bookScreenX + bookAreaWidth, // Width: Book area only
                bookScreenY + bookAreaHeight  // Height: Book area only
        );

        // Apply translation for tech tree content
        context.getMatrices().push();
        context.getMatrices().translate(treeOriginX + offsetX, treeOriginY + offsetY, 0);

        // Render tech tree content here
        renderTechTree(context, mouseX - bookScreenX - offsetX, mouseY - bookScreenY - offsetY, delta);

        context.getMatrices().pop();
        context.disableScissor();
    }

    protected abstract void renderTechTree(DrawContext context, int mouseX, int mouseY, float delta);

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0) { // Left mouse button
            isDragging = true;
            lastMouseX = (int) mouseX;
            lastMouseY = (int) mouseY;
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (button == 0) { // Left mouse button
            isDragging = false;
            return true;
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        if (isDragging && button == 0) {
            offsetX = (int) Math.clamp(offsetX + dragX, minOffsetX, maxOffsetX);
            offsetY = (int) Math.clamp(offsetY + dragY, minOffsetY, maxOffsetY);
            return true;
        }
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.client.setScreen(parentScreen);
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
