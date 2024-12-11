package io.teking.eternitek.core.science.chemistry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.text.Text;

public record Element(String symbol, String name, int number, float mass, float electronegativity, float meltingPoint, float boilingPoint) {

    public static final Codec<Element> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("name").forGetter(Element::name),
            Codec.STRING.fieldOf("symbol").forGetter(Element::symbol),
            Codec.INT.fieldOf("number").forGetter(Element::number),
            Codec.FLOAT.fieldOf("mass").forGetter(Element::mass),
            Codec.FLOAT.fieldOf("electronegativity").forGetter(Element::electronegativity),
            Codec.FLOAT.fieldOf("melting_point").forGetter(Element::meltingPoint),
            Codec.FLOAT.fieldOf("boiling_point").forGetter(Element::boilingPoint)
    ).apply(instance, Element::new));

    public State getState(float temperature) {
        if(temperature >= boilingPoint) {
            return State.GAS;
        } else if(temperature >= meltingPoint) {
            return State.LIQUID;
        } else if(temperature < meltingPoint)   {
            return State.SOLID;
        } else {
            return State.UNKNOWN;
        }
    }

    public String getTranslatableName() {
        return Text.translatable("element.%s", name).toString();
    }

    public enum State {
        UNKNOWN,
        SOLID,
        LIQUID,
        GAS
    }

}
