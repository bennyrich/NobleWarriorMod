package NobleWarriorMod.actions;

import NobleWarriorMod.NobleWarriorMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import java.util.ArrayList;

public class ArmUpAction extends AbstractGameAction {
    public ArmUpAction(AbstractPlayer p) {
        this.player = p;
        this.duration = DURATION;
        this.actionType = ActionType.CARD_MANIPULATION;
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        exhaustHeader = TEXT[0];
    }
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("NobleWarrior:ArmUpAction");
    private static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer player;
    private String exhaustHeader;
    private ArrayList<AbstractCard> tempCardList = new ArrayList<>();
    private static final float DURATION = Settings.ACTION_DUR_XFAST;

    private void handleExhaust() {
        if (this.duration == DURATION && AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(player.discardPile, 1, exhaustHeader, false, false, false, false);
            tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                tempCardList.add(c);
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            for(AbstractCard d : tempCardList) {
                addToTop(new ArmUpHelperAction(player));
                addToTop(new ExhaustSpecificCardAction(d, player.discardPile));
            }
            isDone = true;
        }
    }

    public void update() {
        if(this.player.discardPile.size() == 0) {
            this.isDone = true;
            return;
        }
        handleExhaust();
        tickDuration();
    }
}
