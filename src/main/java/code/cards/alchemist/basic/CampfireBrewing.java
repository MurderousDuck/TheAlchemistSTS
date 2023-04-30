package code.cards.alchemist.basic;

import code.actions.BrewAction;
import code.cards.AbstractEasyCard;
import code.util.BrewStand;
import code.util.Wiz;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class CampfireBrewing extends AbstractEasyCard {
    public final static String ID = makeID("CampfireBrewing");
    // intellij stuff skill, self, basic, , ,  5, 3, ,

    public CampfireBrewing() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.NONE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        Wiz.atb(new BrewAction(BrewStand.getBrewAmount()));
    }

    public void upp() {
        upgradeBaseCost(0);
    }
}