package code.herbs.common;

import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.modifiers.GainDexterityModifier;
import code.util.Wiz;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;

import static code.ModFile.makeID;
import static code.util.BrewStand.updateStackableModifier;

public class Agilieaf extends HerbCard {
    public final static String ID = makeID("Agilieaf");

    public Agilieaf() {
        super(ID, 1, 2);
    }

    @Override
    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new GainDexterityModifier(brewPotency));
    }

    @Override
    public void eat() {
        AbstractCreature target = AbstractDungeon.player;
        Wiz.atb(new ApplyPowerAction(target, AbstractDungeon.player, new DexterityPower(target, this.eatPotency), this.eatPotency));
        Wiz.atb(new ApplyPowerAction(target, AbstractDungeon.player, new LoseDexterityPower(target, this.eatPotency), this.eatPotency));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}