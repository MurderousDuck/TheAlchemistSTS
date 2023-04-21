package code.relics;

import code.CharacterFile;

import static code.ModFile.makeID;

public class PocketLab extends AbstractEasyRelic {
    public static final String ID = makeID("PocketLab");

    public PocketLab() {
        super(ID, RelicTier.BOSS, LandingSound.CLINK, CharacterFile.Enums.ALCHEMIST_COLOR);
    }
}
