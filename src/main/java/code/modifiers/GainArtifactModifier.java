package code.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class GainArtifactModifier extends StackableModifier{
    public static String ID = makeID("GainArtifactModifier");
    public static String MOD_DESCRIPTION = " Gain {0} Artifact.";
    public int amount;

    public GainArtifactModifier() {
        amount = 1;
    }

    public GainArtifactModifier(int amount) {
        this.amount = amount;
    }

    @Override
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + MOD_DESCRIPTION.replace("{0}", amount + "");
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        atb(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArtifactPower(target, this.amount), this.amount));
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new GainArtifactModifier(amount);
    }

    @Override
    public String identifier(AbstractCard card) {
        return ID;
    }
}
