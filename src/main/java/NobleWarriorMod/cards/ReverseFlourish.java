package NobleWarriorMod.cards;

import NobleWarriorMod.enums.CardTagsEnum;
import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class ReverseFlourish extends AbstractClassCard {
    private static final String ID = "NobleWarrior:ReverseFlourish";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 8;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 3;
    private static final int INCREASE_PER_ATTACK = 2;
    private static final int UPGRADE_PLUS_INCREASE_PER_ATTACK = 2;
    private int actualStartingDamage;

    public ReverseFlourish() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.NOBLEWARRIOR_ORANGE,
                CardRarity.UNCOMMON, CardTarget.ENEMY, CardTagsEnum.DANCER);

        this.damage = this.baseDamage = this.actualStartingDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = INCREASE_PER_ATTACK;
        this.tags.add(CardTagsEnum.DANCER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)m,
                new DamageInfo((AbstractCreature)p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    }

    public void triggerOnOtherCardPlayed(AbstractCard c) {
        super.triggerOnOtherCardPlayed(c);

        if(c.type == CardType.ATTACK) {
            baseDamage += baseMagicNumber;
            this.initializeDescription();
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
            this.upgradeMagicNumber(UPGRADE_PLUS_INCREASE_PER_ATTACK);
        }
    }
}
