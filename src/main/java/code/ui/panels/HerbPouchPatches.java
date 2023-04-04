package code.ui.panels;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class HerbPouchPatches {
    @SpirePatch(
            clz= OverlayMenu.class,
            method=SpirePatch.CLASS
    )
    public static class PouchPanelField
    {
        public static SpireField<HerbPouchPanel> herbPouch = new SpireField<>(HerbPouchPanel::new);
    }

    @SpirePatch(
            clz= OverlayMenu.class,
            method="showCombatPanels"
    )
    public static class ShowPouchInCombat
    {
        public static void Postfix()
        {
            PouchPanelField.herbPouch.get(AbstractDungeon.overlayMenu).show();
        }
    }

    @SpirePatch(
            clz= OverlayMenu.class,
            method="hideCombatPanels"
    )
    public static class HidePouchAfterCombat
    {
        public static void Postfix()
        {
            PouchPanelField.herbPouch.get(AbstractDungeon.overlayMenu).hide();
        }
    }

    @SpirePatch(
            clz= OverlayMenu.class,
            method="render"
    )
    public static class HerbPouchRender
    {
        public static void Postfix(OverlayMenu __instance, SpriteBatch sb)
        {
            PouchPanelField.herbPouch.get(__instance).render(sb);
        }
    }
}
