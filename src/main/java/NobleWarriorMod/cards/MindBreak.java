package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.AbstractCardEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import NobleWarriorMod.enums.CardTagsEnum;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import static NobleWarriorMod.NobleWarriorMod.Utils.isDebuffing;

public class MindBreak extends AbstractClassCard {
    private static final String ID = "NobleWarrior:MindBreak";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = cardStrings.NAME;
    private static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int BLOCK_AMOUNT = 12;
    private static final int UPGRADE_PLUS_BLOCK_AMOUNT = 4;

    public MindBreak() {
        super(ID, NAME, NobleWarriorMod.getCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
                AbstractCardEnum.NOBLEWARRIOR_ORANGE, CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, CardTagsEnum.KNIGHT);

        this.block = this.baseBlock = BLOCK_AMOUNT;
        this.tags.add(CardTagsEnum.KNIGHT);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot((AbstractGameAction)new GainBlockAction((AbstractCreature)p, (AbstractCreature)p, this.block));
        boolean getArtifact = false;
        for(AbstractMonster mo : AbstractDungeon.getMonsters().monsters) {
            if(!mo.isDead && isDebuffing(mo)) {
                getArtifact = true;
            }
        }
        if(getArtifact) { addToBot(new ApplyPowerAction(p, p, new ArtifactPower(p, 1), 1)); }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK_AMOUNT);
        }
    }
}
