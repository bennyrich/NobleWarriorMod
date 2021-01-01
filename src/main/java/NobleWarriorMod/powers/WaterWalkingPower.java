package NobleWarriorMod.powers;

import NobleWarriorMod.NobleWarriorMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;

public class WaterWalkingPower extends AbstractPower {
    public static final String POWER_ID = "NobleWarrior:WaterWalking";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public static int cost;
    private AbstractPlayer playerOwner;
    public static ArrayList<AbstractCard> modifiedCards = new ArrayList<>();

    public WaterWalkingPower(AbstractCreature owner, int turns) { this(owner, 1, turns); }

    public WaterWalkingPower(AbstractCreature owner, int cost, int turns) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = PowerType.BUFF;
        playerOwner = (owner instanceof AbstractPlayer) ? (AbstractPlayer)owner : null;
        this.owner = owner;
        WaterWalkingPower.cost = cost;
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
            c.modifyCostForCombat(-cost);
            if(!AbstractDungeon.player.hasPower("Confusion")) {
                WaterWalkingPower.modifiedCards.add(c);
                //NobleWarriorMod.logger.info("Adding to modifiedCards list:" + c.name + ", ID:" + c.cardID);
            }
        }
        //NobleWarriorMod.logger.info("Current modified cards array size at end of onInitialApplication: " + WaterWalkingPower.modifiedCards.size());
    }

    public void onCardDraw(AbstractCard c) {
        //NobleWarriorMod.logger.info("Entered onCardDraw for WaterWalkingPower");
        //NobleWarriorMod.logger.info("Current modified cards array size at start of onCardDraw: " + WaterWalkingPower.modifiedCards.size());
        if(!WaterWalkingPower.modifiedCards.contains(c) && AbstractDungeon.player.hasPower("Confusion")) {
            //NobleWarriorMod.logger.info("About to Water Walk cost for card " + c.name + ", ID:" + c.cardID + ", current cost " + c.costForTurn);
            c.modifyCostForCombat(-cost);
            if(!AbstractDungeon.player.hasPower("Confusion")) { WaterWalkingPower.modifiedCards.add(c); }
            //NobleWarriorMod.logger.info("Post Water Walk cost for card " + c.name + ", ID:" + c.cardID + ": " + c.costForTurn);
        } else {
            //NobleWarriorMod.logger.info("Index of card " + c.name + " in modified cards is " + WaterWalkingPower.modifiedCards.indexOf(c));
        }
        //NobleWarriorMod.logger.info("Current modified cards array size at end of onCardDraw: " + WaterWalkingPower.modifiedCards.size());
    }

    public void onVictory() {
        WaterWalkingPower.modifiedCards = new ArrayList<>();
    }

    public void atEndOfTurn(boolean isPlayer) {
        if(!isPlayer) { return; }
        NobleWarriorMod.logger.info("Entered end of player turn. current amount is: " + amount);
        if (this.amount == 0) {
            // this NEVER gets called EVER??
            NobleWarriorMod.logger.info("Removing WW Power");
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
            for(AbstractCard c : WaterWalkingPower.modifiedCards) {
                NobleWarriorMod.logger.info("Found modified card: " + c.name);
                if(c.isCostModified) {
                    NobleWarriorMod.logger.info("Un-modifying cost: " + c.name);
                    c.modifyCostForCombat(cost);
                }
            }
            WaterWalkingPower.modifiedCards = new ArrayList<>();
            NobleWarriorMod.logger.info("Size of modifiedCards after removing power: " + modifiedCards.size());
        } else {
            NobleWarriorMod.logger.info("Entered ReducePower branch. current amount is: " + amount);
            if(amount == 1) {
                for(AbstractCard c : modifiedCards) {
                    NobleWarriorMod.logger.info("Found modified card: " + c.name);
                    if(c.isCostModified) {
                        NobleWarriorMod.logger.info("Un-modifying cost: " + c.name);
                        c.modifyCostForCombat(cost); // set cost for combat back to c.cost??
                    }
                }
                WaterWalkingPower.modifiedCards = new ArrayList<>();
            }
            addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
            NobleWarriorMod.logger.info("Leaving ReducePower branch. current amount is: " + amount);
        }
    }
}
