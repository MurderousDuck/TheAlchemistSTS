package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.unique.ArmamentsAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class UpgradeCardsInHandModifier extends StackableModifier {
    public static String ID = makeID("UpgradeCardsInHandModifier");
    public static String MOD_DESCRIPTION = " Upgrade all cards in your hand for the rest of combat.";
    public static String MULT_MOD_DESCRIPTION = " Upgrade all cards in your hand {0} times for the rest of combat.";
    public int amount;

    public UpgradeCardsInHandModifier() {
        amount = 1;
    }

    public UpgradeCardsInHandModifier(int amt) {
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
}
