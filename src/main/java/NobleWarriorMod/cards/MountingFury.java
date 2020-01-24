package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import NobleWarriorMod.powers.MountingFuryPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MountingFury extends AbstractClassCard {
    private static final String ID = "NobleWarrior:MountingFury";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int STARTING_DAMAGE = 4;
    private static final int PLUS_DAMAGE = 2;
    private static final int UPGRADE_PLUS_DAMAGE = 2;

    public MountingFury() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.POWER,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.UNCOMMON, CardTarget.SELF, CardTagsEnum.LANCER);

        magicNumber = baseMagicNumber = PLUS_DAMAGE;

        this.tags.add(CardTagsEnum.LANCER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hasPower(MountingFuryPower.POWER_ID)) {
            MountingFuryPower mountingFuryPower = (MountingFuryPower)p.getPower(MountingFuryPower.POWER_ID);
            mountingFuryPower.stackNew(STARTING_DAMAGE, magicNumber);
        } else {
            addToBot(new ApplyPowerAction(p, p, new MountingFuryPower(p, STARTING_DAMAGE, magicNumber), 0));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DAMAGE);
        }
    }
}
