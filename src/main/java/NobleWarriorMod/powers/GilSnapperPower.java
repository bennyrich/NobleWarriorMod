package NobleWarriorMod.powers;

import NobleWarriorMod.NobleWarriorMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainGoldAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.GainPennyEffect;

public class GilSnapperPower extends AbstractPower {
    private static final String POWER_ID = "NobleWarrior:GilSnapper";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean isUpgraded;
    private static final int GOLD_CAP = 50;
    private int currGold;

    public GilSnapperPower(AbstractCreature owner, boolean isUpgraded) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        currGold = 0;
        this.isUpgraded = isUpgraded;
        updateDescription();
        this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID));
    }

    public void updateDescription() {
        if(isUpgraded) {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[1] + DESCRIPTIONS[2] + currGold + DESCRIPTIONS[3];
        } else {
            description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + currGold + DESCRIPTIONS[3];
        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        isUpgraded = true;
        updateDescription();
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        int gainGold = isUpgraded ? 2 * damageAmount : damageAmount;

        if(currGold < GOLD_CAP) {
            if (currGold + gainGold >= GOLD_CAP) {
                int temp = GOLD_CAP - currGold;
                addToBot(new GainGoldAction(temp));
                for(int i=0; i<temp; i++) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(info.owner.hb.cX, info.owner.hb.cY));
                }
                currGold = GOLD_CAP;
            } else {
                addToBot(new GainGoldAction(gainGold));
                for(int i=0; i<gainGold; i++) {
                    AbstractDungeon.effectList.add(new GainPennyEffect(info.owner.hb.cX, info.owner.hb.cY));
                }
                currGold += gainGold;
            }
        }
        updateDescription();
        return damageAmount;
    }
}
