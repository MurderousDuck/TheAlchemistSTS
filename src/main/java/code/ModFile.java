package code;

import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.devcommands.ConsoleCommand;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import code.alchemy.ConcoctionBelt;
import code.cards.AbstractEasyCard;
import code.cards.cardvars.SecondDamage;
import code.cards.cardvars.SecondMagicNumber;
import code.herbs.common.Blazepepper;
import code.herbs.common.Cherryburst;
import code.herbs.common.Shieldlym;
import code.herbs.common.Wavycap;
import code.relics.AbstractEasyRelic;
import code.ui.HerbPouchButton;
import code.util.BrewStand;
import code.util.Wiz;
import code.util.commands.ResetAndFillPouch;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import java.nio.charset.StandardCharsets;

@SuppressWarnings({"unused", "WeakerAccess"})
@SpireInitializer
public class ModFile implements
        EditCardsSubscriber,
        EditRelicsSubscriber,
        EditStringsSubscriber,
        EditKeywordsSubscriber,
        EditCharactersSubscriber,
        StartGameSubscriber,
        PostDungeonUpdateSubscriber,
        PostBattleSubscriber,
        PostInitializeSubscriber {

    public static final String modID = "alchemy";

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static Color characterColor = new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1); // This should be changed eventually
    public static HerbPouchButton herbPouchButton;
    public static CardGroup herbPouch;
    public static ConcoctionBelt concoctionBelt;
    public static int herbPouchKey = 34;
    public static int[] potionSackKeys = new int[] { 37, 43, 44, 45 };

    public static final String SHOULDER1 = modID + "Resources/images/char/mainChar/shoulder.png";
    public static final String SHOULDER2 = modID + "Resources/images/char/mainChar/shoulder2.png";
    public static final String CORPSE = modID + "Resources/images/char/mainChar/corpse.png";
    private static final String ATTACK_S_ART = modID + "Resources/images/512/attack.png";
    private static final String SKILL_S_ART = modID + "Resources/images/512/skill.png";
    private static final String POWER_S_ART = modID + "Resources/images/512/power.png";
    private static final String CARD_ENERGY_S = modID + "Resources/images/512/energy.png";
    private static final String TEXT_ENERGY = modID + "Resources/images/512/text_energy.png";
    private static final String ATTACK_L_ART = modID + "Resources/images/1024/attack.png";
    private static final String SKILL_L_ART = modID + "Resources/images/1024/skill.png";
    private static final String POWER_L_ART = modID + "Resources/images/1024/power.png";
    private static final String CARD_ENERGY_L = modID + "Resources/images/1024/energy.png";
    private static final String CHARSELECT_BUTTON = modID + "Resources/images/charSelect/charButton.png";
    private static final String CHARSELECT_PORTRAIT = modID + "Resources/images/charSelect/charBG.png";

    public static Settings.GameLanguage[] SupportedLanguages = {
            Settings.GameLanguage.ENG,
    };

    private String getLangString() {
        for (Settings.GameLanguage lang : SupportedLanguages) {
            if (lang.equals(Settings.language)) {
                return Settings.language.name().toLowerCase();
            }
        }
        return "eng";
    }

    public ModFile() {
        BaseMod.subscribe(this);

        BaseMod.addColor(TheAlchemist.Enums.ALCHEMIST_COLOR, characterColor, characterColor, characterColor,
                characterColor, characterColor, characterColor, characterColor,
                ATTACK_S_ART, SKILL_S_ART, POWER_S_ART, CARD_ENERGY_S,
                ATTACK_L_ART, SKILL_L_ART, POWER_L_ART,
                CARD_ENERGY_L, TEXT_ENERGY);
    }

    public static String makePath(String resourcePath) {
        return modID + "Resources/" + resourcePath;
    }

    public static String makeImagePath(String resourcePath) {
        return modID + "Resources/images/" + resourcePath;
    }

    public static String makeRelicPath(String resourcePath) {
        return modID + "Resources/images/relics/" + resourcePath;
    }

    public static String makePowerPath(String resourcePath) {
        return modID + "Resources/images/powers/" + resourcePath;
    }

    public static String makeCardPath(String resourcePath) {
        return modID + "Resources/images/cards/" + resourcePath;
    }

    public static void initialize() {
        ModFile thismod = new ModFile();
    }



    @Override
    public void receiveEditCharacters() {
        BaseMod.addCharacter(new TheAlchemist(TheAlchemist.characterStrings.NAMES[1], TheAlchemist.Enums.THE_ALCHEMIST),
                CHARSELECT_BUTTON, CHARSELECT_PORTRAIT, TheAlchemist.Enums.THE_ALCHEMIST);
    }

    @Override
    public void receiveEditRelics() {
        new AutoAdd(modID)
                .packageFilter(AbstractEasyRelic.class)
                .any(AbstractEasyRelic.class, (info, relic) -> {
                    if (relic.color == null) {
                        BaseMod.addRelic(relic, RelicType.SHARED);
                    } else {
                        BaseMod.addRelicToCustomPool(relic, relic.color);
                    }
                    if (!info.seen) {
                        UnlockTracker.markRelicAsSeen(relic.relicId);
                    }
                });
    }

    @Override
    public void receiveEditCards() {
        BaseMod.addDynamicVariable(new SecondMagicNumber());
        BaseMod.addDynamicVariable(new SecondDamage());
        new AutoAdd(modID)
                .packageFilter(AbstractEasyCard.class)
                .setDefaultSeen(true)
                .cards();
    }


    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + getLangString() + "/Cardstrings.json");
        BaseMod.loadCustomStringsFile(CardStrings.class, modID + "Resources/localization/" + getLangString() + "/Herbstrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, modID + "Resources/localization/" + getLangString() + "/Relicstrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, modID + "Resources/localization/" + getLangString() + "/Charstrings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, modID + "Resources/localization/" + getLangString() + "/Powerstrings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, modID + "Resources/localization/" + getLangString() + "/UIstrings.json");
        BaseMod.loadCustomStringsFile(PotionStrings.class, modID + "Resources/localization/" + getLangString() + "/Potionstrings.json");
    }

    @Override
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String json = Gdx.files.internal(modID + "Resources/localization/eng/Keywordstrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        com.evacipated.cardcrawl.mod.stslib.Keyword[] keywords = gson.fromJson(json, com.evacipated.cardcrawl.mod.stslib.Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(modID, keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }

    public static void renderCombatUiElements(SpriteBatch sb)
    {
        if(AbstractDungeon.player instanceof TheAlchemist) {
            if(Wiz.isInCombat())
                renderHerbPouch(sb, AbstractDungeon.overlayMenu.combatDeckPanel.current_x);
        }
    }

    public static void renderHerbPouch(SpriteBatch spriteBatch, float x) {
        if (herbPouchButton != null) {
            herbPouchButton.setX(x);
            herbPouchButton.render(spriteBatch);
        }
    }

    @Override
    public void receivePostDungeonUpdate() {
        if(AbstractDungeon.player instanceof TheAlchemist) {
            if (herbPouchButton != null)
                herbPouchButton.update();
        }
    }

    @Override
    public void receiveStartGame() {
        if(AbstractDungeon.player instanceof TheAlchemist) {
            herbPouchButton = new HerbPouchButton();
            herbPouch = new CardGroup(CardGroup.CardGroupType.DRAW_PILE);
            BrewStand.resetPouchWithAllHerbs(herbPouch);

            concoctionBelt = new ConcoctionBelt();
            concoctionBelt.show();
            concoctionBelt.update();
            concoctionBelt.removeAllPotions();
        }
    }

    @Override
    public void receivePostBattle(AbstractRoom abstractRoom) {
        if(AbstractDungeon.player instanceof TheAlchemist) {
            herbPouch.clear();
            concoctionBelt.hide();
            concoctionBelt.removeAllPotions();
        }
    }

    @Override
    public void receivePostInitialize() {
        ConsoleCommand.addCommand("resetAndFillPouch", ResetAndFillPouch.class);
    }
}
