package code.relics;

import code.TheAlchemist;
import code.actions.ForageAction;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class ForagingKit extends AbstractEasyRelic {
    public static final String ID = makeID("ForagingKit");

    public void atPreBattle() {
        atb(new ForageAction(3));
    }

    public ForagingKit() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheAlchemist.Enums.ALCHEMIST_COLOR);
    }
}
