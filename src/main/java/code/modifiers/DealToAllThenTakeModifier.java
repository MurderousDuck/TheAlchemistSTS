package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import code.alchemy.ConcoctionActions;
import code.util.BrewStand;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class DealToAllThenTakeModifier extends StackableModifier{
    public static String ID = makeID("DealToAllThenTakeModifier");
    public static String MOD_DESCRIPTION = " Deal #b{0} damage to ALL enemies. Take #b{1} damage.";
    public int selfDamage;

    public DealToAllThenTakeModifier() {
        super(ID);
        selfDamage = 1;
    }

    public DealToAllThenTakeModifier(int amount, int selfDamage) {
        super(ID, amount);
        this.selfDamage = selfDamage;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + MOD_DESCRIPTION.replace("{0}", amount + "").replace("{1}", selfDamage + "");
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        BrewStand.explosivePotionEffect();
        atb(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.amount, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));

        BrewStand.playerExplosion();
        atb(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, this.selfDamage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DealToAllThenTakeModifier(amount, selfDamage);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public String getConcoctionString() {
        return MOD_DESCRIPTION.replace("{0}", amount + "").replace("{1}", selfDamage + "");
    }
}

