package code.cards.alchemist.uncommon;

import code.actions.BrewAction;
import code.cards.AbstractEasyCard;
import code.herbs.uncommon.Gummush;
import code.util.BrewStand;
import code.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class GummyCoating extends AbstractEasyCard {
    public final static String ID = makeID("GummyCoating");

    public GummyCoating() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);
        baseBlock = 11;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        block();
        Wiz.atb(new BrewAction(BrewStand.getBrewAmount(), new Gummush()));
    }

    public void upp() {
        upgradeBlock(3);
    }
}