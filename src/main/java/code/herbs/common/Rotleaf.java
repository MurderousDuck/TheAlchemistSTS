package code.herbs.common;

import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.modifiers.ApplyPoisonModifier;
import code.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;

import static code.ModFile.makeID;
import static code.util.BrewStand.updateStackableModifier;

public class Rotleaf extends HerbCard {
    public final static String ID = makeID("Rotleaf");

    public Rotleaf() {
        super(ID, 5, 3);
    }

    @Override
    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new ApplyPoisonModifier(brewPotency));
    }

    @Override
    public void eat() {
        AbstractCreature target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        Wiz.atb(new ApplyPowerAction(target, AbstractDungeon.player, new PoisonPower(target, AbstractDungeon.player, this.brewPotency), this.brewPotency));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}