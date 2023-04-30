package code.actions;

import code.actions.effects.ShowCardAndAddToPouchEffect;
import code.util.BrewStand;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.Objects;

public class ForageAction extends AbstractGameAction {
    private final float x;
    private final float y;

    public ForageAction(int amount, float cardX, float cardY) {
        this.setValues(this.target, this.source, amount);
        this.actionType = ActionType.CARD_MANIPULATION;
        this.startDuration = Settings.FAST_MODE ? Settings.ACTION_DUR_FAST : 0.5F;
        this.duration = this.startDuration;
        this.x = cardX;
        this.y = cardY;
    }

    public ForageAction(int amount) {
        this(amount, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F);
    }

    public void update() {
        if (this.duration == this.startDuration) {
            int i;
            if (this.amount < 6) {
                for(i = 0; i < this.amount; ++i) {
                    AbstractCard c = Objects.requireNonNull(BrewStand.getRandomHerbWithWeight()).makeCopy();
                    AbstractDungeon.effectList.add(new ShowCardAndAddToPouchEffect(c, this.x, this.y));
                }
            } else {
                for(i = 0; i < this.amount; ++i) {
                    AbstractCard c = Objects.requireNonNull(BrewStand.getRandomHerbWithWeight()).makeCopy();
                    AbstractDungeon.effectList.add(new ShowCardAndAddToPouchEffect(c));
                }
            }
            this.duration -= Gdx.graphics.getDeltaTime();
        }

        this.tickDuration();
    }
}
