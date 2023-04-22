package code.herbs.common;

import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.herbs.HerbRarity;
import code.modifiers.DealDamageModifier;
import code.modifiers.GainBlockModifier;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static code.ModFile.makeID;
import static code.util.BrewStand.updateStackableModifier;
import static code.util.Wiz.atb;

public class Wavycap extends HerbCard {
    public final static String ID = makeID("Wavycap");

    public Wavycap() {
        super(ID, 5, 3, HerbRarity.COMMON);
    }

    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new GainBlockModifier(brewPotency));
        updateStackableModifier(actions, new DealDamageModifier(brewPotency));
    }

    @Override
    public void eat() {
        DamageInfo info = new DamageInfo(AbstractDungeon.player, this.eatPotency, DamageInfo.DamageType.THORNS);
        AbstractCreature target = AbstractDungeon.getMonsters().getRandomMonster(null, true, AbstractDungeon.cardRandomRng);
        info.applyEnemyPowersOnly(target);

        atb(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.eatPotency));
        atb(new DamageAction(target, info, AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}