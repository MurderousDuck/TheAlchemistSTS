package code.actions;

import code.ModFile;
import code.alchemy.Concoction;
import code.herbs.HerbCard;
import code.util.BrewStand;
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

    public BrewAction() {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.numberOfHerbs = BrewStand.getBrewAmount();
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (ModFile.herbPouch.size() < this.numberOfHerbs) {
                this.isDone = true;
                return;
            }

            CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : ModFile.herbPouch.group)
                temp.addToTop(c);
            temp.sortAlphabetically(true);

            AbstractDungeon.gridSelectScreen.open(temp, this.numberOfHerbs, TEXT[0] + this.numberOfHerbs + TEXT[1], false);
            tickDuration();
            return;
        }

        ArrayList<HerbCard> herbs = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
            herbs.add((HerbCard) c);
            ModFile.herbPouch.removeCard(c);
        }
        ModFile.concoctionBelt.addPotion(new Concoction(herbs));

        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        this.isDone = true;
    }
}

