package code.actions;

import code.ModFile;
import code.herbs.HerbCard;
import code.util.PouchManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import static code.ModFile.makeID;
import static code.util.Wiz.att;

public class EatAction extends AbstractGameAction {
    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(makeID("EatAction"))).TEXT;

    public boolean random = false;

    public EatAction(int amount) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
    }

    public EatAction(int amount, boolean random) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.amount = amount;
        this.random = random;
    }

    public void update() {
        if(this.random) {
            CardGroup temp = PouchManager.getHerbPouchInAlphabeticalOrder();
            for(int i = 0; i < amount; i++) {
                AbstractCard c = temp.getRandomCard(AbstractDungeon.cardRandomRng);
                att(new AbstractGameAction() {
                    @Override
                    public void update() {
                        ((HerbCard) c).eat();
                        ModFile.herbPouch.removeCard(c);
                        isDone = true;
                    }
                });
                temp.removeCard(c);
            }
            this.isDone = true;
        }
        if(this.duration == this.startDuration) {
            if (ModFile.herbPouch.size() < this.amount) {
                this.isDone = true;
                return;
            }

            CardGroup temp = PouchManager.getHerbPouchInAlphabeticalOrder();
            AbstractDungeon.gridSelectScreen.open(temp, this.amount, true, TEXT[0] + this.amount + TEXT[1]);
        } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                att(new AbstractGameAction() {
                    @Override
                    public void update() {
                        ((HerbCard) c).eat();
                        ModFile.herbPouch.removeCard(c);
                        isDone = true;
                    }
                });
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }

        this.tickDuration();
    }
}

