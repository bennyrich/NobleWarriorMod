package NobleWarriorMod.cards;

import NobleWarriorMod.enums.CardTagsEnum;
import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class SeekCover extends AbstractClassCard {
    private static final String ID = "NobleWarrior:SeekCover";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMOUNT = 4;
    private static final int UPGRADE_PLUS_BLOCK_AMOUNT = 2;
    private static final int DEXTERITY = 1;
    private static final int DEX_LIMIT = 3;
    private static final int UPGRADE_PLUS_DEX_LIMIT = 1;

    public SeekCover() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, CardTagsEnum.ARCHER);

        this.block = this.baseBlock = BLOCK_AMOUNT;
        this.baseMagicNumber = this.magicNumber = DEX_LIMIT;

        this.tags.add(CardTagsEnum.ARCHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.exhaust = false;
        for(AbstractPower pow : p.powers) {
            if( pow instanceof DexterityPower && pow.amount >= (magicNumber - 1) ) {
                this.exhaust = true;
            }
        }
        addToBot(new GainBlockAction(p, p, this.block));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, DEXTERITY), DEXTERITY));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK_AMOUNT);
            upgradeMagicNumber(UPGRADE_PLUS_DEX_LIMIT);
        }
    }
}
