package NobleWarriorMod.cards;

import NobleWarriorMod.enums.CardTagsEnum;
import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.FastDrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FanDance extends AbstractClassCard {
    private static final String ID = "NobleWarrior:FanDance";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 4;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 2;
    private static final int NUM_HITS = 2;
    private boolean tookUnblockedDamage;
    private int expectedUnblockedDamage;
    private int realCurrentBlock;
    private int realCurrentHealth;
    private DamageInfo outgoingDamage;

    public FanDance() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST,
                DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, CardTagsEnum.DANCER);

        this.baseDamage = this.damage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = NUM_HITS;
        this.tookUnblockedDamage = false;
        expectedUnblockedDamage = 0;
        realCurrentBlock = 0;
        realCurrentHealth = 0;
        this.tags.add(CardTagsEnum.DANCER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        realCurrentBlock = m.currentBlock;
        realCurrentHealth = m.currentHealth;
        for(int i=0; i < this.magicNumber; i++) {
            outgoingDamage = new DamageInfo((AbstractCreature) p, this.damage, this.damageTypeForTurn);
            expectedUnblockedDamage = outgoingDamage.output - realCurrentBlock;
            realCurrentBlock -= outgoingDamage.output;
            tookUnblockedDamage = (expectedUnblockedDamage > 0 && realCurrentHealth > 0);
            realCurrentHealth -= expectedUnblockedDamage;
            expectedUnblockedDamage = 0;
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new DamageAction((AbstractCreature) m,
                    outgoingDamage, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            if(tookUnblockedDamage) { AbstractDungeon.actionManager.addToTop((AbstractGameAction)new FastDrawCardAction(p, 1)); }
        }
        realCurrentBlock = 0;
        realCurrentHealth = 0;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
        }
    }
}
