package code.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FadingPower extends AbstractEasyPower {
    public static final String POWER_ID = "Fading";

    public FadingPower(AbstractCreature owner, int amount) {
        super(POWER_ID, AbstractPower.PowerType.BUFF, false, owner, amount);
        updateDescription();
    }

    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        return this.calculateDamageTakenAmount(damage, type);
    }

    private float calculateDamageTakenAmount(float damage, DamageInfo.DamageType type) {
        return damage / 2.0F;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void atEndOfTurn(boolean isPlayer) {
        this.flash();
        if (this.amount == 1) {
            removeThisPower();
        } else {
            reduceThisPower(1);
        }
    }
}
