package NobleWarriorMod.cards;

import NobleWarriorMod.enums.CardTagsEnum;
import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.SlowPower;

public class SlowDance extends AbstractClassCard {
    private static final String ID = "NobleWarrior:SlowDance";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int ATTACK_DMG = 8;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 4;

    public SlowDance() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.NOBLEWARRIOR_ORANGE,
        AbstractCard.CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, CardTagsEnum.DANCER);

        this.baseDamage = this.damage = ATTACK_DMG;
        this.isMultiDamage = true;

        this.tags.add(CardTagsEnum.DANCER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAllEnemiesAction((AbstractCreature)p, this.multiDamage,
                this.damageTypeForTurn, AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)mo, (AbstractCreature)p,
                    (AbstractPower)new SlowPower((AbstractCreature)mo, 0), 0, true,
                    AbstractGameAction.AttackEffect.NONE));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
        }
    }
}
