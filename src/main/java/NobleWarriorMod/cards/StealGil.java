package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import NobleWarriorMod.powers.StolenPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

public class StealGil extends AbstractClassCard {
    private static final String ID = "NobleWarrior:StealGil";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 9;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 3;
    private static final int GOLD = 10;
    private static final int UPGRADE_PLUS_GOLD = 5;

    public StealGil() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.NOBLEWARRIOR_ORANGE,
                AbstractCard.CardRarity.COMMON, CardTarget.ENEMY, CardTagsEnum.THIEF);

        this.baseDamage = this.damage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = GOLD;

        this.tags.add(CardTagsEnum.THIEF);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if(!m.hasPower(StolenPower.POWER_ID)) {
            addToBot(new GainGoldAction(magicNumber));
            addToBot(new ApplyPowerAction(m, p, new StolenPower(m), 0));
            CardCrawlGame.sound.play("GOLD_JINGLE");
            for(int i=0; i<magicNumber; i++) {
                AbstractDungeon.effectList.add(new GainPennyEffect(m.hb.cX, m.hb.cY));
            }
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
            upgradeMagicNumber(UPGRADE_PLUS_GOLD);
        }
    }
}
