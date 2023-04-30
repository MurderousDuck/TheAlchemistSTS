package code.cards.alchemist.common;

import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;

public class HangryStrike extends AbstractEasyCard {
    public final static String ID = makeID("HangryStrike");
    // intellij stuff ATTACK, ENEMY, COMMON, 7, 3, , , , 

    public HangryStrike() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY);
        baseDamage = 7;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {

    }

    public void upp() {
        upgradeDamage(3);
    }
}