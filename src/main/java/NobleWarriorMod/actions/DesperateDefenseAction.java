package NobleWarriorMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class DesperateDefenseAction extends AbstractGameAction {
    public DesperateDefenseAction(AbstractPlayer p, AbstractMonster target) {
        this.p = p;
        this.target = (target == null) ? AbstractDungeon.getMonsters().getRandomMonster(true) : target;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
    }

    private AbstractPlayer p;
    private AbstractMonster target;

    public void update() {
        CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        for (AbstractCard drawPileCard : this.p.drawPile.group) {
            if (drawPileCard.type == AbstractCard.CardType.SKILL) {
                tmp.addToRandomSpot(drawPileCard);
            }
        }

        if (tmp.size() > 0) {
            AbstractCard randomlyChosenCard = tmp.getRandomCard(AbstractDungeon.cardRandomRng);
            AbstractDungeon.player.drawPile.group.remove(randomlyChosenCard);
            (AbstractDungeon.getCurrRoom()).souls.remove(randomlyChosenCard);
            randomlyChosenCard.freeToPlayOnce = true;
            AbstractDungeon.player.limbo.group.add(randomlyChosenCard);

            randomlyChosenCard.current_y = -200.0F * Settings.scale;
            randomlyChosenCard.target_x = Settings.WIDTH / 2.0F - 200.0F * Settings.scale;
            randomlyChosenCard.target_y = Settings.HEIGHT / 2.0F;
            randomlyChosenCard.targetAngle = 0.0F;
            randomlyChosenCard.lighten(false);
            randomlyChosenCard.drawScale = 0.12F;
            randomlyChosenCard.targetDrawScale = 0.75F;

            if (!randomlyChosenCard.canUse(AbstractDungeon.player, this.target)) {
                AbstractDungeon.actionManager.addToTop((AbstractGameAction)new UnlimboAction(randomlyChosenCard));
                AbstractDungeon.actionManager.addToTop((AbstractGameAction)new DiscardSpecificCardAction(randomlyChosenCard, AbstractDungeon.player.limbo));
                AbstractDungeon.actionManager.addToTop((AbstractGameAction)new WaitAction(0.4F));
            } else {
                randomlyChosenCard.applyPowers();
                AbstractDungeon.actionManager.addToTop((AbstractGameAction)new QueueCardAction(randomlyChosenCard, (AbstractCreature)this.target));
                AbstractDungeon.actionManager.addToTop((AbstractGameAction)new UnlimboAction(randomlyChosenCard));
                if (!Settings.FAST_MODE) {
                    AbstractDungeon.actionManager.addToTop((AbstractGameAction)new WaitAction(Settings.ACTION_DUR_MED));
                } else {
                    AbstractDungeon.actionManager.addToTop((AbstractGameAction)new WaitAction(Settings.ACTION_DUR_FASTER));
                }
            }
        }
        this.isDone = true;
    }
}
