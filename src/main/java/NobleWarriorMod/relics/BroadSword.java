package NobleWarriorMod.relics;

import basemod.abstracts.CustomRelic;
import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.CardTagsEnum;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;

public class BroadSword extends CustomRelic {

    public static final String ID = "NobleWarrior:BroadSword";
    private boolean active = true;

    public BroadSword() { super(ID, new Texture(NobleWarriorMod.getRelicImagePath(ID)), AbstractRelic.RelicTier.STARTER, AbstractRelic.LandingSound.FLAT); }

    public void atBattleStart() { this.active = true; }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(this.active && card.hasTag(CardTagsEnum.SQUIRE)) {
            flash();
/*
            AbstractMonster m = null;

            if (action.target != null) {
                m = (AbstractMonster)action.target;
            }

            AbstractDungeon.actionManager.addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, this));
            AbstractCard tmp = card.makeSameInstanceOf();
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = Settings.HEIGHT / 2.0F;
            if (tmp.cost > 0) {
                tmp.freeToPlayOnce = true;
            }
            tmp.applyPowers();
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, m, card.energyOnUse, true));
*/
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(1));
            this.active = false;
        }
    }

    public String getUpdatedDescription() { return this.DESCRIPTIONS[0]; }

    public AbstractRelic makeCopy() { return (AbstractRelic)new BroadSword(); }
}