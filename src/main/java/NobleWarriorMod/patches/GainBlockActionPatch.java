package NobleWarriorMod.patches;

import NobleWarriorMod.powers.ImmobilizePower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;

public class GainBlockActionPatch {
    @SpirePatch(clz = GainBlockAction.class, method = "update")
    public static class GainBlockActionPatch_Update
    {
        // Patch GainBlockAction to prevent creatures with the Immobilize debuff from gaining Block
        @SpirePrefixPatch
        public static SpireReturn Insert(GainBlockAction __instance) {
            if(__instance.target.hasPower(ImmobilizePower.POWER_ID)) {
                __instance.isDone = true;
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
