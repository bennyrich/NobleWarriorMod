package NobleWarriorMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import NobleWarriorMod.NobleWarriorMod;

public class UpgradeAction extends AbstractGameAction {
    public UpgradeAction(AbstractPlayer p, int numCards) {
        this.cardsToUpgrade = numCards;
        this.player = p;
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = this.DURATION;
        upgradeHeaderText = TEXT[0];
    }
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ArmamentsAction");
    private static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer player;
    private int cardsToUpgrade;
    private String upgradeHeaderText;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;

    private void handleUpgrade() {
        if (this.duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }
            if (this.player.hand.size() == 0) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(upgradeHeaderText, this.cardsToUpgrade, true, true, false, true);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                if (c.canUpgrade()) {
                    c.upgrade();
                    c.initializeDescription();
                    c.superFlash();
                }
                this.player.hand.addToTop(c);
            }
            this.player.hand.refreshHandLayout();
            this.player.hand.glowCheck();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        tickDuration();
    }

    public void update() {
        if(this.player.hand.size() == 0) {
            this.isDone = true;
            return;
        }
        if(this.cardsToUpgrade > 0) {
            handleUpgrade();
        }
    }
}
