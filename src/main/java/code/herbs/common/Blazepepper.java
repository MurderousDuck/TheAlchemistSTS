package code.herbs.common;

import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.modifiers.DealDamageModifier;
import code.util.Wiz;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;
import static code.util.BrewStand.updateStackableModifier;

public class Blazepepper extends HerbCard {
    public final static String ID = makeID("Blazepepper");

    public Blazepepper() {
        super(ID, 10, 5);
    }

    @Override
    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new DealDamageModifier(brewPotency));
    }

    @Override
    public void eat() {
        DamageInfo info = new DamageInfo(AbstractDungeon.player, this.eatPotency, DamageInfo.DamageType.THORNS);
        AbstractCreature target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        info.applyEnemyPowersOnly(target);

        Wiz.atb(new DamageAction(target, info, AbstractGameAction.AttackEffect.FIRE));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}