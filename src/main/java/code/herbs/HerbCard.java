package code.herbs;

import code.alchemy.ConcoctionActions;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.TheAlchemist.Enums.ALCHEMIST_COLOR;
import static code.TheAlchemist.Enums.HERB_COLOR;


public abstract class HerbCard extends AbstractEasyCard {
    public final int brewPotency;
    public final int eatPotency;
    public final HerbRarity rarity;

    public final boolean throwable;
    public final boolean targeted;

    public HerbCard(String ID, int brewPotency, int eatPotency, HerbRarity rarity) {
        super(ID, -2, CardType.SKILL,
                !rarity.equals(HerbRarity.ELUSIVE) ? CardRarity.valueOf(rarity.name()) : CardRarity.SPECIAL, CardTarget.NONE, HERB_COLOR);
        this.brewPotency = brewPotency;
        this.eatPotency = eatPotency;
        this.rarity = rarity;
        this.throwable = false;
        this.targeted = false;
    }

    public HerbCard(String ID, int brewPotency, int eatPotency, HerbRarity rarity, boolean throwable, boolean targeted) {
        super(ID, -2, CardType.SKILL,
                !rarity.equals(HerbRarity.ELUSIVE) ? CardRarity.valueOf(rarity.name()) : CardRarity.SPECIAL, CardTarget.NONE, HERB_COLOR);
        this.brewPotency = brewPotency;
        this.eatPotency = eatPotency;
        this.rarity = rarity;
        this.throwable = throwable;
        this.targeted = targeted;
    }

    public abstract void brew(AbstractCreature target, ConcoctionActions actions);
    public abstract void eat();
}

