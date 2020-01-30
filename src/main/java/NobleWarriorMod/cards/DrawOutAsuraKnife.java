package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class DrawOutAsuraKnife extends AbstractClassCard {
    private static final String ID = "NobleWarrior:DrawOutAsuraKnife";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK = 8;
    private static final int UPGRADE_PLUS_ATTACK = 3;
    private static final int DRAW = 1;
    private static final AbstractCard PREV2 = new AsuraKnife();
    private static AbstractCard PREV2U = new AsuraKnife();
    private static final AbstractCard PREV1 = new Dazed();

    public DrawOutAsuraKnife() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.COMMON, CardTarget.ENEMY, CardTagsEnum.SAMURAI);

        damage = baseDamage = ATTACK;
        magicNumber = baseMagicNumber = DRAW;
        previews.add(PREV1);
        previews.add(PREV2);
        cardsToPreview = previews.get(0);
        exhaust = true;
        tags.add(CardTagsEnum.SAMURAI);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new DrawCardAction(magicNumber));
        addToBot(new MakeTempCardInDrawPileAction(new Dazed(), 1, true, true));
        AbstractCard c1 = new AsuraKnife();
        if(upgraded) { c1.upgrade(); }
        addToBot(new MakeTempCardInDrawPileAction(c1, 1, true, true));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_ATTACK);
            PREV2U.upgrade();
            previews = new ArrayList<>();
            previews.add(PREV1);
            previews.add(PREV2U);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
