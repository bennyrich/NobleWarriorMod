package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;

import static NobleWarriorMod.NobleWarriorMod.Utils.isAttacking;

public class CastleOfStone extends AbstractClassCard {
    private static final String ID = "NobleWarrior:CastleOfStone";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 3;

    public CastleOfStone() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.RARE, AbstractCard.CardTarget.SELF, CardTagsEnum.KNIGHT);

        this.isEthereal = true;
        this.exhaust = true;
        this.tags.add(CardTagsEnum.KNIGHT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int tmp = 0;
        for(AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if(!mo.isDead && isAttacking(mo)) { tmp++; }
        }
        if(tmp > 0) {
            addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, tmp), tmp));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.isEthereal = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
