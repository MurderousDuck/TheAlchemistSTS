package code.relics;

import code.TheAlchemist;

import static code.ModFile.makeID;

public class PocketLab extends AbstractEasyRelic {
    public static final String ID = makeID("PocketLab");

    public PocketLab() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK, TheAlchemist.Enums.ALCHEMIST_COLOR);
    }
}
