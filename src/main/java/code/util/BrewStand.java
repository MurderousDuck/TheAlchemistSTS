package code.util;

import code.ModFile;
import code.herbs.HerbCard;
import code.herbs.HerbRarity;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

import java.util.ArrayList;

import static code.util.Wiz.atb;

public class BrewStand {

    public static boolean isConcoctionThrowable(ArrayList<HerbCard> herbsToBrew) {
        return herbsToBrew.stream().anyMatch(h -> h.throwable);
    }

    public static boolean isConcoctionTargeted(ArrayList<HerbCard> herbsToBrew) {
        return herbsToBrew.stream().anyMatch(h -> h.targeted);
    }

    public static int getBrewAmount() {
        if(AbstractDungeon.player.hasRelic(ModFile.makeID("PocketLab")))
            return 3;
        else return 2;
    }

    public static HerbCard getRandomHerbWithWeight() {
        int rarity = AbstractDungeon.cardRandomRng.random(99);
        if(HerbRarity.ELUSIVE.isWithinRange(rarity)) {
            ArrayList<AbstractCard> elusiveList = PouchManager.getElusiveHerbs().group;
            return (HerbCard) elusiveList.get(AbstractDungeon.cardRandomRng.random(elusiveList.size() -1));
        }
        if(HerbRarity.RARE.isWithinRange(rarity)) {
            ArrayList<AbstractCard> rareList = PouchManager.getRareHerbs().group;
            return (HerbCard) rareList.get(AbstractDungeon.cardRandomRng.random(rareList.size() -1));
        }
        if(HerbRarity.UNCOMMON.isWithinRange(rarity)) {
            ArrayList<AbstractCard> uncommonList = PouchManager.getUncommonHerbs().group;
            return (HerbCard) uncommonList.get(AbstractDungeon.cardRandomRng.random(uncommonList.size() -1));
        }
        if(HerbRarity.COMMON.isWithinRange(rarity)) {
            ArrayList<AbstractCard> commonList = PouchManager.getCommonHerbs().group;
            return (HerbCard) commonList.get(AbstractDungeon.cardRandomRng.random(commonList.size() -1));
        }
        return null;
    }

    public static void explosivePotionEffect() {
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped()) {
                atb(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.1F));
            }
        }
    }

    public static void playerExplosion() {
        atb(new VFXAction(new ExplosionSmallEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY), 0.1F));
    }
}
