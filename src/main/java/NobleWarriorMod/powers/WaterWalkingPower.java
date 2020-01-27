package NobleWarriorMod.powers;

import NobleWarriorMod.NobleWarriorMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class WaterWalkingPower extends AbstractPower {
    private static final String POWER_ID = "NobleWarrior:WaterWalking";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int cost;
    private AbstractPlayer playerOwner;

    public WaterWalkingPower(AbstractCreature owner, int turns) { this(owner, 1, turns); }

    public WaterWalkingPower(AbstractCreature owner, int cost, int turns) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = PowerType.BUFF;
        playerOwner = (owner instanceof AbstractPlayer) ? (AbstractPlayer)owner : null;
        this.owner = owner;
        this.cost = cost;
        this.amount = turns;
        updateDescription();
        this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID));
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        updateDescription();
    }

    public void updateDescription() {
        if(amount == 1) {
            this.description = DESCRIPTIONS[0] + cost + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + cost + DESCRIPTIONS[1] + amount + DESCRIPTIONS[3];
        }
    }

    public void onInitialApplication() {
        if(playerOwner == null) { return; }
        for(AbstractCard c : playerOwner.hand.group) {
            if(c.costForTurn - cost >= 0) { c.setCostForTurn(c.costForTurn - cost); }
        }
    }

    public void onCardDraw(AbstractCard c) {
        if(c.costForTurn - cost >= 0) { c.setCostForTurn(c.costForTurn - cost); }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if(!isPlayer) { return; }
        if (this.amount == 0) {
            addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            addToBot((AbstractGameAction)new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }
}
