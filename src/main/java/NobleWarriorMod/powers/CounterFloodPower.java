package NobleWarriorMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import NobleWarriorMod.NobleWarriorMod;
import com.megacrit.cardcrawl.powers.SlowPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

public class CounterFloodPower extends AbstractPower {
    public static final String POWER_ID = "NobleWarrior:CounterFlood";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public CounterFloodPower(AbstractPlayer owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = PowerType.DEBUFF;
        this.owner = owner;
        this.amount = amount;
        this.isTurnBased = false;
        updateDescription();
        this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        updateDescription();
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.owner != null && info.owner instanceof AbstractMonster && info.type != DamageInfo.DamageType.THORNS) {
            NobleWarriorMod.logger.info("owner: " + owner.toString());
            for(int i = 0; i < amount; i++) {
                int rand = AbstractDungeon.miscRng.random(1, 6);
                switch (rand) {
                    case 1:
                        addToBot(new ApplyPowerAction(info.owner, this.owner, new WeakPower(info.owner, 1, true), 1));
                        break;
                    case 2:
                        addToBot(new ApplyPowerAction(info.owner, this.owner, new VulnerablePower(info.owner, 1, true), 1));
                        break;
                    case 3:
                        addToBot(new ApplyPowerAction(info.owner, this.owner, new BlindPower(info.owner, 1, true), 1));
                        break;
                    case 4:
                        addToBot(new ApplyPowerAction(info.owner, this.owner, new SilencePower(info.owner, 1, true), 1));
                        break;
                    case 5:
                        addToBot(new ApplyPowerAction(info.owner, this.owner, new ImmobilizePower(info.owner, 1, true), 1));
                        break;
                    case 6:
                        addToBot(new ApplyPowerAction(info.owner, this.owner, new SlowPower(info.owner, 1), 1));
                        break;
                }
            }
        }
        return damageAmount;
    }
}
