package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

public class Murasame extends AbstractClassCard {
    private static final String ID = "NobleWarrior:Murasame";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK = 12;
    private static final int UPGRADE_PLUS_ATTACK = 4;
    private static final int STR = 2;
    private static final int UPGRADE_PLUS_STR = 1;
    private static final AbstractCard PREV2 = new DrawOutMurasame();
    private static AbstractCard PREV2U = new DrawOutMurasame();
    private static final AbstractCard PREV1 = new Wound();

    public Murasame() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY, null);

        previews.add(PREV1);
        previews.add(PREV2);
        cardsToPreview = previews.get(0);
        damage = baseDamage = ATTACK;
        magicNumber = baseMagicNumber = STR;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber), magicNumber));
        addToBot(new MakeTempCardInDiscardAction(new Wound(), 1));
        AbstractCard c1 = new DrawOutMurasame();
        if(upgraded) { c1.upgrade(); }
        addToBot(new MakeTempCardInDiscardAction(c1, 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_ATTACK);
            upgradeMagicNumber(UPGRADE_PLUS_STR);
            PREV2U.upgrade();
            previews = new ArrayList<>();
            previews.add(PREV1);
            previews.add(PREV2U);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
