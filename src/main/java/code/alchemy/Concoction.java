package code.alchemy;

import basemod.BaseMod;
import basemod.abstracts.AbstractCardModifier;
import basemod.abstracts.CustomPotion;
import basemod.helpers.CardModifierManager;
import code.ModFile;
import code.herbs.HerbCard;
import code.herbs.common.Agileaf;
import code.herbs.common.Buffbloom;
import code.herbs.common.Rotleaf;
import code.herbs.common.Wavycap;
import code.herbs.elusive.MindsEye;
import code.herbs.elusive.Spectralite;
import code.herbs.rare.Artiflower;
import code.herbs.rare.Steeleaf;
import code.herbs.uncommon.*;
import code.modifiers.StackableModifier;
import code.util.BrewStand;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import java.util.ArrayList;
import java.util.Comparator;

import static code.ModFile.makeID;
import static code.util.Wiz.atb;

public class Concoction extends CustomPotion {
    public static final String POTION_ID = makeID("Concoction");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public ArrayList<HerbCard> herbs;
    public ConcoctionActions actions;

    public Concoction(ArrayList<HerbCard> herbsToBrew) {
        super(NAME, POTION_ID, PotionRarity.PLACEHOLDER, PotionSize.M, PotionColor.STEROID);
        actions = new ConcoctionActions();

        if(!herbsToBrew.isEmpty()) {
            herbs = herbsToBrew;
            for (HerbCard herb : herbsToBrew) {
                herb.brew(null, actions);
                CardModifierManager.modifiers(actions).sort(Comparator.comparingInt(value -> value.priority));
            }
            initializeDescription();
        }

        this.isThrown = BrewStand.isConcoctionThrowable(herbsToBrew);
        this.targetRequired = BrewStand.isConcoctionTargeted(herbsToBrew);
        this.labOutlineColor = ModFile.characterColor;
    }

    public void initializeData() {
    }

    private void initializeDescription() {
        StringBuilder desc = new StringBuilder();
        ArrayList<AbstractCardModifier> mods = CardModifierManager.modifiers(actions);
        for (int i = 0; i < mods.size(); i++) {
            if(i == 0)
                desc.append(((StackableModifier)mods.get(i)).getConcoctionString().trim());
            else
                desc.append(((StackableModifier)mods.get(i)).getConcoctionString());
            if(i != mods.size() -1)
                desc.append(" NL");
        }

        this.description = desc.toString();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, desc.toString()));
        initializePowerTips();
    }

    private void initializePowerTips() {
        if(herbs.stream().anyMatch(h -> h instanceof Rotleaf || h instanceof Wavycap))
            this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.BLOCK.NAMES[0]),
                    GameDictionary.keywords.get(GameDictionary.BLOCK.NAMES[0])));

        if(herbs.stream().anyMatch(h -> h instanceof Rotleaf))
            this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.POISON.NAMES[0]),
                    GameDictionary.keywords.get(GameDictionary.POISON.NAMES[0])));

        if(herbs.stream().anyMatch(h -> h instanceof Buffbloom))
            this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.STRENGTH.NAMES[0]),
                    GameDictionary.keywords.get(GameDictionary.STRENGTH.NAMES[0])));

        if(herbs.stream().anyMatch(h -> h instanceof Agileaf))
            this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.DEXTERITY.NAMES[0]),
                    GameDictionary.keywords.get(GameDictionary.DEXTERITY.NAMES[0])));

        if(herbs.stream().anyMatch(h -> h instanceof Frightlure))
            this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.VULNERABLE.NAMES[0]),
                    GameDictionary.keywords.get(GameDictionary.VULNERABLE.NAMES[0])));

        if(herbs.stream().anyMatch(h -> h instanceof Sappervine))
            this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.WEAK.NAMES[0]),
                    GameDictionary.keywords.get(GameDictionary.WEAK.NAMES[0])));

        if(herbs.stream().anyMatch(h -> h instanceof Gummush))
            this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.RETAIN.NAMES[0]),
                    GameDictionary.keywords.get(GameDictionary.RETAIN.NAMES[0])));

        if(herbs.stream().anyMatch(h -> h instanceof ForgesEmbrace))
            this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.UPGRADE.NAMES[0]),
                    GameDictionary.keywords.get(GameDictionary.UPGRADE.NAMES[0])));

        if(herbs.stream().anyMatch(h -> h instanceof Thornybulb))
            this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.THORNS.NAMES[0]),
                    GameDictionary.keywords.get(GameDictionary.THORNS.NAMES[0])));

        if(herbs.stream().anyMatch(h -> h instanceof Artiflower))
            this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.ARTIFACT.NAMES[0]),
                    GameDictionary.keywords.get(GameDictionary.ARTIFACT.NAMES[0])));

        if(herbs.stream().anyMatch(h -> h instanceof Spectralite))
            this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.INTANGIBLE.NAMES[0]),
                    GameDictionary.keywords.get(GameDictionary.INTANGIBLE.NAMES[0])));

        if(herbs.stream().anyMatch(h -> h instanceof MindsEye))
            this.tips.add(new PowerTip(BaseMod.getKeywordTitle("stslib:fetch"),
                    BaseMod.getKeywordDescription("stslib:fetch")));

        if(herbs.stream().anyMatch(h -> h instanceof Steeleaf))
            this.tips.add(new PowerTip(BaseMod.getKeywordProper("alchemy:metal"),
                    BaseMod.getKeywordDescription("alchemy:metal")));
    }

    public void use(AbstractCreature target) {
        for (AbstractCardModifier mod : CardModifierManager.modifiers(actions)) {
            mod.onUse(actions, target, null);
        }
    }

    public CustomPotion makeCopy() {
        return new Concoction(herbs);
    }

    public int getPotency(int ascensionLevel) {
        return -1;
    }
}
