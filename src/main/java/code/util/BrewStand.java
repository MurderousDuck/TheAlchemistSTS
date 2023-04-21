package code.util;

import basemod.helpers.CardModifierManager;
import code.ModFile;
import code.alchemy.ConcoctionActions;
import code.herbs.HerbCard;
import code.herbs.common.Blazepepper;
import code.herbs.common.Cherryburst;
import code.herbs.common.Rotleaf;
import code.herbs.common.Wavycap;
import code.modifiers.*;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;

import java.util.ArrayList;

import static code.util.Wiz.atb;

public class BrewStand {

    public static boolean isConcoctionThrowable(ArrayList<HerbCard> herbsToBrew) {
        return herbsToBrew.stream().anyMatch(BrewStand::isHerbThrowable);
    }
    public static boolean isHerbThrowable(HerbCard herb) {
        return herb instanceof Blazepepper || herb instanceof Wavycap || herb instanceof Rotleaf || herb instanceof Cherryburst;
    }

    public static boolean isConcoctionTargetable(ArrayList<HerbCard> herbsToBrew) {
        return herbsToBrew.stream().anyMatch(BrewStand::isHerbTargetable);
    }
    public static boolean isHerbTargetable(HerbCard herb) {
        return herb instanceof Blazepepper || herb instanceof Wavycap || herb instanceof Rotleaf;
    }

    public static String getConcoctionDescription(ArrayList<HerbCard> herbs) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < herbs.size(); i++) {
            if(i < herbs.size() - 1) {
                sb.append(herbs.get(i).brewDescription).append(" ");
            } else {
                sb.append(herbs.get(i).brewDescription);
            }
        }
        return sb.toString();
    }

    public static int getBrewAmount() {
        if(AbstractDungeon.player.hasRelic(ModFile.makeID("PocketLab")))
            return 3;
        else return 2;
    }

    public static void updateStackableModifier(ConcoctionActions actions, StackableModifier mod) {
        if(!doSpecialStackingModifier(actions, mod)) {
            if(CardModifierManager.hasModifier(actions, mod.ID)) {
                StackableModifier modifier = (StackableModifier) CardModifierManager.getModifiers(actions, mod.ID).get(0);
                modifier.amount += mod.amount;
                actions.initializeDescription();
            } else {
                CardModifierManager.addModifier(actions, mod);
            }
        }
    }

    public static boolean doSpecialStackingModifier(ConcoctionActions actions, StackableModifier mod) {
        if(mod.ID.equals(DealDamageToAllModifier.ID)) {
            if(CardModifierManager.hasModifier(actions, DealToAllThenTakeModifier.ID)) {
                StackableModifier modifier = (StackableModifier) CardModifierManager.getModifiers(actions, DealToAllThenTakeModifier.ID).get(0);
                modifier.amount += mod.amount;
                actions.initializeDescription();
            } else {
                CardModifierManager.addModifier(actions, mod);
            }
            return true;
        }

        if(mod.ID.equals(DrawCardModifier.ID)) {
            if(CardModifierManager.hasModifier(actions, DiscardHandThenDrawModifier.ID)) {
                StackableModifier modifier = (StackableModifier) CardModifierManager.getModifiers(actions, DiscardHandThenDrawModifier.ID).get(0);
                modifier.amount += mod.amount;
                actions.initializeDescription();
            } else {
                CardModifierManager.addModifier(actions, mod);
            }
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
}
