package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import NobleWarriorMod.enums.CardTagsEnum;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class Ambidexterity extends AbstractClassCard {
    private static final String ID = "NobleWarrior:Ambidexterity";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;

    public Ambidexterity() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.COMMON, AbstractCard.CardTarget.SELF, CardTagsEnum.NINJA);

        previews = new ArrayList<>();
        previews.add(new ThrowingKnife());
        previews.add(new ThrowingShuriken());
        cardsToPreview = previews.get(0);
        this.tags.add(CardTagsEnum.NINJA);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c1 = new ThrowingKnife();
        AbstractCard c2 = new ThrowingShuriken();
        if(upgraded) { c1.upgrade(); c2.upgrade(); }
        addToBot(new MakeTempCardInHandAction(c1, 1));
        addToBot(new MakeTempCardInHandAction(c2, 1));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            AbstractCard p1 = new ThrowingKnife();
            AbstractCard p2 = new ThrowingShuriken();
            p1.upgrade();
            p2.upgrade();
            previews = new ArrayList<>();
            previews.add(p1);
            previews.add(p2);
            cardsToPreview = previews.get(0);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
