package code.cards.alchemist.basic;

import code.actions.ForageAction;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class CarefulForaging extends AbstractEasyCard {
    public final static String ID = makeID("CarefulForaging");

    public CarefulForaging() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = 4;
        magicNumber = baseMagicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new ForageAction(this.magicNumber));
        blck();
    }

    public void upp() {
        upgradeMagicNumber(1);
    }
}