package NobleWarriorMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import NobleWarriorMod.NobleWarriorMod;

public class BlindPower extends AbstractPower {
    public static final String POWER_ID = "NobleWarrior:Blind";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean justApplied;

    public BlindPower(AbstractCreature owner, int amount) {
        this(owner, amount, false);
    }

    public BlindPower(AbstractCreature owner, int amount, boolean isSourceMonster) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = PowerType.DEBUFF;
        this.owner = owner;
        this.amount = amount;
        this.isTurnBased = true;
        this.justApplied = isSourceMonster;
        updateDescription();
        this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID));
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        updateDescription();
    }

    public void updateDescription() {
        if(amount == 1) {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            NobleWarriorMod.logger.info("Normal damage recognized; damage = " + damage);
            if (this.owner.hasPower("Weakened")) {
                if (!this.owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Crane")) {
                    NobleWarriorMod.logger.info("Paper Crane recognized; damage = " + damage * (float) (5 / 6));
                    return damage * (5F / 6F);
                }
                NobleWarriorMod.logger.info("Normal weak power recognized; damage = " + damage * (float) (2 / 3));
                return damage * (2F / 3F);
            } else {
                NobleWarriorMod.logger.info("No weak recognized; damage = " + damage * 0.5F);
                return damage * 0.5F;
            }
        }
        return damage;
    }

    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0) {
            addToBot((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            addToBot((AbstractGameAction)new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }
}
