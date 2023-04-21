package code.herbs;

import code.alchemy.ConcoctionActions;
import code.cards.AbstractEasyCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

import static code.TheAlchemist.Enums.ALCHEMIST_COLOR;


public abstract class HerbCard extends AbstractEasyCard {
    public final String brewDescription;
    public final int brewPotency;
    public final int eatPotency;
    public HerbCard(String ID, int brewPotency, int eatPotency) {
        super(ID, -2, CardType.SKILL, CardRarity.SPECIAL, CardTarget.NONE, ALCHEMIST_COLOR);
        this.brewPotency = brewPotency;
        this.eatPotency = eatPotency;
        this.brewDescription = this.cardStrings.EXTENDED_DESCRIPTION[0];
    }

    public abstract void brew(AbstractCreature target, ConcoctionActions actions);
    public abstract void eat();
}

