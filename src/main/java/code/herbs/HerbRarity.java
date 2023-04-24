package code.herbs;

public enum HerbRarity {
    COMMON(40, 99),
    UNCOMMON(10, 39),
    RARE(1, 9),
    ELUSIVE(0, 0);

    public final int start;
    public final int end;
    HerbRarity(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public boolean isWithinRange(int roll) {
        return roll >= this.start && roll <= this.end;
    }
}
