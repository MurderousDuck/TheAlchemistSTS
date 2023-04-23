package code.herbs.elusive;

import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.herbs.HerbRarity;
import code.modifiers.GainIntangibleModifier;
import code.powers.FadingPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;
import static code.util.BrewStand.updateStackableModifier;
import static code.util.Wiz.atb;

public class Spectralite extends HerbCard {
    public final static String ID = makeID("Spectralite");

    public Spectralite() {
        super(ID, 1, 1, HerbRarity.ELUSIVE);
    }

    @Override
    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new GainIntangibleModifier(brewPotency));
    }

    @Override
    public void eat() {
        AbstractCreature target = AbstractDungeon.player;
        atb(new ApplyPowerAction(target, AbstractDungeon.player, new FadingPower(target, this.eatPotency), this.eatPotency));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}