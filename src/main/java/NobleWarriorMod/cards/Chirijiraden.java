package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Chirijiraden extends AbstractClassCard {
    private static final String ID = "NobleWarrior:Chirijiraden";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int ATTACK = 30;
    private static final int UPGRADE_PLUS_ATTACK = 10;
    private static final int EXHAUST = 1;
    private static final int UPGRADE_PLUS_EXHAUST = 1;
    private static final AbstractCard PREV2 = new DrawOutChirijiraden();
    private static AbstractCard PREV2U = new DrawOutChirijiraden();

    public Chirijiraden() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ALL_ENEMY, null);

        previews.add(new Burn());
        previews.add(PREV2);
        cardsToPreview = previews.get(0);
        damage = baseDamage = ATTACK;
        magicNumber = baseMagicNumber = EXHAUST;
        exhaust = true;
        isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ExhaustAction(magicNumber, false, false, false));
        addToBot(new MakeTempCardInDiscardAction(new Burn(), 1));
        AbstractCard c1 = new DrawOutChirijiraden();
        if(upgraded) { c1.upgrade(); }
        addToBot(new MakeTempCardInDiscardAction(c1, 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_ATTACK);
            upgradeMagicNumber(UPGRADE_PLUS_EXHAUST);
            AbstractCard p1 = new Burn();
            PREV2U.upgrade();
            previews = new ArrayList<>();
            previews.add(p1);
            previews.add(PREV2U);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
