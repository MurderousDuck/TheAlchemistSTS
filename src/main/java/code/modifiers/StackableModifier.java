package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.ModFile.makeID;

public abstract class StackableModifier extends AbstractCardModifier {
    public String ID = makeID("StackableModifier");
    public int amount;

    public StackableModifier() {
        amount = 1;
    }

    public StackableModifier(int amount) {
        this.amount = amount;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
