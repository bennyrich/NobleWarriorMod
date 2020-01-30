package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.curses.Doubt;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import java.util.ArrayList;

public class DrawOutMuramasa extends AbstractClassCard {
    private static final String ID = "NobleWarrior:DrawOutMuramasa";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK = 6;
    private static final int UPGRADE_PLUS_ATTACK = 3;
    private static final int STRDOWN = 1;
    private static final int UPGRADE_PLUS_STRDOWN = 1;
    private static final AbstractCard PREV2 = new Muramasa();
    private static AbstractCard PREV2U = new Muramasa();
    private static final AbstractCard PREV1 = new Doubt();

    public DrawOutMuramasa() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.UNCOMMON, CardTarget.ENEMY, CardTagsEnum.SAMURAI);

        damage = baseDamage = ATTACK;
        magicNumber = baseMagicNumber = STRDOWN;
        previews.add(PREV1);
        previews.add(PREV2);
        cardsToPreview = previews.get(0);
        exhaust = true;
        tags.add(CardTagsEnum.SAMURAI);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new ApplyPowerAction(m, p, new StrengthPower(m, -magicNumber), -magicNumber));
        if(!m.hasPower("Artifact")) {
            addToBot(new ApplyPowerAction(m, p, new GainStrengthPower(m, magicNumber), magicNumber));
        }
        addToBot(new MakeTempCardInDrawPileAction(new Doubt(), 1, true, true));
        AbstractCard c1 = new Muramasa();
        if(upgraded) { c1.upgrade(); }
        addToBot(new MakeTempCardInDrawPileAction(c1, 1, true, true));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_ATTACK);
            upgradeMagicNumber(UPGRADE_PLUS_STRDOWN);
            PREV2U.upgrade();
            previews = new ArrayList<>();
            previews.add(PREV1);
            previews.add(PREV2U);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
