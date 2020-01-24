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
import com.megacrit.cardcrawl.monsters.AbstractMonster;
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

    public static class Utils {
        public static boolean isAttacking(AbstractMonster mo) {
            return (mo.intent == AbstractMonster.Intent.ATTACK_DEBUFF || mo.intent == AbstractMonster.Intent.ATTACK
                    || mo.intent == AbstractMonster.Intent.ATTACK_BUFF || mo.intent == AbstractMonster.Intent.ATTACK_DEFEND);
        }
        public static boolean isDebuffing(AbstractMonster mo) {
            return (mo.intent == AbstractMonster.Intent.ATTACK_DEBUFF || mo.intent == AbstractMonster.Intent.DEBUFF
                    || mo.intent == AbstractMonster.Intent.DEFEND_DEBUFF || mo.intent == AbstractMonster.Intent.STRONG_DEBUFF);
        }
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

        BaseMod.addCard(new StrikeNW());
        BaseMod.addCard(new DefendNW());
        // SQUIRE
        BaseMod.addCard(new BalancedStrike());
        BaseMod.addCard(new Rush());
        BaseMod.addCard(new Yell());
        BaseMod.addCard(new ThoroughSearch());
        BaseMod.addCard(new CombatTraining());
        BaseMod.addCard(new Accumulate());
        BaseMod.addCard(new EquipArmor());
        BaseMod.addCard(new Salve());
        BaseMod.addCard(new ThrowStone());
        BaseMod.addCard(new DesperateDefense());
        // ARCHER
        BaseMod.addCard(new GainHeight());
        BaseMod.addCard(new PiercingShot());
        BaseMod.addCard(new SwiftShot());
        BaseMod.addCard(new VenomShot());
        BaseMod.addCard(new WildShot());
        BaseMod.addCard(new ChargeUp());
        //BaseMod.addCard(new LayOfTheLand());  //DEPRECATED
        BaseMod.addCard(new RainOfArrows());
        BaseMod.addCard(new SeekCover());
        BaseMod.addCard(new Sift());
        BaseMod.addCard(new AdrenalineRush());
        // DANCER
        BaseMod.addCard(new Disillusion());
        BaseMod.addCard(new FanDance());
        BaseMod.addCard(new WithKnives());
        BaseMod.addCard(new Fly());
        BaseMod.addCard(new BodyLanguage());
        BaseMod.addCard(new ReverseFlourish());
        BaseMod.addCard(new SlowDance());
        BaseMod.addCard(new BraveUp());
        BaseMod.addCard(new Polka());
        BaseMod.addCard(new ForbiddenWaltz());
        // GEOMANCER
        BaseMod.addCard(new CallUponTheEarth());
        BaseMod.addCard(new Kamaitachi());
        BaseMod.addCard(new Pitfall());
        BaseMod.addCard(new Snowstorm());
        BaseMod.addCard(new AttackBoost());
        BaseMod.addCard(new LawOfTheLand());
        BaseMod.addCard(new Sandstorm());
        BaseMod.addCard(new WindBlast());
        BaseMod.addCard(new CounterFlood());
        // KNIGHT
        BaseMod.addCard(new ArmorBreak());
        BaseMod.addCard(new PowerBreak());
        BaseMod.addCard(new SpeedBreak());
        BaseMod.addCard(new TacticalRetreat());
        BaseMod.addCard(new ArmUp());
        BaseMod.addCard(new CastleOfStone());
        BaseMod.addCard(new MindBreak());
        BaseMod.addCard(new ShieldBreak());
        BaseMod.addCard(new WeaponBreak());
        BaseMod.addCard(new WeaponGuard());
        // LANCER
        BaseMod.addCard(new SuperJump());
        BaseMod.addCard(new HighJump());
        BaseMod.addCard(new Jump());
        BaseMod.addCard(new AggressiveGuard());
        BaseMod.addCard(new CalculatedStrike());
        BaseMod.addCard(new ObserveBattle());
        BaseMod.addCard(new PolearmGuard());
        BaseMod.addCard(new WhirlingDefense());
        BaseMod.addCard(new MountingFury());
        BaseMod.addCard(new EvasiveJump());
        // MONK
        BaseMod.addCard(new Brawler());
        BaseMod.addCard(new RepeatingFist());
        BaseMod.addCard(new SpinFist());
        BaseMod.addCard(new SwiftStep());
        BaseMod.addCard(new Hamedo());

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
