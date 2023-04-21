package code.alchemy;

import basemod.abstracts.CustomPotion;
import code.ModFile;
import code.herbs.HerbCard;
import code.util.BrewStand;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.PotionStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static code.ModFile.makeID;

public class Concoction extends CustomPotion {
    public static final String POTION_ID = makeID("Concoction");
    private static final PotionStrings potionStrings = CardCrawlGame.languagePack.getPotionString(POTION_ID);
    public static final String NAME = potionStrings.NAME;
    public static final String[] DESCRIPTIONS = potionStrings.DESCRIPTIONS;

    public static ArrayList<HerbCard> herbs = new ArrayList<>();
    public static ConcoctionActions actions = new ConcoctionActions();

    public Concoction(ArrayList<HerbCard> herbsToBrew) {
        super(NAME, POTION_ID, PotionRarity.PLACEHOLDER, PotionSize.M, PotionColor.STEROID);
        herbs = herbsToBrew;
        initializeData();
        this.isThrown = BrewStand.isConcoctionThrowable(herbsToBrew);
        this.targetRequired = BrewStand.isConcoctionTargetable(herbsToBrew);
        this.labOutlineColor = ModFile.characterColor;
    }

    public void initializeData() {
        this.description = (herbs != null && !herbs.isEmpty()) ? BrewStand.getConcoctionDescription(herbs) : DESCRIPTIONS[0];
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
    }

    public void use(AbstractCreature target) {
        actions.use(AbstractDungeon.player, (AbstractMonster) target);
    }

    public CustomPotion makeCopy() {
        return new Concoction(herbs);
    }

    public int getPotency(int ascensionLevel) {
        return -1;
    }
}
