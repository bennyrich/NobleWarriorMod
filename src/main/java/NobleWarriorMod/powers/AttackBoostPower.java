package NobleWarriorMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import NobleWarriorMod.NobleWarriorMod;

public class AttackBoostPower extends AbstractPower {
    private static final String POWER_ID = "NobleWarrior:AttackBoost";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean active;

    public AttackBoostPower(AbstractCreature owner, int amount, boolean active) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.active = active;
        this.amount = amount;
        updateDescription();
        this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        updateDescription();
    }

    public void atStartOfTurn() {
        this.active = true;
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (active && type == DamageInfo.DamageType.NORMAL) {
            return damage += this.amount;
        }
        return damage;
    }

    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(active && card.type == AbstractCard.CardType.ATTACK) {
            active = false;
        }
    }
}
