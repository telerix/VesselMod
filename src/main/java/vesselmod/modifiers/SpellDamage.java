package vesselmod.modifiers;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;

import static vesselmod.VesselMod.makeID;

public class SpellDamage extends AbstractDamageModifier {
    public static final String ID = makeID("SpellDamage");

    public SpellDamage() {}

    public boolean isInherent(){
        return true;
    }

    @Override
    public AbstractDamageModifier makeCopy() {
        return new SpellDamage();
    }
}
