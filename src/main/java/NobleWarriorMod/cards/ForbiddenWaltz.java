package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import NobleWarriorMod.powers.BlindPower;
import NobleWarriorMod.powers.ImmobilizePower;
import NobleWarriorMod.powers.SilencePower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.SlowPower;

public class ForbiddenWaltz extends AbstractClassCard {
    private static final String ID = "NobleWarrior:ForbiddenWaltz";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int DEBUFF_AMOUNT = 1;
    private static final int UPGRADE_PLUS_DEBUFF_AMOUNT = 1;
    private static final int POISON_AMOUNT = 5;

    public ForbiddenWaltz() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.RARE, CardTarget.ALL_ENEMY, CardTagsEnum.DANCER);

        magicNumber = baseMagicNumber = DEBUFF_AMOUNT;
        exhaust = true;
        this.tags.add(CardTagsEnum.DANCER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            addToBot(new ApplyPowerAction(mo, p, new ImmobilizePower(mo, magicNumber, false), magicNumber));
            addToBot(new ApplyPowerAction(mo, p, new SilencePower(mo, magicNumber, false), magicNumber));
            addToBot(new ApplyPowerAction(mo, p, new BlindPower(mo, magicNumber, false), magicNumber));
            addToBot(new ApplyPowerAction(mo, p, new SlowPower(mo, magicNumber), magicNumber));
            addToBot(new ApplyPowerAction(mo, p, new PoisonPower(mo, p, POISON_AMOUNT), POISON_AMOUNT));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DEBUFF_AMOUNT);
        }
    }
}
