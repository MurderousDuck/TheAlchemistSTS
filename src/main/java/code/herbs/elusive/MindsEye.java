package code.herbs.elusive;

import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.herbs.HerbRarity;
import code.modifiers.FetchDrawPileModifier;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.ScryAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;
import static code.util.ModManager.updateStackableModifier;
import static code.util.Wiz.atb;

public class MindsEye extends HerbCard {
    public final static String ID = makeID("MindsEye");
    public final int scryValue = 7;

    public MindsEye() {
        super(ID, 1, 1, HerbRarity.ELUSIVE);
    }

    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new FetchDrawPileModifier(brewPotency));
    }

    @Override
    public void eat() {
        atb(new ScryAction(this.scryValue));
        atb(new DrawCardAction(AbstractDungeon.player, this.eatPotency));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}