package NobleWarriorMod.characters;

import NobleWarriorMod.enums.NobleWarriorEnum;
import basemod.abstracts.CustomPlayer;
import NobleWarriorMod.relics.BroadSword;
import NobleWarriorMod.enums.AbstractCardEnum;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import java.util.ArrayList;
import NobleWarriorMod.cards.BalancedStrike;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.core.CardCrawlGame;

public class NobleWarrior extends CustomPlayer {
    private static final int ENERGY_PER_TURN = 3;
    private static final int START_HP = 75;
    private static final int START_GOLD = 99;
    private static final Color ORANGE = CardHelper.getColor(192, 106, 18);
    private static final String CHAR_SHOULDER_1 = "images/character/shoulder.png";
    private static final String CHAR_SHOULDER_2 = "images/character/shoulder2.png";
    private static final String CHAR_CORPSE = "images/character/corpse.png";
    private static final String CHAR_ATLAS = "images/character/idle/skeleton.atlas";
    private static final String CHAR_JSON = "images/character/idle/skeleton.json";

    public NobleWarrior(String name, AbstractPlayer.PlayerClass setClass) {
        super(name, setClass, null, null, (String)null, null);

        initializeClass(null, CHAR_SHOULDER_2, CHAR_SHOULDER_1, CHAR_CORPSE,
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));

        loadAnimation(CHAR_ATLAS, CHAR_JSON, 1.0F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
    }

    public String getTitle(AbstractPlayer.PlayerClass playerClass) { return "The Noble Warrior"; }

    public AbstractCard.CardColor getCardColor() { return AbstractCardEnum.NOBLEWARRIOR_ORANGE; }

    public Color getCardRenderColor() { return ORANGE; }

    public Color getCardTrailColor() { return ORANGE; }

    public int getAscensionMaxHPLoss() { return 7; }

    public BitmapFont getEnergyNumFont() { return FontHelper.energyNumFontRed; }

    public String getCustomModeCharacterButtonSoundKey() { return "ATTACK_HEAVY"; }

    public String getLocalizedCharacterName() {  return "The Noble Warrior"; }

    public AbstractPlayer newInstance() { return (AbstractPlayer)new NobleWarrior("The Noble Warrior", NobleWarriorEnum.NOBLEWARRIOR_CLASS); }

    public String getSpireHeartText() { return "NL You ready your Sword..."; }

    public Color getSlashAttackColor() { return ORANGE; }

    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() { return new AbstractGameAction.AttackEffect[] { AbstractGameAction.AttackEffect.SLASH_HEAVY,
            AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.BLUNT_HEAVY, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE,
            AbstractGameAction.AttackEffect.BLUNT_HEAVY }; }

    public String getVampireText() { return (CardCrawlGame.languagePack.getEventString("Gatherer:Vampires")).DESCRIPTIONS[0]; }

    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("ATTACK_HEAVY", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
    }

    public AbstractCard getStartCardForEvent() { return (AbstractCard)new BalancedStrike(); }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo("The Noble Warrior",
                "Embroiled in a complex political and religious war, he seeks power in the Spire. NL The master of a variety of warrior type classes.",
                START_HP, START_HP, 0, START_GOLD, 5, (AbstractPlayer)this,
                getStartingRelics(), getStartingDeck(), false);
    }

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> startingDeck = new ArrayList<>();
        startingDeck.add("NobleWarrior:StrikeNW");
        startingDeck.add("NobleWarrior:StrikeNW");
        startingDeck.add("NobleWarrior:Sift");
        //startingDeck.add("NobleWarrior:StrikeNW");
        //startingDeck.add("NobleWarrior:StrikeNW");
        startingDeck.add("NobleWarrior:DefendNW");
        startingDeck.add("NobleWarrior:DefendNW");
        startingDeck.add("NobleWarrior:DefendNW");
        //startingDeck.add("NobleWarrior:DefendNW");
        startingDeck.add("NobleWarrior:LayOfTheLand");
        startingDeck.add("NobleWarrior:RainOfArrows");
        startingDeck.add("NobleWarrior:Sift");
        startingDeck.add("NobleWarrior:BalancedStrike");

        return startingDeck;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(BroadSword.ID);
        return retVal;
    }
}