package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.EchoPower;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class DoublePlayModifier extends StackableModifier {
    public static String ID = makeID("DoublePlayModifier");
    public static String MOD_DESCRIPTION = " NL Your next card this turn is played twice.";
    public static String MULT_MOD_DESCRIPTION = " NL Your next {0} cards this turn are played twice.";
    public int amount;

    public DoublePlayModifier() {
        amount = 1;
    }

    public DoublePlayModifier(int amt) {
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
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new EchoPower(AbstractDungeon.player, amount), amount));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DoublePlayModifier(amount);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
