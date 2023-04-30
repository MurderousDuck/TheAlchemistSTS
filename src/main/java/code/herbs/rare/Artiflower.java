package code.herbs.rare;

import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.herbs.HerbRarity;
import code.modifiers.GainArtifactModifier;
import code.powers.LoseArtifactPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static code.ModFile.makeID;
import static code.util.ModManager.updateStackableModifier;
import static code.util.Wiz.atb;

public class Artiflower extends HerbCard {
    public final static String ID = makeID("Artiflower");

    public Artiflower() {
        super(ID, 1, 1, HerbRarity.RARE);
    }

    @Override
    public void brew(AbstractCreature target, ConcoctionActions actions) {
        updateStackableModifier(actions, new GainArtifactModifier(brewPotency));
    }

    @Override
    public void eat() {
        AbstractCreature target = AbstractDungeon.player;
        atb(new ApplyPowerAction(target, AbstractDungeon.player, new ArtifactPower(target, this.eatPotency), this.eatPotency));
        atb(new ApplyPowerAction(target, AbstractDungeon.player, new LoseArtifactPower(target, this.eatPotency), this.eatPotency));
    }

    @Override
    public void upp() {

    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {

    }
}