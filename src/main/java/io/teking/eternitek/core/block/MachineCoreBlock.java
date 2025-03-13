package io.teking.eternitek.core.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

import java.util.List;

public class MachineCoreBlock extends Block implements Tooltipped {

    public MachineCoreBlock(Settings settings) {
        super(settings);
    }

    @Override
    public List<OrderedText> getTooltip(BlockState state) {
        return List.of(
                Text.literal("Machine Core").fillStyle(TITLE).asOrderedText(),
                Text.literal("Requires 100 GƐ").fillStyle(ERROR).asOrderedText()
        );
    }

}
