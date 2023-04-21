package code.ui;

import code.ModFile;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class ConcoctionBeltPatch {
    @SpirePatch(clz = AbstractPlayer.class, method = "combatUpdate")
    public static class PotionSackUpdatePatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractPlayer __instance) {
            ModFile.concoctionBelt.update();
        }
    }

    @SpirePatch(clz = EnergyPanel.class, method = "renderOrb")
    public static class PotionSackRenderPatch {
        @SpirePostfixPatch
        public static void Postfix(EnergyPanel __instance, SpriteBatch sb) {
            ModFile.concoctionBelt.render(sb);
            ModFile.concoctionBelt.potionUi.render(sb);
        }
    }

    @SpirePatch2(clz = AbstractPlayer.class, method = "updateFullKeyboardCardSelection")
    public static class SkipCardSelectPatch {
        @SpirePrefixPatch
        public static SpireReturn<Boolean> Prefix() {
            if (ModFile.concoctionBelt != null && ModFile.concoctionBelt.potionUi.targetMode)
                return SpireReturn.Return(Boolean.FALSE);
            return SpireReturn.Continue();
        }
    }
}
