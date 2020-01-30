package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CallUponTheEarth extends AbstractClassCard {
    private static final String ID = "NobleWarrior:CallUponTheEarth";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int ATTACK_DMG = 5;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 2;
    private static final int NUM_CARDS = 3;

    public CallUponTheEarth() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.NOBLEWARRIOR_ORANGE,
                CardRarity.COMMON, CardTarget.ENEMY, CardTagsEnum.GEOMANCER);

        this.damage = this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = NUM_CARDS;
        this.tags.add(CardTagsEnum.GEOMANCER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        if(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() > magicNumber) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR;
        }
    }

    public void triggerOnGlowCheck() {
        if(AbstractDungeon.actionManager.cardsPlayedThisTurn.size() >= magicNumber) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR;
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
        }
    }
}
