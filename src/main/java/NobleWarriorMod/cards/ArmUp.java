package NobleWarriorMod.cards;

import NobleWarriorMod.actions.ArmUpAction;
import NobleWarriorMod.enums.CardTagsEnum;
import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ArmUp extends AbstractClassCard {
    private static final String ID = "NobleWarrior:ArmUp";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private boolean baseRetain;

    public ArmUp() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.RARE, CardTarget.SELF, CardTagsEnum.KNIGHT);

        this.baseRetain = false;
        this.tags.add(CardTagsEnum.KNIGHT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ArmUpAction(p));
    }

    public void triggerOnEndOfTurnForPlayingCard() {
        super.triggerOnEndOfTurnForPlayingCard();
        retain = baseRetain;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            baseRetain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}