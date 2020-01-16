package NobleWarriorMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import NobleWarriorMod.NobleWarriorMod;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class CombatTrainingPower extends AbstractPower {
    private static final String POWER_ID = "NobleWarrior:CombatTraining";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final int ATTACK_AMT = 3;
    private int strength;

    public CombatTrainingPower(AbstractCreature owner, int strength) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = ATTACK_AMT;
        this.strength = strength;
        updateDescription();
        this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID));
    }

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.strength + DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[3] + this.strength + DESCRIPTIONS[2];
        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.strength += stackAmount;
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.type == AbstractCard.CardType.ATTACK) {
            this.amount--;
            if (this.amount == 0) {
                flash();
                this.amount = ATTACK_AMT;
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new ApplyPowerAction((AbstractCreature)owner, (AbstractCreature)owner,
                        (AbstractPower)new StrengthPower((AbstractCreature)owner, strength), strength));
            }
            updateDescription();
        } else {
            this.amount = ATTACK_AMT;
            updateDescription();
        }
    }

    public void atStartOfTurn() {
        this.amount = ATTACK_AMT;
        updateDescription();
    }
}
