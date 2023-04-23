package code.herbs.rare;

import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.herbs.HerbRarity;
import code.modifiers.DiscardHandThenDrawModifier;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;
import static code.util.BrewStand.updateStackableModifier;
import static code.util.Wiz.atb;

public class GamblersFruit extends HerbCard {
    public final static String ID = makeID("GamblersFruit");

    public GamblersFruit() {
        super(ID, 3, 1, HerbRarity.RARE);
    }

    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new DiscardHandThenDrawModifier(brewPotency));
    }

    @Override
    public void eat() {
        atb(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, eatPotency, false));
        atb(new DrawCardAction(AbstractDungeon.player, eatPotency));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}