package code.alchemy;

import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class ConcoctionActions extends AbstractEasyCard {
    public final static String ID = makeID("ConcoctionActions");

    public String concoctionString;

    public ConcoctionActions() {
        super(ID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE);
        concoctionString = "";
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
    }

    public void upp() {
    }
}