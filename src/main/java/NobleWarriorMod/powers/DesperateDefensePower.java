package NobleWarriorMod.powers;

import NobleWarriorMod.actions.DesperateDefenseAction;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import NobleWarriorMod.NobleWarriorMod;

public class DesperateDefensePower extends AbstractPower {
    private static final String POWER_ID = "NobleWarrior:DesperateDefense";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public DesperateDefensePower(AbstractCreature owner, AbstractMonster target, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = this.baseAmount = amount;
        updateDescription();
        this.target = target;
        this.type = AbstractPower.PowerType.BUFF;
        this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID));
    }
    private int baseAmount;
    private AbstractMonster target;

    public void updateDescription() {
        if (this.amount == 1) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[2];
        }
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        this.baseAmount += stackAmount;
        updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(card.type == AbstractCard.CardType.ATTACK && amount > 0) {
            this.amount--;
            flash();
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new DesperateDefenseAction((AbstractPlayer)owner, target));
            updateDescription();
        }
    }

    public void atStartOfTurn() {
        this.amount = this.baseAmount;
        updateDescription();
    }
}
