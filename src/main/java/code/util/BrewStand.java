package code.util;

import basemod.ReflectionHacks;
import basemod.helpers.CardModifierManager;
import code.ModFile;
import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.herbs.HerbRarity;
import code.herbs.common.*;
import code.herbs.elusive.Doubleye;
import code.herbs.elusive.MindsEye;
import code.herbs.elusive.Spectralite;
import code.herbs.rare.*;
import code.herbs.uncommon.*;
import code.modifiers.*;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.cards.SoulGroup;
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
            ArrayList<AbstractCard> elusiveList = getElusiveHerbs().group;
            return (HerbCard) elusiveList.get(AbstractDungeon.cardRandomRng.random(elusiveList.size() -1));
        }
        if(HerbRarity.RARE.isWithinRange(rarity)) {
            ArrayList<AbstractCard> rareList = getRareHerbs().group;
            return (HerbCard) rareList.get(AbstractDungeon.cardRandomRng.random(rareList.size() -1));
        }
        if(HerbRarity.UNCOMMON.isWithinRange(rarity)) {
            ArrayList<AbstractCard> uncommonList = getUncommonHerbs().group;
            return (HerbCard) uncommonList.get(AbstractDungeon.cardRandomRng.random(uncommonList.size() -1));
        }
        if(HerbRarity.COMMON.isWithinRange(rarity)) {
            ArrayList<AbstractCard> commonList = getCommonHerbs().group;
            return (HerbCard) commonList.get(AbstractDungeon.cardRandomRng.random(commonList.size() -1));
        }
        return null;
    }

    public static void updateStackableModifier(ConcoctionActions actions, StackableModifier mod) {
        if(!mergeChecker(actions, mod)) {
            if(CardModifierManager.hasModifier(actions, mod.modId)) {
                StackableModifier modifier = (StackableModifier) CardModifierManager.getModifiers(actions, mod.modId).get(0);
                modifier.amount += mod.amount;

                if(mod instanceof DealToAllThenTakeModifier)
                    ((DealToAllThenTakeModifier) modifier).selfDamage += ((DealToAllThenTakeModifier) mod).selfDamage;

                actions.initializeDescription();
            } else {
                CardModifierManager.addModifier(actions, mod);
            }
        }
    }

    public static boolean mergeChecker(ConcoctionActions actions, StackableModifier mod) {
        if(mod.modId.equals(DealDamageToAllModifier.ID)) {
            return specialMergeModifier(actions, mod, DealToAllThenTakeModifier.ID);
        }
        if(mod.modId.equals(DrawCardModifier.ID)) {
            return specialMergeModifier(actions, mod, DiscardHandThenDrawModifier.ID);
        }
        if(mod.modId.equals(DealToAllThenTakeModifier.ID)) {
            return specialRetroactiveMergeModifier(actions, mod, DealDamageToAllModifier.ID);
        }
        if(mod.modId.equals(DiscardHandThenDrawModifier.ID)) {
            return specialRetroactiveMergeModifier(actions, mod, DrawCardModifier.ID);
        }
        return false;
    }

    private static boolean specialMergeModifier(ConcoctionActions actions, StackableModifier mod, String mergeModId) {
        if(CardModifierManager.hasModifier(actions, mergeModId)) {
            StackableModifier mergeMod = (StackableModifier) CardModifierManager.getModifiers(actions, mergeModId).get(0);
            mergeMod.amount += mod.amount;
            actions.initializeDescription();
            return true;
        }
        return false;
    }

    private static boolean specialRetroactiveMergeModifier(ConcoctionActions actions, StackableModifier mod, String mergeModId) {
        if(CardModifierManager.hasModifier(actions, mergeModId)) {
            StackableModifier mergeMod = (StackableModifier) CardModifierManager.getModifiers(actions, mergeModId).get(0);
            mod.amount += mergeMod.amount;

            CardModifierManager.removeSpecificModifier(actions, mergeMod, true);
            CardModifierManager.addModifier(actions, mod);

            actions.initializeDescription();
            return true;
        }
        return false;
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

    public static void resetPouchWithAllHerbs(CardGroup herbPouch) {
        if(herbPouch != null) {
            emptyPouch(herbPouch);
            addAllHerbsToCardGroup(herbPouch);
            addAllHerbsToCardGroup(herbPouch);
        }
    }

    public static void emptyPouch(CardGroup herbPouch) {
        herbPouch.group.clear();
    }

    public static void addAllHerbsToCardGroup(CardGroup herbPouch) {
        herbPouch.group.addAll(getCommonHerbs().group);
        herbPouch.group.addAll(getUncommonHerbs().group);
        herbPouch.group.addAll(getRareHerbs().group);
        herbPouch.group.addAll(getElusiveHerbs().group);
    }

    public static CardGroup getCommonHerbs() {
        CardGroup commonHerbs = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        commonHerbs.addToBottom(new Blazepepper());
        commonHerbs.addToBottom(new Shieldlym());
        commonHerbs.addToBottom(new Wavycap());
        commonHerbs.addToBottom(new Cherryburst());
        commonHerbs.addToBottom(new Rotleaf());
        commonHerbs.addToBottom(new Buffbloom());
        commonHerbs.addToBottom(new Agileaf());
        return commonHerbs;
    }

    public static CardGroup getUncommonHerbs() {
        CardGroup uncommonHerbs = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        uncommonHerbs.addToBottom(new Frightlure());
        uncommonHerbs.addToBottom(new Sappervine());
        uncommonHerbs.addToBottom(new Joltleaf());
        uncommonHerbs.addToBottom(new Gummush());
        uncommonHerbs.addToBottom(new Swiftroot());
        uncommonHerbs.addToBottom(new ForgesEmbrace());
        uncommonHerbs.addToBottom(new Thornybulb());
        return uncommonHerbs;
    }

    public static CardGroup getRareHerbs() {
        CardGroup rareHerbs = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        rareHerbs.addToBottom(new Artiflower());
        rareHerbs.addToBottom(new Chaosbloom());
        rareHerbs.addToBottom(new GamblersFruit());
        rareHerbs.addToBottom(new Steeleaf());
        rareHerbs.addToBottom(new Nitrogliceroot());
        return rareHerbs;
    }

    public static CardGroup getElusiveHerbs() {
        CardGroup elusiveHerbs = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        elusiveHerbs.addToBottom(new Spectralite());
        elusiveHerbs.addToBottom(new MindsEye());
        elusiveHerbs.addToBottom(new Doubleye());
        return elusiveHerbs;
    }
}
