package NobleWarriorMod.cards;

import NobleWarriorMod.enums.CardTagsEnum;
import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.powers.AttackBoostPower;
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

public class AttackBoost extends AbstractClassCard {
    private static final String ID = "NobleWarrior:AttackBoost";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DAMAGE = 2;
    private boolean ACTIVE;

    public AttackBoost() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.POWER,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, CardTagsEnum.GEOMANCER);

        this.baseMagicNumber = this.magicNumber = DAMAGE;
        this.ACTIVE = true;
        this.tags.add(CardTagsEnum.GEOMANCER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        NobleWarriorMod.logger.info("Using Attack Boost card; active = " + ACTIVE);
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                (AbstractPower)new AttackBoostPower((AbstractCreature)p, this.magicNumber, ACTIVE), this.magicNumber));
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        super.onPlayCard(c, m);

        if(c.type == CardType.ATTACK) { ACTIVE = false; }
    }

    public void atTurnStart() {
        super.atTurnStart();

        ACTIVE = true;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_DAMAGE);
        }
    }
}
