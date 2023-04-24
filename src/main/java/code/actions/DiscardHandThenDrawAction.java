package code.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardHandThenDrawAction extends AbstractGameAction {
    private final float startingDuration;

    public DiscardHandThenDrawAction(int amount) {
        this.target = AbstractDungeon.player;
        this.actionType = ActionType.WAIT;
        this.startingDuration = Settings.ACTION_DUR_FAST;
        this.duration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
    }

    public void update() {
        if (this.duration == this.startingDuration) {
            this.addToTop(new DrawCardAction(this.target, amount));
            this.addToTop(new DiscardAction(this.target, this.target, AbstractDungeon.player.hand.size(), true));
            this.isDone = true;
        }
    }
}
