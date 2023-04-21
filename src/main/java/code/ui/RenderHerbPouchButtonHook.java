package code.ui;

import code.ModFile;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.ui.panels.DrawPilePanel;

public class RenderHerbPouchButtonHook {
    @SpirePatch(clz = DrawPilePanel.class, method = "render")
    public static class RenderFrozenButtonHook {
        public static void Postfix(DrawPilePanel __instance, SpriteBatch spriteBatch) {
            ModFile.renderCombatUiElements(spriteBatch);
        }
    }
}
