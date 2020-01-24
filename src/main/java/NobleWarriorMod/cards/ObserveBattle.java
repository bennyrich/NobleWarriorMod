package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class ObserveBattle extends AbstractClassCard {
    private static final String ID = "NobleWarrior:ObserveBattle";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int VULN_AND_STR = 2;
    private static final int UPGRADE_PLUS_VULN_AND_STR = 1;

    public ObserveBattle() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ALL_ENEMY, CardTagsEnum.LANCER);

        this.baseMagicNumber = this.magicNumber = VULN_AND_STR;

        this.tags.add(CardTagsEnum.LANCER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if(!mo.isDead) {
                addToBot(new ApplyPowerAction(mo, p, new VulnerablePower(mo, magicNumber, false), magicNumber));
            }
        }
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        addToBot(new ApplyPowerAction(p, p, new LoseStrengthPower(p, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_VULN_AND_STR);
        }
    }
}