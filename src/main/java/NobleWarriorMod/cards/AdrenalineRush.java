package NobleWarriorMod.cards;

import NobleWarriorMod.enums.CardTagsEnum;
import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.powers.AdrenalineRushPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AdrenalineRush extends AbstractClassCard {
    private static final String ID = "NobleWarrior:AdrenalineRush";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int EOT_BLOCK = 6;
    private static final int UPGRADE_PLUS_EOT_BLOCK = 2;

    public AdrenalineRush() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.POWER,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, CardTagsEnum.ARCHER);

        magicNumber = baseMagicNumber = EOT_BLOCK;
        this.tags.add(CardTagsEnum.ARCHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                (AbstractPower)new AdrenalineRushPower(p, 1, magicNumber), 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_EOT_BLOCK);
        }
    }
}
