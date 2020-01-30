package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.powers.ChokingGraspPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Decay;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class AmeNoMurakumo extends AbstractClassCard {
    private static final String ID = "NobleWarrior:AmeNoMurakumo";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int BLOCK = 7;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int CHOKE_AMT = 3;
    private static final AbstractCard PREV2 = new DrawOutAmeNoMurakumo();
    private static AbstractCard PREV2U = new DrawOutAmeNoMurakumo();

    public AmeNoMurakumo() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF, null);

        previews.add(new Decay());
        previews.add(PREV2);
        cardsToPreview = previews.get(0);
        block = baseBlock = BLOCK;
        magicNumber = baseMagicNumber = CHOKE_AMT;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new ApplyPowerAction(p, p, new ChokingGraspPower(p, CHOKE_AMT), CHOKE_AMT));
        addToBot(new MakeTempCardInDiscardAction(new Decay(), 1));
        AbstractCard c1 = new DrawOutAmeNoMurakumo();
        if(upgraded) { c1.upgrade(); }
        addToBot(new MakeTempCardInDiscardAction(c1, 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            AbstractCard p1 = new Decay();
            PREV2U.upgrade();
            previews = new ArrayList<>();
            previews.add(p1);
            previews.add(PREV2U);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
