package NobleWarriorMod.cards;

import NobleWarriorMod.enums.CardTagsEnum;
import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class RepeatingFist extends AbstractClassCard {
    private static final String ID = "NobleWarrior:RepeatingFist";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int NUM_HITS = 2;
    private static final int PLUS_HITS = 1;
    private static final int UPGRADE_PLUS_HITS = 2;

    public RepeatingFist() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.COMMON, CardTarget.ENEMY, CardTagsEnum.MONK);

        this.damage = this.baseDamage = DAMAGE;
        this.baseMagicNumber = this.magicNumber = NUM_HITS;
        this.tags.add(CardTagsEnum.MONK);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i=0; i < magicNumber; i++) {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        }
        magicNumber += upgraded ? UPGRADE_PLUS_HITS : PLUS_HITS;
        baseMagicNumber += upgraded ? UPGRADE_PLUS_HITS : PLUS_HITS;
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}