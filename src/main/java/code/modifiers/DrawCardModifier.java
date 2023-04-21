package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class DrawCardModifier extends StackableModifier {
    public static String ID = makeID("DrawCardModifier");
    public static String MOD_DESCRIPTION = " NL Draw a card.";
    public static String MULT_MOD_DESCRIPTION = " NL Draw {0} cards.";
    public int amount;

    public DrawCardModifier() {
        amount = 1;
    }

    public DrawCardModifier(int amt) {
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
        atb(new DrawCardAction(amount));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DrawCardModifier(amount);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
