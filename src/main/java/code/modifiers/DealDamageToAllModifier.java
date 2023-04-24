package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import code.alchemy.ConcoctionActions;
import code.util.BrewStand;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class DealDamageToAllModifier extends StackableModifier{
    public static String ID = makeID("DealDamageToAllModifier");
    public static String MOD_DESCRIPTION = " Deal #b{0} damage to ALL enemies.";

    public DealDamageToAllModifier() {
        super(ID);
    }

    public DealDamageToAllModifier(int amount) {
        super(ID, amount);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + MOD_DESCRIPTION.replace("{0}", amount + "");
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        BrewStand.explosivePotionEffect();
        atb(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DealDamageToAllModifier(amount);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public String getConcoctionString() {
        return MOD_DESCRIPTION.replace("{0}", amount + "");
    }
}

