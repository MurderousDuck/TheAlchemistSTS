package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.DuplicationPower;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class DoublePlayModifier extends StackableModifier {
    public static final String ID = makeID("DoublePlayModifier");
    public static final String MOD_DESCRIPTION = " Your next card this turn is played twice.";
    public static final String MULT_MOD_DESCRIPTION = " Your next #b{0} cards this turn are played twice.";

    public DoublePlayModifier(int amount) {
        super(ID, amount);
        this.priority = 14;
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
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DuplicationPower(AbstractDungeon.player, amount), amount));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new DoublePlayModifier(amount);
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
