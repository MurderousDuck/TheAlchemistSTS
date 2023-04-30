package code.herbs.elusive;

import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.herbs.HerbRarity;
import code.modifiers.DealToAllThenTakeModifier;
import code.util.BrewStand;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;
import static code.util.ModManager.updateStackableModifier;
import static code.util.Wiz.atb;

public class Nitrogliceroot extends HerbCard {
    public final static String ID = makeID("Nitrogliceroot");

    public Nitrogliceroot() {
        super(ID, 50, 25, HerbRarity.ELUSIVE, true, false);
    }

    @Override
    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new DealToAllThenTakeModifier(brewPotency, brewPotency / 5));
    }

    @Override
    public void eat() {
        BrewStand.explosivePotionEffect();
        atb(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.eatPotency, true), DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));

        BrewStand.playerExplosion();
        atb(new DamageAction(AbstractDungeon.player, new DamageInfo(AbstractDungeon.player, this.eatPotency / 5, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}