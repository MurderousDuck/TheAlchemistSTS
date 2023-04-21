package code.herbs;

public enum HerbPriority {
    BLOCK(10),
    SHIELDLYM(11),
    WAVYCAP(12),

    DAMAGE(20),
    BLAZEPEPPER(21),
    CHERRYBURST(22),

    BUFFS(30),
    JOLTLEAF(31),
    BUFFBLOOM(32),
    AGILEAF(33),

    DEBUFFS(40),
    ROTLEAF(41),
    FRIGHTLURE(42),
    SAPPERVINE(43),

    CARD_MANIPULATION(50),
    RANDOMMUSH(100);

    public final int priority;
    HerbPriority(int priority) {
        this.priority = priority;
    }
}
