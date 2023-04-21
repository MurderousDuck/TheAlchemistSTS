package code.herbs.uncommon;

import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.modifiers.GainEnergyModifier;
import code.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

import static code.ModFile.makeID;
import static code.util.BrewStand.updateStackableModifier;

public class Gummush extends HerbCard {
    public final static String ID = makeID("Gummush");

    public Gummush() {
        super(ID, 1, 1);
    }

    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new GainEnergyModifier(brewPotency));
    }

    @Override
    public void eat() {
        Wiz.atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedPower(AbstractDungeon.player, eatPotency), eatPotency));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}