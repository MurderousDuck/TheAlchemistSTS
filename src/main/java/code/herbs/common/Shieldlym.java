package code.herbs.common;

import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.modifiers.GainBlockModifier;
import code.util.Wiz;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;
import static code.util.BrewStand.updateStackableModifier;

public class Shieldlym extends HerbCard {
    public final static String ID = makeID("Shieldlym");

    public Shieldlym() {
        super(ID, 10, 5);
    }

    @Override
    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new GainBlockModifier(brewPotency));
    }

    @Override
    public void eat() {
        Wiz.atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.eatPotency));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}