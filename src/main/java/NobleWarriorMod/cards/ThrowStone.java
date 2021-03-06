package NobleWarriorMod.cards;

import NobleWarriorMod.actions.ThrowStoneAction;
import basemod.abstracts.CustomCard;
import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import NobleWarriorMod.enums.CardTagsEnum;

public class ThrowStone extends AbstractClassCard {
    private static final String ID = "NobleWarrior:ThrowStone";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int ATTACK_DMG = 6;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 2;

    public ThrowStone() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST,
                DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.NOBLEWARRIOR_ORANGE, AbstractCard.CardRarity.COMMON, CardTarget.ALL_ENEMY, CardTagsEnum.SQUIRE);

        this.baseDamage = this.damage = ATTACK_DMG;

        this.tags.add(CardTagsEnum.SQUIRE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ThrowStoneAction(p, this.damage, this.damageTypeForTurn));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
        }
    }
}
