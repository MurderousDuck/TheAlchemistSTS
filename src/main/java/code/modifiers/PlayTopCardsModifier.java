package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class PlayTopCardsModifier extends StackableModifier {
    public static String ID = makeID("PlayTopCardsModifier");
    public static String MOD_DESCRIPTION = " Play the top {0} cards of your draw pile.";
    public int amount;

    public PlayTopCardsModifier() {
        amount = 1;
    }

    public PlayTopCardsModifier(int amt) {
        amount = amt;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + MOD_DESCRIPTION.replace("{0}", amount + "");
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        for(int i = 0; i < this.amount; ++i) {
            atb(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false));
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new PlayTopCardsModifier(amount);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
