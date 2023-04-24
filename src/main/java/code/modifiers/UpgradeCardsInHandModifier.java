package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import code.alchemy.ConcoctionActions;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class UpgradeCardsInHandModifier extends StackableModifier {
    public static String ID = makeID("UpgradeCardsInHandModifier");
    public static String MOD_DESCRIPTION = " #yUpgrade all cards in your hand for the rest of combat.";
    public static String MULT_MOD_DESCRIPTION = " #yUpgrade all cards in your hand #b{0} times for the rest of combat.";

    public UpgradeCardsInHandModifier(int amount) {
        super(ID, amount);
        this.priority = 12;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        if(amount > 1) {
            return rawDescription + MULT_MOD_DESCRIPTION.replace("{0}", amount + "");
        } else {
            return rawDescription + MOD_DESCRIPTION;
        }
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        for (int i = 0; i < amount; i++) {
            atb(new ArmamentsAction(true));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new UpgradeCardsInHandModifier(amount);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }

    @Override
    public String getConcoctionString() {
        if(amount > 1) {
            return MULT_MOD_DESCRIPTION.replace("{0}", amount + "");
        } else {
            return MOD_DESCRIPTION;
        }
    }
}
