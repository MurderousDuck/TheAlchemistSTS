package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import code.alchemy.ConcoctionActions;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.MetallicizePower;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class GainMetallicizeModifier extends StackableModifier{
    public static String ID = makeID("GainMetallicizeModifier");
    public static String MOD_DESCRIPTION = " Gain #b{0} #yMetallicize.";

    public GainMetallicizeModifier() {
        super(ID);
    }

    public GainMetallicizeModifier(int amount) {
        super(ID, amount);
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + MOD_DESCRIPTION.replace("{0}", amount + "");
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MetallicizePower(AbstractDungeon.player, this.amount), this.amount));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GainMetallicizeModifier(amount);
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
