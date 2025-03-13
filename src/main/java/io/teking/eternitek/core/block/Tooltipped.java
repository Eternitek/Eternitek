package io.teking.eternitek.core.block;

import net.minecraft.block.BlockState;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.List;

public interface Tooltipped {

    Style TITLE = Style.EMPTY.withBold(true).withColor(0xC4E099);
    Style ERROR = Style.EMPTY.withColor(0xE0B599);

    Palette DEFAULT = new Palette(0xC8323C39, 0xA36ABE30, 0xA337946E);

    List<OrderedText> getTooltip(BlockState state);

    default Palette getColors() {
        return DEFAULT;
    }

    record Palette(int backgroundColor, int borderLight, int borderDark) {}

}
