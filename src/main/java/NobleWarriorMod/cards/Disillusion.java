package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.actions.DisillusionAction;
import NobleWarriorMod.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;

public class Disillusion extends AbstractClassCard {
    private static final String ID = "NobleWarrior:Disillusion";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int BLOCK_AMOUNT = 13;
    private static final int UPGRADE_PLUS_BLOCK_AMOUNT = 4;
    private static final int CARDS_TO_DRAW = 2;
    private static final int UPGRADE_PLUS_CARDS_TO_DRAW = 1;

    public Disillusion() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.COMMON, AbstractCard.CardTarget.SELF, CardTagsEnum.DANCER);

        this.block = this.baseBlock = BLOCK_AMOUNT;
        this.magicNumber = this.baseMagicNumber = CARDS_TO_DRAW;
        this.tags.add(CardTagsEnum.DANCER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new GainBlockAction((AbstractCreature)p,
                (AbstractCreature)p, this.block));
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DisillusionAction(p, this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK_AMOUNT);
            upgradeMagicNumber(UPGRADE_PLUS_CARDS_TO_DRAW);
        }
    }
}
