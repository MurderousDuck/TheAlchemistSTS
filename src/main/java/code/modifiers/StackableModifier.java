package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

public abstract class StackableModifier extends AbstractCardModifier {
    public String modId;
    public int amount;

    public StackableModifier(String ID) {
        modId = ID;
        amount = 1;
    }

    public StackableModifier(String ID, int amount) {
        modId = ID;
        this.amount = amount;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
    }

    public String getConcoctionString() {
        return "";
    }
}
