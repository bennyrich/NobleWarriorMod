package NobleWarriorMod.actions;

import NobleWarriorMod.NobleWarriorMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDiscardEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect;

public class InfiltrateAction extends AbstractGameAction {
    public InfiltrateAction(AbstractPlayer p) { this(p, 1); }

    public InfiltrateAction(AbstractPlayer p, int cardsToCopy) {
        this.player = p;
        this.cardsToCopy = cardsToCopy;
        this.duration = DURATION;
        this.actionType = ActionType.CARD_MANIPULATION;
    }
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("NobleWarrior:InfiltrateAction");
    private static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer player;
    private int cardsToCopy;
    private static final float DURATION = Settings.ACTION_DUR_XFAST;

    public void update() {
        if(this.player.hand.size() == 0) {
            this.isDone = true;
            return;
        }
        if (this.duration == DURATION) {
            if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                this.isDone = true;
                return;
            }
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.cardsToCopy, false, false);
            AbstractDungeon.player.hand.applyPowers();
            tickDuration();
            return;
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractCard temp = c.makeStatEquivalentCopy();

                if(!temp.isEthereal) {
                    temp.isEthereal = true;
                    temp.rawDescription = "Ethereal. NL " + temp.rawDescription;
                    temp.initializeDescription();
                }
                if(!temp.exhaust) {
                    temp.exhaust = true;
                    temp.rawDescription = temp.rawDescription + " NL Exhaust.";
                    temp.initializeDescription();
                }
                //addToBot(new MakeTempCardInHandAction(temp));
                if(AbstractDungeon.player.hand.size() < 10) {
                    // TODO: Apply power from Water Walking?
                    AbstractDungeon.player.hand.addToHand(c);
                } else {
                    AbstractDungeon.player.discardPile.addToTop(c);
                }
                if(AbstractDungeon.player.hand.size() < 10) {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToHandEffect(temp, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                } else {
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDiscardEffect(temp, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                }
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.player.hand.glowCheck();
        tickDuration();
    }
}
