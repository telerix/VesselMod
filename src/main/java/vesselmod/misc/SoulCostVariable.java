package vesselmod.misc;

import basemod.abstracts.DynamicVariable;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import vesselmod.cards.BaseCard;

public class SoulCostVariable extends DynamicVariable {

    @Override
    public String key(){
        return "vesselmod:S";
    }

    @Override
    public boolean isModified(AbstractCard card) {
        if (card instanceof BaseCard){
            return ((BaseCard) card).isSoulCostModified;
        }
        else return false;
    }


    @Override
    public void setIsModified(AbstractCard card, boolean v) {
        if (card instanceof BaseCard) {
            ((BaseCard) card).isSoulCostModified = v;
        }
    }

    @Override
    public int value(AbstractCard card) {
        if (card instanceof BaseCard) {
            if (card.freeToPlayOnce) {
                return 0;
            } else {
                return ((BaseCard) card).soulCost;
            }
        }
        else return 0;
    }

    @Override
    public int baseValue(AbstractCard card) {
        if (card instanceof BaseCard) {
            return ((BaseCard) card).baseSoulCost;
        }
        else return 0;
    }

    @Override
    public boolean upgraded(AbstractCard card) {
        if (card instanceof BaseCard) {
            return ((BaseCard) card).upgradedSoulCost;
        }
        else return false;
    }

    @Override
    public Color getIncreasedValueColor() {
        return Settings.RED_TEXT_COLOR;
    }

    @Override
    public Color getDecreasedValueColor() {
        return Settings.GREEN_TEXT_COLOR;
    }
}
