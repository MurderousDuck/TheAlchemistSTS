package code.herbs;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

import static code.ModFile.modID;

public class CardTypePatches {

    //Taken from NellyDevo
    //Thank you!

    @SpirePatch(
            clz = SingleCardViewPopup.class,
            method = "renderCardTypeText"
    )
    public static class SingleCardViewPopupRenderCardTypeTextPatch {
        private static UIStrings uiStrings;
        private static String[] TEXT;
        @SpireInsertPatch(
                localvars = {"label"},
                locator = Locator.class
        )
        public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb, @ByRef String[] label) {
            if (uiStrings == null) {
                String ID = modID + ":HerbCardType";
                uiStrings = CardCrawlGame.languagePack.getUIString(ID);
                TEXT = uiStrings.TEXT;
            }
            AbstractCard reflectedCard = ReflectionHacks.getPrivate(__instance, SingleCardViewPopup.class, "card");
            if(reflectedCard instanceof HerbCard)
                label[0] = TEXT[0];
        }

        public static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderFontCentered");
                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            }
        }
    }

    @SpirePatch(
            clz=AbstractCard.class,
            method="renderType"
    )
    public static class AbstractCardRenderTypePatch {
        private static UIStrings uiStrings;
        private static String[] TEXT;
        @SpireInsertPatch(
                localvars={"text"},
                locator = Locator.class
        )
        public static void Insert(AbstractCard __instance, SpriteBatch sb, @ByRef String[] text) {
            if (uiStrings == null) {
                String ID = modID + ":HerbCardType";
                uiStrings = CardCrawlGame.languagePack.getUIString(ID);
                TEXT = uiStrings.TEXT;
            }
            if(__instance instanceof HerbCard)
                text[0] = TEXT[0];
        }

        public static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(FontHelper.class, "renderRotatedText");

                return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
            }
        }
    }
}

