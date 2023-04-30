package code.util;

import basemod.helpers.CardModifierManager;
import code.alchemy.ConcoctionActions;
import code.modifiers.*;

public class ModManager {
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
}
