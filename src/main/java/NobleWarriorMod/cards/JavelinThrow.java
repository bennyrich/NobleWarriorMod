package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class JavelinThrow extends AbstractClassCard {
    private static final String ID = "NobleWarrior:JavelinThrow";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int DAMAGE = 3;
    private static final int UPGRADE_PLUS_DAMAGE = 1;
    private static final AbstractCard PREV1 = new ThrowingSpear();

    public JavelinThrow() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.COMMON, CardTarget.ENEMY, CardTagsEnum.NINJA);

        damage = baseDamage = DAMAGE;
        previews.add(PREV1);
        cardsToPreview = previews.get(0);
        this.tags.add(CardTagsEnum.NINJA);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c1 = new ThrowingSpear();
        if(upgraded) { c1.upgrade(); }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new MakeTempCardInHandAction(c1, 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            AbstractCard p1 = new ThrowingSpear();
            p1.upgrade();
            previews = new ArrayList<>();
            previews.add(p1);
            cardsToPreview = previews.get(0);
        }
    }
}
