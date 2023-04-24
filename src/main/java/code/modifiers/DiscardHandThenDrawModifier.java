package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import code.actions.DiscardHandThenDrawAction;
import code.alchemy.ConcoctionActions;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class DiscardHandThenDrawModifier extends StackableModifier {
    public static String ID = makeID("DiscardHandThenDrawModifier");
    public static String MOD_DESCRIPTION = " Discard your hand then draw #b{0} cards.";

    public DiscardHandThenDrawModifier() {
        super(ID);
    }

    public DiscardHandThenDrawModifier(int amount) {
        super(ID, amount);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + MOD_DESCRIPTION.replace("{0}", amount + "");
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        atb(new DiscardHandThenDrawAction(amount));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DiscardHandThenDrawModifier(amount);
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
