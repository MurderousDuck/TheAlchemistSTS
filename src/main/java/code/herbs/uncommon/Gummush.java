package code.herbs.uncommon;

import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.herbs.HerbRarity;
import code.modifiers.GainEnergyModifier;
import code.modifiers.RetainHandModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.RetainCardsAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

import static code.ModFile.makeID;
import static code.util.BrewStand.updateStackableModifier;
import static code.util.Wiz.atb;

public class Gummush extends HerbCard {
    public final static String ID = makeID("Gummush");

    public Gummush() {
        super(ID, 1, 1, HerbRarity.UNCOMMON);
    }

    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new RetainHandModifier(brewPotency));
    }

    @Override
    public void eat() {
        this.addToBot(new RetainCardsAction(AbstractDungeon.player, eatPotency));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}