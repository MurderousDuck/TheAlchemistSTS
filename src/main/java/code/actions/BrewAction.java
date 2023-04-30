package code.actions;

import code.ModFile;
import code.alchemy.Concoction;
import code.herbs.HerbCard;
import code.util.PouchManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

import static code.ModFile.makeID;

public class BrewAction extends AbstractGameAction {
    public static final String[] TEXT = (CardCrawlGame.languagePack.getUIString(makeID("BrewAction"))).TEXT;
    private final int numberOfHerbs;
    private HerbCard setHerb = null;
    private HerbCard setHerb2 = null;

    public BrewAction(int amount) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.numberOfHerbs = amount;
    }

    public BrewAction(int amount, HerbCard setHerb) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.setHerb = setHerb;
        this.numberOfHerbs = amount - 1;
    }

    public BrewAction(int amount, HerbCard setHerb, HerbCard setHerb2) {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.setHerb = setHerb;
        this.setHerb2 = setHerb2;
        this.numberOfHerbs = amount - 2;
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (ModFile.herbPouch.size() < this.numberOfHerbs) {
                this.isDone = true;
                return;
            }

            CardGroup temp = PouchManager.getHerbPouchInAlphabeticalOrder();
            AbstractDungeon.gridSelectScreen.open(temp, this.numberOfHerbs, TEXT[0] + this.numberOfHerbs + TEXT[1], false);
            tickDuration();
            return;
        }

        ArrayList<HerbCard> herbs = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
            herbs.add((HerbCard) c);
            ModFile.herbPouch.removeCard(c);
        }
        if(setHerb != null) herbs.add(setHerb);
        if(setHerb2 != null) herbs.add(setHerb2);
        ModFile.concoctionBelt.addPotion(new Concoction(herbs));

        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        this.isDone = true;
    }
}

