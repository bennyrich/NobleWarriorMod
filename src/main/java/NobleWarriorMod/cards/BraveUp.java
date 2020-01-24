package NobleWarriorMod.cards;

import NobleWarriorMod.enums.CardTagsEnum;
import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

public class BraveUp extends AbstractClassCard {
    private static final String ID = "NobleWarrior:BraveUp";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int VULNERABLE = 2;
    private static final int UPGRADE_PLUS_VULNERABLE = -1;

    public BraveUp() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, CardTagsEnum.DANCER);
        magicNumber = baseMagicNumber = VULNERABLE;

        this.tags.add(CardTagsEnum.DANCER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, 3), 3));
        addToBot(new ApplyPowerAction(p, p, new VulnerablePower(p, magicNumber, false), magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_VULNERABLE);
        }
    }
}
