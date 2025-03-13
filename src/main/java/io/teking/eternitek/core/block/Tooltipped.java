package io.teking.eternitek.core.block;

import net.minecraft.block.BlockState;
import net.minecraft.text.OrderedText;

import java.util.List;

public interface Tooltipped {

    List<OrderedText> getTooltip(BlockState state);

}
