package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class BombToss extends AbstractClassCard {
    private static final String ID = "NobleWarrior:BombToss";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DAMAGE = 1;
    private static final int BOMBS = 2;
    private static final AbstractCard PREV1 = new ThrowingBomb();

    public BombToss() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.COMMON, CardTarget.ALL_ENEMY, CardTagsEnum.NINJA);

        damage = baseDamage = DAMAGE;
        magicNumber = baseMagicNumber = BOMBS;
        isMultiDamage = true;
        previews.add(PREV1);
        cardsToPreview = previews.get(0);
        this.tags.add(CardTagsEnum.NINJA);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c1 = new ThrowingBomb();
        AbstractCard c2 = new ThrowingBomb();
        if(upgraded) { c1.upgrade(); c2.upgrade(); }
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        addToBot(new MakeTempCardInHandAction(c1, 1));
        addToBot(new MakeTempCardInHandAction(c2, 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            PREV1.upgrade();
            previews = new ArrayList<>();
            previews.add(PREV1);
        }
    }
}
