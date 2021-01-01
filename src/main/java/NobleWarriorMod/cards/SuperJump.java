package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import NobleWarriorMod.powers.JumpPower;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class SuperJump extends AbstractClassCard {
    public static final String ID = "NobleWarrior:SuperJump";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final int ATTACK_DMG = 32;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 6;

    public SuperJump() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST,
                DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.RARE, CardTarget.ALL_ENEMY, CardTagsEnum.LANCER);
        // was: ATTACK

        this.baseDamage = this.damage = ATTACK_DMG;
        this.isMultiDamage = true;
        this.tags.add(CardTagsEnum.LANCER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractClassCard tempC = new SuperJumpLanded();
        if (this.upgraded) { tempC.upgrade(); }
        if(p.hasPower(JumpPower.POWER_ID)) {
            JumpPower jumpPower = (JumpPower)p.getPower(JumpPower.POWER_ID);
            jumpPower.stackPower(tempC, null);
        } else {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new ApplyPowerAction((AbstractCreature) p, (AbstractCreature) p,
                    (AbstractPower) new JumpPower((AbstractCreature) p, null, tempC), ATTACK_DMG));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
        }
    }
}
