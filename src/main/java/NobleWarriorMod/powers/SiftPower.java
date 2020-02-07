package NobleWarriorMod.powers;

import NobleWarriorMod.actions.SiftAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import NobleWarriorMod.NobleWarriorMod;

public class SiftPower extends AbstractPower {
    private static final String POWER_ID = "NobleWarrior:Sift";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public SiftPower(AbstractCreature owner, int amount, int cardsToDiscard, int cardsToUpgrade) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.cardsToDiscard = cardsToDiscard;
        this.cardsToUpgrade = cardsToUpgrade;
        updateDescription();
        this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID));
    }
    private int cardsToDiscard;
    private int cardsToUpgrade;

    public void updateDescription() {
        if(cardsToDiscard == 1) {
            this.description = DESCRIPTIONS[0] + cardsToDiscard + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + cardsToDiscard + DESCRIPTIONS[3];
        }
        if(cardsToUpgrade == 1) {
            this.description = this.description + cardsToUpgrade + DESCRIPTIONS[2];
        }
        else {
            this.description = this.description + cardsToUpgrade + DESCRIPTIONS[4];
        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        updateDescription();
    }

    public void atStartOfTurnPostDraw() {
        flash();
        for(int i=0; i < amount; i++) {
            AbstractDungeon.actionManager.addToBottom(new SiftAction((AbstractPlayer)this.owner, this.cardsToDiscard, this.cardsToUpgrade));
        }
    }
}
