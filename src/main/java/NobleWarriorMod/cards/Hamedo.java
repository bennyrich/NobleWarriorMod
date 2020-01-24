package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;

public class Hamedo extends AbstractClassCard {
    private static final String ID = "NobleWarrior:Hamedo";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int ATTACK_AMT = 15;
    private static final int UPGRADE_PLUS_ATTACK_AMT = 3;

    public Hamedo() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.RARE, CardTarget.ALL_ENEMY, CardTagsEnum.MONK);

        damage = baseDamage = ATTACK_AMT;
        this.exhaust = true;
        this.tags.add(CardTagsEnum.MONK);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for(AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if(!mo.isDead && NobleWarriorMod.Utils.isAttacking(mo)) {
                addToBot(new DamageAction(mo, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
        }
        addToBot(new ApplyPowerAction(p, p, new BufferPower(p, 1), 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_ATTACK_AMT);
        }
    }
}
