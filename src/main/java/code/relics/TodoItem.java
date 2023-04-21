package code.relics;

import code.TheAlchemist;

import static code.ModFile.makeID;

public class TodoItem extends AbstractEasyRelic {
    public static final String ID = makeID("TodoItem");

    public TodoItem() {
        super(ID, RelicTier.STARTER, LandingSound.FLAT, TheAlchemist.Enums.ALCHEMIST_COLOR);
    }
}
