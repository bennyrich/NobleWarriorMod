package NobleWarriorMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ArmUpHelperAction extends AbstractGameAction {
    public ArmUpHelperAction(AbstractPlayer p) {
        this.player = p;
        this.duration = DURATION;
        this.actionType = ActionType.CARD_MANIPULATION;
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        createCopyHeader = TEXT[1];
    }
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("NobleWarrior:ArmUpAction");
    private static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer player;
    private String createCopyHeader;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;

    private void handleCopy() {
        if (this.duration == DURATION && AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(player.discardPile, 1, createCopyHeader, false, false, false, false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                addToTop(new MakeTempCardInDrawPileAction(c, 1, true, true));
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
        }
    }

    public void update() {
        if(this.player.discardPile.size() == 0) {
            this.isDone = true;
            return;
        }
        handleCopy();
        tickDuration();
    }
}
