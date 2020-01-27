package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import NobleWarriorMod.powers.WaterWalkingPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class WalkOnWater extends AbstractClassCard {
    private static final String ID = "NobleWarrior:WalkOnWater";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int COST_DOWN = 1;
    private static final int TURNS = 1;
    private static final int UPGRADE_PLUS_TURNS = 1;
    private int turns;

    public WalkOnWater() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.RARE, AbstractCard.CardTarget.SELF, CardTagsEnum.NINJA);

        magicNumber = baseMagicNumber = COST_DOWN;
        turns = TURNS;
        this.tags.add(CardTagsEnum.NINJA);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new WaterWalkingPower(p, magicNumber, turns), turns));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            turns += UPGRADE_PLUS_TURNS;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
