package NobleWarriorMod.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.SoulGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DisillusionAction extends AbstractGameAction {
    public DisillusionAction(AbstractPlayer p, int cardsToDraw) {
        this.amount = cardsToDraw;
        this.player = p;
        this.actionType = ActionType.DRAW;
        this.duration = Settings.ACTION_DUR_XFAST;
    }
    private AbstractPlayer player;
    private boolean shuffleCheck = false;

    public void update() {
        int deckSize = AbstractDungeon.player.drawPile.size();
        int discardSize = AbstractDungeon.player.discardPile.size();

        if (SoulGroup.isActive()) {
            return;
        }

        if (deckSize + discardSize == 0) {
            this.isDone = true;
            return;
        }

        if (!this.shuffleCheck) {
            if (this.amount + AbstractDungeon.player.hand.size() > 10) {
                int handSizeAndDraw = 10 - this.amount + AbstractDungeon.player.hand.size();
                this.amount += handSizeAndDraw;
                AbstractDungeon.player.createHandIsFullDialog();
            }
            if (this.amount > deckSize) {
                int tmp = this.amount - deckSize;
                addToTop(new DisillusionAction((AbstractPlayer)AbstractDungeon.player, tmp));
                addToTop(new EmptyDeckShuffleAction());
                if (deckSize != 0) {
                    addToTop(new DisillusionAction((AbstractPlayer)AbstractDungeon.player, deckSize));
                }
                this.amount = 0;
                this.isDone = true;
            }
            this.shuffleCheck = true;
        }

        this.duration -= Gdx.graphics.getDeltaTime();

        if (this.amount != 0 && this.duration < 0.0F) {
            this.duration = Settings.ACTION_DUR_XFAST;
            this.amount--;
            AbstractDungeon.player.draw();
            player.hand.getTopCard().retain = true;
            AbstractDungeon.player.hand.refreshHandLayout();

            if (this.amount == 0)
                this.isDone = true;
        }
    }
}
