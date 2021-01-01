package NobleWarriorMod.patches;

import NobleWarriorMod.powers.WaterWalkingPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AddToHandPatch {
    @SpirePatch(clz = CardGroup.class, method = "addToHand")
    public static class AddToHandPatch_Update
    {
        // Patch CardGroup.addToHand to reduce cost if Water Walking is active
        @SpirePrefixPatch
        public static void Insert(CardGroup __instance, AbstractCard c) {
            //NobleWarriorMod.logger.info("Entered addToHand");
            //NobleWarriorMod.logger.info("Current modified cards array size at start of addToHand: " + WaterWalkingPower.modifiedCards.size());
            if(AbstractDungeon.player.hasPower(WaterWalkingPower.POWER_ID) && !WaterWalkingPower.modifiedCards.contains(c)) {
                //NobleWarriorMod.logger.info("About to Water Walk cost for card " + c.name + ", ID:" + c.cardID + ", current cost " + c.costForTurn);
                c.modifyCostForCombat(-WaterWalkingPower.cost);
                //NobleWarriorMod.logger.info("Post Water Walk cost for card " + c.name + ", ID:" + c.cardID + ": " + c.costForTurn);
                if(!AbstractDungeon.player.hasPower("Confusion")) { WaterWalkingPower.modifiedCards.add(c); }
            }
            //NobleWarriorMod.logger.info("Current modified cards array size at end of addToHand: " + WaterWalkingPower.modifiedCards.size());
        }
    }
}
// WaterWalkingPower maintains a static AbstractCard ArrayList
// Set it empty when the constructor is called
// Add a card to it when the cost is modified. modify the cost for combat. when power is removed, go over the arraylist
// and re-modify the cost and then empty out the arraylist.

// add a flag to waterwalkingpower to tell if we've done the card draw already or not. only modify here if we've already done the card draw?
