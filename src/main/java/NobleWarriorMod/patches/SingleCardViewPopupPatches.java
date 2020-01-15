package NobleWarriorMod.patches;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.screens.SingleCardViewPopup;
import NobleWarriorMod.cards.AbstractClassCard;

public class SingleCardViewPopupPatches {
//    private static final Field<AbstractCard> cardField = JavaUtilities.GetPrivateField("card", SingleCardViewPopup.class);

    @SpirePatch(clz = SingleCardViewPopup.class, method = "renderTitle")
    public static class SingleCardViewPopup_RenderTitle
    {
        @SpireInsertPatch(rloc = 0, localvars = {"card"})
        public static void Insert(SingleCardViewPopup __instance, SpriteBatch sb, AbstractCard card) {
            AbstractClassCard c = (card instanceof AbstractClassCard) ? (AbstractClassCard)card : null;

            if (c != null && !c.isFlipped)
            {
                c.renderInSingleCardPopup(sb, false);
            }
        }
    }
}
