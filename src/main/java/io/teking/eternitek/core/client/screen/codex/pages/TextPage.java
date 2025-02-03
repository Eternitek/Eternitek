package io.teking.eternitek.core.client.screen.codex.pages;

import net.minecraft.text.Text;

public class TextPage {

    private Text title;
    private Text text;

    public TextPage(Text title, Text text) {
        this.title = title;
        this.text = text;
    }

    public TextPage(String title, String text) {
        this.title = Text.of(title);
        this.text = Text.of(text);
    }

}
