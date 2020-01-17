package NobleWarriorMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import NobleWarriorMod.NobleWarriorMod;

public class BodyLanguagePower extends AbstractPower {
    private static final String POWER_ID = "NobleWarrior:BodyLanguage";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BodyLanguagePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = PowerType.BUFF;
        this.owner = owner;
        this.amount = amount;
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
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + DESCRIPTIONS[2] + amount + DESCRIPTIONS[3];
        }
    }

    public void atStartOfTurnPostDraw() {
        flash();
        for(int i = 0; i < amount; i++) {
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction((AbstractPlayer)owner, 1));
            AbstractDungeon.actionManager.addToBottom(new ExhaustAction(1, false, false, false));
        }
    }
}
