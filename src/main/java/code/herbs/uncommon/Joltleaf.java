package code.herbs.uncommon;

import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.herbs.HerbRarity;
import code.modifiers.GainEnergyModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;

import static code.ModFile.makeID;
import static code.util.BrewStand.updateStackableModifier;
import static code.util.Wiz.atb;

public class Joltleaf extends HerbCard {
    public final static String ID = makeID("Joltleaf");

    public Joltleaf() {
        super(ID, 1, 1, HerbRarity.UNCOMMON);
    }

    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new GainEnergyModifier(brewPotency));
    }

    @Override
    public void eat() {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EnergizedPower(AbstractDungeon.player, eatPotency), eatPotency));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}