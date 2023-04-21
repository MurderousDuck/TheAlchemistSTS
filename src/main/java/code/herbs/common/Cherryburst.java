package code.herbs.common;

import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.modifiers.DealDamageToAllModifier;
import code.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;
import static code.util.BrewStand.updateStackableModifier;

public class Cherryburst extends HerbCard {
    public final static String ID = makeID("Cherryburst");

    public Cherryburst() {
        super(ID, 7, 4);
    }

    @Override
    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new DealDamageToAllModifier(brewPotency));
    }

    @Override
    public void eat() {
        Wiz.atb(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.eatPotency, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}