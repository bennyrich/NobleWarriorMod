package NobleWarriorMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class WithKnivesAction extends AbstractGameAction {
    public WithKnivesAction(AbstractPlayer p, int numCards) {
        this.amount = numCards;
        this.player = p;
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_XFAST;
    }
    private AbstractPlayer player;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("NobleWarrior:WithKnivesAction");
    public static final String[] TEXT = uiStrings.TEXT;

    public void update() {
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
        } else {
            if (this.duration == Settings.ACTION_DUR_XFAST) {
                if (this.player.discardPile.isEmpty()) {
                    this.isDone = true;

                    return;
                }
                AbstractDungeon.gridSelectScreen.open(this.player.discardPile, this.amount, true, TEXT[0] + this.amount + TEXT[1]);
            } else if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                    this.player.discardPile.removeCard(c);
                    this.player.hand.moveToDeck(c, true);
                }
                AbstractDungeon.gridSelectScreen.selectedCards.clear();
            }

            tickDuration();
        }
    }
}
