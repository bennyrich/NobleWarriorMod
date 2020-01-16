package NobleWarriorMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class SiftAction extends AbstractGameAction {
    public SiftAction(AbstractPlayer p, int cardsToDiscard, int cardsToUpgrade) {
        this.player = p;
        this.cardsToDiscard = cardsToDiscard;
        this.cardsToUpgrade = cardsToUpgrade;
        this.wereCardsDiscarded = false;
        this.duration = this.DURATION;
        this.actionType = AbstractGameAction.ActionType.DISCARD;
        if(cardsToDiscard == 1) {
            discardHeaderText = TEXT[0] + cardsToDiscard + TEXT[1];
        } else {
            discardHeaderText = TEXT[0] + cardsToDiscard + TEXT[2];
        }
    }
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("NobleWarrior:SiftAction");
    private static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer player;
    private int cardsToDiscard;
    private int cardsToUpgrade;
    private String discardHeaderText;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private boolean wereCardsDiscarded;

    private void handleDiscard() {
        if (this.duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }
            //TODO: find out if we need this
            //numDiscarded = this.amount;
            AbstractDungeon.handCardSelectScreen.open(discardHeaderText, this.cardsToDiscard, true, true);
            AbstractDungeon.player.hand.applyPowers();
            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            DiscardAction.numDiscarded = AbstractDungeon.handCardSelectScreen.selectedCards.group.size();
            if(DiscardAction.numDiscarded > 0) { this.wereCardsDiscarded = true; }
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                this.player.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                GameActionManager.incrementDiscard(false);
            }
            if(wereCardsDiscarded) {
                AbstractDungeon.actionManager.addToTop((AbstractGameAction)new UpgradeAction((AbstractPlayer)this.player, this.cardsToUpgrade));
                wereCardsDiscarded = false;
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }

    public void update() {
        if(this.player.hand.size() == 0) {
            this.isDone = true;
            return;
        }
        if(this.cardsToDiscard > 0) {
            handleDiscard();
        }
        this.player.hand.glowCheck();
    }
}
