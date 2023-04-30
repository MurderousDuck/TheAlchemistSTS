package code.herbs.elusive;

import code.actions.CopyCardAction;
import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.herbs.HerbRarity;
import code.modifiers.DoublePlayModifier;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;
import static code.util.ModManager.updateStackableModifier;
import static code.util.Wiz.atb;

public class Doubleye extends HerbCard {
    public final static String ID = makeID("Doubleye");

    public Doubleye() {
        super(ID, 1, 1, HerbRarity.ELUSIVE);
    }

    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new DoublePlayModifier(brewPotency));
    }

    @Override
    public void eat() {
        atb(new CopyCardAction(AbstractDungeon.player, eatPotency));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}