package NobleWarriorMod.powers;

import NobleWarriorMod.NobleWarriorMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class AggressiveGuardPower extends AbstractPower {
    private static final String POWER_ID = "NobleWarrior:AggressiveGuard";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public AggressiveGuardPower(AbstractCreature owner, int damage) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = damage;
        updateDescription();
        this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        updateDescription();
    }

    public void onGainedBlock(float blockAmount) {
        AbstractMonster mo = AbstractDungeon.getRandomMonster();
        flash();
        addToTop((AbstractGameAction)new DamageAction(mo, new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
    }

    public void atEndOfTurn(boolean isPlayer) {
        addToBot((AbstractGameAction)new RemoveSpecificPowerAction(owner, owner, ID));
    }
}
