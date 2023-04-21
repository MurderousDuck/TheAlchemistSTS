package code.ui;

import basemod.ReflectionHacks;
import code.ModFile;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;
import javassist.CtBehavior;

public class ExhaustPileViewScreenPatches {
  public static boolean showHerbs = false;
  
  private static boolean isShowingHerbs = false;
  
  private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModFile.makeID("HerbPouchViewScreen"));
  
  @SpirePatch(clz = ExhaustPileViewScreen.class, method = "open")
  public static class OpenExhaustPileViewScreenPatch {
    @SpireInsertPatch(locator = ExhaustPileViewScreenPatches.OpenExhaustPileViewScreenPatchLocator.class)
    public static void Insert(ExhaustPileViewScreen __instance) {
      if (ExhaustPileViewScreenPatches.showHerbs) {
        CardGroup group = (CardGroup) ReflectionHacks.getPrivate(__instance, ExhaustPileViewScreen.class, "exhaustPileCopy");
        group.clear();
        group.group.addAll(ModFile.herbPouch.group);
        ExhaustPileViewScreenPatches.showHerbs = false;
        ExhaustPileViewScreenPatches.isShowingHerbs = true;
      } else {
        ExhaustPileViewScreenPatches.isShowingHerbs = false;
      } 
    }
  }
  
  private static class OpenExhaustPileViewScreenPatchLocator extends SpireInsertLocator {
    public int[] Locate(CtBehavior ctBehavior) throws Exception {
      Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ExhaustPileViewScreen.class, "hideCards");
      return LineFinder.findInOrder(ctBehavior, (Matcher)methodCallMatcher);
    }
  }
  
  @SpirePatch(clz = ExhaustPileViewScreen.class, method = "render")
  public static class RenderExhaustPileViewScreenPatch {
    @SpireInsertPatch(locator = ExhaustPileViewScreenPatches.RenderExhaustPileViewScreenPatchLocator.class)
    public static SpireReturn<Void> Insert(ExhaustPileViewScreen __instance, SpriteBatch sb) {
      if (ExhaustPileViewScreenPatches.isShowingHerbs) {
        FontHelper.renderDeckViewTip(sb, ExhaustPileViewScreenPatches.uiStrings.TEXT[0], 96.0F * Settings.scale, Settings.CREAM_COLOR);
        return SpireReturn.Return(null);
      } 
      return SpireReturn.Continue();
    }
  }
  
  private static class RenderExhaustPileViewScreenPatchLocator extends SpireInsertLocator {
    public int[] Locate(CtBehavior ctBehavior) throws Exception {
      Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderDeckViewTip");
      return LineFinder.findInOrder(ctBehavior, (Matcher)methodCallMatcher);
    }
  }
}