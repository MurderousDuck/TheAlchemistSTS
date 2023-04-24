package code.alchemy;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

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