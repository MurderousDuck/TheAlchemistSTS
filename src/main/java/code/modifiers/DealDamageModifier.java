package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import code.alchemy.ConcoctionActions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class DealDamageModifier extends StackableModifier {
    public static String ID = makeID("IncreaseBlockModifier");
    public static String MOD_DESCRIPTION = " Deal #b{0} damage.";

    public DealDamageModifier(int amount) {
        super(ID, amount);
        this.priority = 3;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + MOD_DESCRIPTION.replace("{0}", amount + "");
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        DamageInfo info = new DamageInfo(AbstractDungeon.player, this.amount, DamageInfo.DamageType.THORNS);
        info.applyEnemyPowersOnly(target);
        atb(new DamageAction(target, info, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DealDamageModifier(amount);
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
