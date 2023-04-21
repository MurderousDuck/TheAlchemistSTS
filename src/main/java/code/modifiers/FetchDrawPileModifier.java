package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.defect.SeekAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class FetchDrawPileModifier extends StackableModifier {
    public static String ID = makeID("FetchDrawPileModifier");
    public static String MOD_DESCRIPTION = " NL Fetch a card from your draw pile.";
    public static String MULT_MOD_DESCRIPTION = " NL Fetch {0} cards from your draw pile.";
    public int amount;

    public FetchDrawPileModifier() {
        amount = 1;
    }

    public FetchDrawPileModifier(int amt) {
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
        atb(new SeekAction(amount));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new FetchDrawPileModifier(amount);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
