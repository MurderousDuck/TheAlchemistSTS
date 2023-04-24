package code.util;

import basemod.ReflectionHacks;
import code.ModFile;
import code.herbs.HerbCard;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.Soul;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class SoulHelper {
    public static void pouchSouls(AbstractCard card) {
        SoulGroup soulGroup = AbstractDungeon.getCurrRoom().souls;
        boolean needMoreSouls = true;
        ArrayList<Soul> souls = ReflectionHacks.getPrivate(soulGroup, SoulGroup.class, "souls");
        for (Soul s : souls) {
            if (s.isReadyForReuse) {
                card.untip();
                card.unhover();
                onToPouch(s, card);
                needMoreSouls = false;
                break;
            }
        }

        if (needMoreSouls) {
            Soul s = new Soul();
            onToPouch(s, card);
            souls.add(s);
        }
    }

    public static void onToPouch(Soul s, AbstractCard card) {
        s.group = ModFile.herbPouch;
        s.card = card;
        ReflectionHacks.setPrivate(s, Soul.class, "pos", new Vector2(s.card.current_x, s.card.current_y));
        ReflectionHacks.setPrivate(s, Soul.class, "target", new Vector2(ModFile.herbPouchButton.POUCH_X, ModFile.herbPouchButton.POUCH_Y));
        ReflectionHacks.privateMethod(Soul.class, "setSharedVariables").invoke(s);
        ReflectionHacks.setPrivate(s, Soul.class, "rotation", s.card.angle + 270.0F);
        ReflectionHacks.setPrivate(s, Soul.class, "rotateClockwise", true);
    }
}
