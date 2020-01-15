package NobleWarriorMod.cards;

import NobleWarriorMod.enums.CardTagsEnum;
import basemod.abstracts.CustomCard;
import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import NobleWarriorMod.actions.GainHeightAction;

public class GainHeight extends AbstractClassCard {
    private static final String ID = "NobleWarrior:GainHeight";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = -1;
    private static final int STRENGTH_MULTI = 2;
    private static final int UPGRADE_PLUS_STRENGTH_MULTI = 1;

    public GainHeight() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST,
                DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.RARE, AbstractCard.CardTarget.SELF, CardTagsEnum.ARCHER);

        this.baseMagicNumber = this.magicNumber = STRENGTH_MULTI;
        this.exhaust = true;
        this.tags.add(CardTagsEnum.ARCHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new GainHeightAction(p, this.magicNumber, this.freeToPlayOnce, this.energyOnUse));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_STRENGTH_MULTI);
        }
    }
}
