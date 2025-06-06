package vesselmod.actions.fx;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;

import static vesselmod.VesselMod.makeID;

public class DashSlashEffect extends AbstractGameAction {
    private static final String SFX_DASH = makeID("Dash");
    private static final String SFX_SLASH = makeID("GreatSlash");

    @Override
    public void update() { //reversed due to dmg already at the bottom of list
        this.addToTop(new SFXAction(SFX_SLASH));
        this.addToTop(new WaitAction(0.3f));
        this.addToTop(new SFXAction(SFX_DASH));
        this.isDone = true;
    }
}
