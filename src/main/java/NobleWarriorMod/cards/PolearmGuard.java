package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PolearmGuard extends AbstractClassCard {
    private static final String ID = "NobleWarrior:PolearmGuard";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMT = 8;
    private static final int UPGRADE_PLUS_BLOCK_AMT = 3;
    private static final int DRAW_AMT = 1;
    private boolean canDraw;

    public PolearmGuard() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, AbstractCard.CardRarity.UNCOMMON, CardTarget.SELF, CardTagsEnum.LANCER);

        block = baseBlock = BLOCK_AMT;
        this.baseMagicNumber = this.magicNumber = DRAW_AMT;

        this.tags.add(CardTagsEnum.LANCER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        if(canDraw) { addToBot(new DrawCardAction(p, magicNumber)); }
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);

        if(c != this) { canDraw = true; }
    }

    public void atTurnStart() {
        super.atTurnStart();

        canDraw = false;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK_AMT);
        }
    }
}