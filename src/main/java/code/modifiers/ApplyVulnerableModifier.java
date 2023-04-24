package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import code.alchemy.ConcoctionActions;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class ApplyVulnerableModifier extends StackableModifier {
    public static String ID = makeID("ApplyVulnerabilityModifier");
    public static String MOD_DESCRIPTION = " Apply #b{0} #yVulnerable.";

    public ApplyVulnerableModifier(int amount) {
        super(ID, amount);
        this.priority = 5;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + MOD_DESCRIPTION.replace("{0}", amount + "");
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        atb(new ApplyPowerAction(target, AbstractDungeon.player, new VulnerablePower(target, this.amount, false), this.amount));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new ApplyVulnerableModifier(amount);
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
