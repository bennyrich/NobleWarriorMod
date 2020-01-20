package NobleWarriorMod;

import NobleWarriorMod.modules.Keyword;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.compression.lzma.Base;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import jdk.nashorn.internal.codegen.CompilerConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import basemod.BaseMod;
import basemod.ModPanel;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.NobleWarriorEnum;
import com.megacrit.cardcrawl.helpers.CardHelper;
import NobleWarriorMod.characters.NobleWarrior;
import NobleWarriorMod.cards.*;
import NobleWarriorMod.relics.BroadSword;

import java.nio.charset.StandardCharsets;


@SpireInitializer
public class NobleWarriorMod implements PostInitializeSubscriber, EditCardsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber, EditStringsSubscriber, EditKeywordsSubscriber {

    public static final Logger logger = LogManager.getLogger(NobleWarriorMod.class.getName());
    private static final String MODNAME = "The Noble Warrior";
    private static final String AUTHOR = "Benny and Friends";
    private static final Color ORANGE = CardHelper.getColor(192, 106, 18);
    private static final String DESCRIPTION = "Adds the Noble Warrior as a new playable character, with multiple class choices";
    private static final String ATTACK_ORANGE = "images/cardui/512/bg_attack_orange.png";
    private static final String SKILL_ORANGE = "images/cardui/512/bg_skill_orange.png";
    private static final String POWER_ORANGE = "images/cardui/512/bg_power_orange.png";
    private static final String ENERGY_ORB = "images/cardui/512/card_purpleorange_orb.png";
    private static final String ATTACK_ORANGE_PORTRAIT = "images/cardui/1024/bg_attack_orange.png";
    private static final String SKILL_ORANGE_PORTRAIT = "images/cardui/1024/bg_skill_orange.png";
    private static final String POWER_ORANGE_PORTRAIT = "images/cardui/1024/bg_power_orange.png";
    private static final String ENERGY_ORB_PORTRAIT = "images/cardui/1024/card_purpleorange_orb2.png";
    private static final String ENERGY_ORB_PORTRAIT_SMALL = "images/cardui/512/card_purpleorange_orb_small.png";
    private static final String CHAR_BUTTON = "images/ui/charSelect/testButtonNobleWarrior.png";
    private static final String CHAR_BG = "images/ui/charSelect/charSelectBG.jpg";
    private static final String CARD_STRINGS = "localization/eng/CardStrings.json";
    private static final String RELIC_STRINGS = "localization/eng/RelicStrings.json";
    private static final String POWER_STRINGS = "localization/eng/PowerStrings.json";
    private static final String KEYWORD_STRINGS = "localization/eng/KeywordStrings.json";
    private static final String UI_STRINGS = "localization/eng/UIStrings.json";
    private static final String MODBADGE = "images/badge.png";

    public NobleWarriorMod() {

        BaseMod.subscribe((ISubscriber)this);

        logger.info("Creating color Orange as 192/106/18 R/G/B (BaseMod.addColor())");
        BaseMod.addColor(AbstractCardEnum.NOBLEWARRIOR_ORANGE, ORANGE, ATTACK_ORANGE, SKILL_ORANGE, POWER_ORANGE, ENERGY_ORB,
                ATTACK_ORANGE_PORTRAIT, SKILL_ORANGE_PORTRAIT, POWER_ORANGE_PORTRAIT, ENERGY_ORB_PORTRAIT, ENERGY_ORB_PORTRAIT_SMALL);
        logger.info("Finished call to BaseMod.addColor");
    }

    public static void initialize() {
        logger.info("Initializing Noble Warrior mod");
        new NobleWarriorMod();
        logger.info("Noble Warrior mod initialization complete");
    }

    public static String getCardImagePath(String cardID) { return "images/cards/" + cardID.replaceFirst("NobleWarrior:", "") + ".png"; }
    public static String getRelicImagePath(String cardID) { return "images/relics/" + cardID.replaceFirst("NobleWarrior:", "") + ".png"; }
    public static String getPowerImagePath(String cardID) { return "images/powers/" + cardID.replaceFirst("NobleWarrior:", "") + ".png"; }
    public static String getRelicOutlineImagePath(String cardID) { return "images/relics/outlines/" + cardID.replaceFirst("NobleWarrior:", "") + ".png"; }

    public void receivePostInitialize() {
        ModPanel settingsPanel = new ModPanel();
        BaseMod.registerModBadge(new Texture(MODBADGE), MODNAME, AUTHOR, DESCRIPTION, settingsPanel); // Change first null to a mod badge image if I ever make one
    }


    public void receiveEditCharacters() {
        logger.info("Begin editing characters");
        logger.info("Add " + NobleWarriorEnum.NOBLEWARRIOR_CLASS.toString());

        BaseMod.addCharacter((AbstractPlayer)new NobleWarrior(MODNAME, NobleWarriorEnum.NOBLEWARRIOR_CLASS), CHAR_BUTTON, CHAR_BG, NobleWarriorEnum.NOBLEWARRIOR_CLASS);

        logger.info("Done editing characters");
    }

    public void receiveEditCards() {
        logger.info("Begin editing cards");
        logger.info("Add cards for " + NobleWarriorEnum.NOBLEWARRIOR_CLASS.toString());

        BaseMod.addCard((AbstractCard)new StrikeNW());
        BaseMod.addCard((AbstractCard)new DefendNW());
        BaseMod.addCard((AbstractCard)new BalancedStrike());
        BaseMod.addCard((AbstractCard)new Rush());
        BaseMod.addCard((AbstractCard)new Yell());
        BaseMod.addCard((AbstractCard)new ThoroughSearch());
        BaseMod.addCard((AbstractCard)new CombatTraining());
        BaseMod.addCard((AbstractCard)new SuperJump());
        BaseMod.addCard((AbstractCard)new GainHeight());
        BaseMod.addCard((AbstractCard)new Accumulate());
        BaseMod.addCard((AbstractCard)new EquipArmor());
        BaseMod.addCard((AbstractCard)new Salve());
        BaseMod.addCard((AbstractCard)new ThrowStone());
        BaseMod.addCard((AbstractCard)new DesperateDefense());
        BaseMod.addCard((AbstractCard)new PiercingShot());
        BaseMod.addCard((AbstractCard)new SwiftShot());
        BaseMod.addCard((AbstractCard)new VenomShot());
        BaseMod.addCard((AbstractCard)new WildShot());
        BaseMod.addCard((AbstractCard)new ChargeUp());
        BaseMod.addCard((AbstractCard)new LayOfTheLand());
        BaseMod.addCard((AbstractCard)new RainOfArrows());
        BaseMod.addCard((AbstractCard)new SeekCover());
        BaseMod.addCard((AbstractCard)new Sift());
        BaseMod.addCard((AbstractCard)new Disillusion());
        BaseMod.addCard((AbstractCard)new FanDance());
        BaseMod.addCard((AbstractCard)new WithKnives());
        BaseMod.addCard((AbstractCard)new Fly());
        BaseMod.addCard((AbstractCard)new BodyLanguage());
        BaseMod.addCard((AbstractCard)new ReverseFlourish());
        BaseMod.addCard((AbstractCard)new SlowDance());
        BaseMod.addCard((AbstractCard)new CallUponTheEarth());
        BaseMod.addCard((AbstractCard)new Kamaitachi());
        BaseMod.addCard((AbstractCard)new Pitfall());
        BaseMod.addCard((AbstractCard)new Snowstorm());

        logger.info("Done editing cards");
    }

    public void receiveEditStrings() {
        logger.info("Begin editing strings");

        BaseMod.loadCustomStringsFile(RelicStrings.class, RELIC_STRINGS);
        BaseMod.loadCustomStringsFile(CardStrings.class, CARD_STRINGS);
        BaseMod.loadCustomStringsFile(PowerStrings.class, POWER_STRINGS);
        BaseMod.loadCustomStringsFile(UIStrings.class, UI_STRINGS);

        logger.info("Done editing strings");
    }

    public void receiveEditRelics() {
        logger.info("Begin editing relics");

        BaseMod.addRelicToCustomPool((AbstractRelic)new BroadSword(), AbstractCardEnum.NOBLEWARRIOR_ORANGE);

        logger.info("Done editing relics");
    }

    public void receiveEditKeywords() {
        logger.info("Setting up custom keywords");

        Gson gson = new Gson();
        Keyword[] keywords = (Keyword[])gson.fromJson(Gdx.files.internal(KEYWORD_STRINGS).readString(String.valueOf(StandardCharsets.UTF_8)), Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
            }
        }

        logger.info("Done setting up keywords");
    }

}
