package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.actions.unique.FeedAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Masamune extends AbstractClassCard {
    private static final String ID = "NobleWarrior:Masamune";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK = 12;
    private static final int UPGRADE_PLUS_ATTACK = 3;
    private static final int MAXHP = 2;
    private static final int UPGRADE_PLUS_MAXHP = 1;
    private static final AbstractCard PREV2 = new DrawOutMasamune();
    private static AbstractCard PREV2U = new DrawOutMasamune();
    private static final AbstractCard PREV1 = new Pain();

    public Masamune() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.ENEMY, null);

        previews.add(PREV1);
        previews.add(PREV2);
        cardsToPreview = previews.get(0);
        damage = baseDamage = ATTACK;
        magicNumber = baseMagicNumber = MAXHP;
        exhaust = true;
        this.tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new FeedAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.magicNumber));
        addToBot(new MakeTempCardInDiscardAction(new Pain(), 1));
        AbstractCard c1 = new DrawOutMasamune();
        if(upgraded) { c1.upgrade(); }
        addToBot(new MakeTempCardInDiscardAction(c1, 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_ATTACK);
            upgradeMagicNumber(UPGRADE_PLUS_MAXHP);
            PREV2U.upgrade();
            previews = new ArrayList<>();
            previews.add(PREV1);
            previews.add(PREV2U);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
