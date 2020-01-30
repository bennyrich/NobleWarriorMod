package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Pain;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenPower;

import java.util.ArrayList;

public class DrawOutMasamune extends AbstractClassCard {
    private static final String ID = "NobleWarrior:DrawOutMasamune";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK = 8;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int REGEN = 2;
    private static final int UPGRADE_PLUS_REGEN = 1;
    private static final AbstractCard PREV2 = new Masamune();
    private static AbstractCard PREV2U = new Masamune();
    private static final AbstractCard PREV1 = new Pain();

    public DrawOutMasamune() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.RARE, CardTarget.SELF, CardTagsEnum.SAMURAI);

        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = REGEN;
        previews.add(PREV1);
        previews.add(PREV2);
        cardsToPreview = previews.get(0);
        exhaust = true;
        tags.add(CardTagsEnum.SAMURAI);
        tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new ApplyPowerAction(p, p, new RegenPower(p, magicNumber), magicNumber));
        addToBot(new MakeTempCardInDrawPileAction(new Pain(), 1, true, true));
        AbstractCard c1 = new Masamune();
        if(upgraded) { c1.upgrade(); }
        addToBot(new MakeTempCardInDrawPileAction(c1, 1, true, true));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_PLUS_REGEN);
            PREV2U.upgrade();
            previews = new ArrayList<>();
            previews.add(PREV1);
            previews.add(PREV2U);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
