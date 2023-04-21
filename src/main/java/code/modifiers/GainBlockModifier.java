package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class GainBlockModifier extends StackableModifier {
    public static String ID = makeID("GainBlockModifier");
    public static String MOD_DESCRIPTION = " Gain {0} Block.";

    public int amount;

    public GainBlockModifier() {
        new GainBlockModifier(1);
    }

    public GainBlockModifier(int amount) {
        this.amount = amount;
        this.priority = 0;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + MOD_DESCRIPTION.replace("{0}", amount + "");
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GainBlockModifier(amount);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
