package code.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.ThornsPower;

import static code.util.Wiz.atb;

public class LoseThornsPower extends AbstractEasyPower {
    public static final String POWER_ID = "ThornsLoss";

    public LoseThornsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, PowerType.DEBUFF, false, owner, amount);
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        atb(new ApplyPowerAction(this.owner, this.owner, new ThornsPower(this.owner, -this.amount), -this.amount));
        removeThisPower();
    }
}