package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class SwiftShot extends AbstractClassCard {
    private static final String ID = "NobleWarrior:SwiftShot";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 9;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 3;
    private static final int ENERGY = 1;

    public SwiftShot() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.NOBLEWARRIOR_ORANGE,
                AbstractCard.CardRarity.COMMON, CardTarget.ENEMY, CardTagsEnum.ARCHER);

        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = ENERGY;

        this.tags.add(CardTagsEnum.ARCHER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        if(NobleWarriorMod.Utils.isAttacking(m)) {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new GainEnergyAction(magicNumber));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
        }
    }
}
