package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.powers.WeaponGuardPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;

public class WeaponGuard extends AbstractClassCard {
    private static final String ID = "NobleWarrior:WeaponGuard";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMOUNT = 8;
    private static final int UPGRADE_PLUS_BLOCK_AMOUNT = 3;
    private static final int THORNS = 2;

    public WeaponGuard() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, CardTagsEnum.KNIGHT);

        this.block = this.baseBlock = BLOCK_AMOUNT;
        this.magicNumber = baseMagicNumber = THORNS;
        this.tags.add(CardTagsEnum.KNIGHT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new GainBlockAction((AbstractCreature)p, (AbstractCreature)p, this.block));
        addToBot(new ApplyPowerAction(p, p, new WeaponGuardPower(p, magicNumber), magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK_AMOUNT);
        }
    }
}
