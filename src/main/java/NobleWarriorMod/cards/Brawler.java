package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;

public class Brawler extends AbstractClassCard {
    private static final String ID = "NobleWarrior:Brawler";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMOUNT = 8;
    private static final int UPGRADE_PLUS_BLOCK_AMOUNT = 3;
    private static final int CARDS_TO_DRAW = 1;
    private static final int UPGRADE_PLUS_CARDS_TO_DRAW = 1;

    public Brawler() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.COMMON, AbstractCard.CardTarget.SELF, CardTagsEnum.MONK);

        this.block = this.baseBlock = BLOCK_AMOUNT;
        this.magicNumber = this.baseMagicNumber = CARDS_TO_DRAW;
        this.tags.add(CardTagsEnum.MONK);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        boolean canDraw = true;
        for (AbstractCard c : p.hand.group) {
            if (c != this && c.type == CardType.SKILL) {
                canDraw = false;
                break;
            }
        }
        if(canDraw) { addToBot(new DrawCardAction(p, magicNumber)); }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK_AMOUNT);
            upgradeMagicNumber(UPGRADE_PLUS_CARDS_TO_DRAW);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
