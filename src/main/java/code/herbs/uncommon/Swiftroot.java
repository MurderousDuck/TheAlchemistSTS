package code.herbs.uncommon;

import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.herbs.HerbRarity;
import code.modifiers.DrawCardModifier;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;
import static code.util.BrewStand.updateStackableModifier;
import static code.util.Wiz.atb;

public class Swiftroot extends HerbCard {
    public final static String ID = makeID("Swiftroot");

    public Swiftroot() {
        super(ID, 2, 1, HerbRarity.UNCOMMON);
    }

    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new DrawCardModifier(brewPotency));
    }

    @Override
    public void eat() {
        atb(new DrawCardAction(AbstractDungeon.player, eatPotency));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}