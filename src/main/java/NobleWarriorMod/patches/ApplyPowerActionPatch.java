package NobleWarriorMod.patches;

import NobleWarriorMod.powers.SilencePower;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
import static basemod.ReflectionHacks.getPrivate;

public class ApplyPowerActionPatch {
    @SpirePatch(clz = ApplyPowerAction.class, method = "update")
    public static class ApplyPowerActionPatch_Update
    {
        // Patch ApplyPowerAction to prevent monsters with the Silence debuff from applying any DEBUFF powers to players
        @SpirePrefixPatch
        public static SpireReturn Insert(ApplyPowerAction __instance) {
            AbstractPower patchedPowerToApply = (AbstractPower)getPrivate(__instance, ApplyPowerAction.class, "powerToApply");
            if(__instance.source.hasPower(SilencePower.POWER_ID) && __instance.target.isPlayer && patchedPowerToApply.type == AbstractPower.PowerType.DEBUFF) {
                __instance.isDone = true;
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
