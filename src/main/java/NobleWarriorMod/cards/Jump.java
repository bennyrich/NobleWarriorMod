package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import NobleWarriorMod.powers.JumpPower;

public class Jump extends AbstractClassCard {
    public static final String ID = "NobleWarrior:Jump";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 12;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 4;

    public Jump() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.COMMON, CardTarget.ENEMY, CardTagsEnum.LANCER);

        this.baseDamage = this.damage = ATTACK_DMG;
        this.tags.add(CardTagsEnum.LANCER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(p.hasPower(JumpPower.POWER_ID)) {
            JumpPower jumpPower = (JumpPower)p.getPower(JumpPower.POWER_ID);
            jumpPower.stackNewTarget(ATTACK_DMG, m);
        } else {
            addToBot(new ApplyPowerAction(p, p, new JumpPower(p, ATTACK_DMG, false, m), 0));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
        }
    }
}
