package code.util;

import code.ModFile;
import code.herbs.common.*;
import code.herbs.elusive.Doubleye;
import code.herbs.elusive.MindsEye;
import code.herbs.elusive.Nitrogliceroot;
import code.herbs.elusive.Spectralite;
import code.herbs.rare.*;
import code.herbs.uncommon.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;

public class PouchManager {
    public static CardGroup getHerbPouchInAlphabeticalOrder() {
        CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard c : ModFile.herbPouch.group)
            temp.addToTop(c);
        temp.sortAlphabetically(true);
        return temp;
    }

    public static void addHerbToPouch(AbstractCard herb) {
        if(ModFile.herbPouch.size() >= ModFile.pouchSize) {
            ModFile.herbPouch.removeTopCard();
        }
        ModFile.herbPouch.addToBottom(herb);
    }

    public static void resetPouchWithAllHerbs(CardGroup herbPouch) {
        if(herbPouch != null) {
            emptyPouch();
            addAllHerbsToCardGroup(herbPouch);
        }
    }

    public static void emptyPouch() {
        ModFile.herbPouch.group.clear();
    }

    public static void addAllHerbsToCardGroup(CardGroup cardGroup) {
        cardGroup.group.addAll(getAllHerbs().group);
    }

    public static CardGroup getAllHerbs() {
        CardGroup allHerbs = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        allHerbs.group.addAll(getCommonHerbs().group);
        allHerbs.group.addAll(getUncommonHerbs().group);
        allHerbs.group.addAll(getRareHerbs().group);
        allHerbs.group.addAll(getElusiveHerbs().group);
        return allHerbs;
    }

    public static CardGroup getCommonHerbs() {
        CardGroup commonHerbs = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        commonHerbs.addToBottom(new Blazepepper());
        commonHerbs.addToBottom(new Shieldlym());
        commonHerbs.addToBottom(new Wavycap());
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
        uncommonHerbs.addToBottom(new Cherryburst());
        return uncommonHerbs;
    }

    public static CardGroup getRareHerbs() {
        CardGroup rareHerbs = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        rareHerbs.addToBottom(new Artiflower());
        rareHerbs.addToBottom(new Chaosbloom());
        rareHerbs.addToBottom(new GamblersFruit());
        rareHerbs.addToBottom(new Steeleaf());
        rareHerbs.addToBottom(new Thornybulb());
        return rareHerbs;
    }

    public static CardGroup getElusiveHerbs() {
        CardGroup elusiveHerbs = new CardGroup(CardGroup.CardGroupType.CARD_POOL);
        elusiveHerbs.addToBottom(new Spectralite());
        elusiveHerbs.addToBottom(new MindsEye());
        elusiveHerbs.addToBottom(new Doubleye());
        elusiveHerbs.addToBottom(new Nitrogliceroot());
        return elusiveHerbs;
    }
}
