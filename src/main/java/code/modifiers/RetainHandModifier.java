package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EquilibriumPower;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class RetainHandModifier extends StackableModifier {
    public static String ID = makeID("RetainHandModifier");
    public static String MOD_DESCRIPTION = " Retain your hand this turn.";
    public static String MULT_MOD_DESCRIPTION = " Return your hand for the next {0} turns.";
    public int amount;

    public RetainHandModifier() {
        amount = 1;
    }

    public RetainHandModifier(int amt) {
        amount = amt;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(amount > 1)
            return rawDescription + MULT_MOD_DESCRIPTION.replace("{0}", amount + "");
        else
            return rawDescription + MOD_DESCRIPTION;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EquilibriumPower(AbstractDungeon.player, amount), amount));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new RetainHandModifier(amount);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
