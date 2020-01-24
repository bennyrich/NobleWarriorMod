package NobleWarriorMod.cards;

import NobleWarriorMod.powers.RetainOneTurnPower;
import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CalculatedStrike extends AbstractClassCard {
    private static final String ID = "NobleWarrior:CalculatedStrike";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 9;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 3;
    private static final int RETAIN_AMT = 1;

    public CalculatedStrike() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, CardTagsEnum.LANCER);

        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = RETAIN_AMT;

        this.tags.add(AbstractCard.CardTags.STRIKE);
        this.tags.add(CardTagsEnum.LANCER);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ApplyPowerAction(p, p, new RetainOneTurnPower(p, magicNumber), magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
        }
    }
}