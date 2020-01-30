package NobleWarriorMod.patches;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.cards.AbstractClassCard;
import NobleWarriorMod.powers.WaterWalkingPower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AddToHandPatch {
    @SpirePatch(clz = CardGroup.class, method = "addToHand")
    public static class AddToHandPatch_Update
    {
        // Patch CardGroup.addToHand to reduce cost if Water Walking is active
        @SpirePrefixPatch
        public static void Insert(CardGroup __instance, AbstractCard c) {
            if(AbstractDungeon.player.hasPower(WaterWalkingPower.POWER_ID) && !WaterWalkingPower.modifiedCards.contains(c)) {
                c.modifyCostForCombat(-WaterWalkingPower.cost);
                WaterWalkingPower.modifiedCards.add(c);
            }
        }
    }
}
// WaterWalkingPower maintains a static AbstractCard ArrayList
// Set it empty when the constructor is called
// Add a card to it when the cost is modified. modify the cost for combat. when power is removed, go over the arraylist
// and re-modify the cost and then empty out the arraylist.
