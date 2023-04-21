package code.herbs.common;

import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.modifiers.GainStrengthModifier;
import code.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import static code.ModFile.makeID;
import static code.util.BrewStand.updateStackableModifier;

public class Buffbloom extends HerbCard {
    public final static String ID = makeID("Buffbloom");

    public Buffbloom() {
        super(ID, 1, 2);
    }

    @Override
    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new GainStrengthModifier(brewPotency));
    }

    @Override
    public void eat() {
        AbstractCreature target = AbstractDungeon.player;
        Wiz.atb(new ApplyPowerAction(target, AbstractDungeon.player, new StrengthPower(target, this.eatPotency), this.eatPotency));
        Wiz.atb(new ApplyPowerAction(target, AbstractDungeon.player, new LoseStrengthPower(target, this.eatPotency), this.eatPotency));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}