package NobleWarriorMod.powers;

import NobleWarriorMod.NobleWarriorMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.InstantKillAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DoomPower extends AbstractPower {
    public static final String POWER_ID = "NobleWarrior:Doom";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int counter;
    private AbstractMonster monsterOwner;
    private int DOOM_DAMAGE = 50;

    public DoomPower(AbstractCreature owner) { this(owner, 3); }

    public DoomPower(AbstractCreature owner, int counter) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        monsterOwner = (owner instanceof AbstractMonster) ? (AbstractMonster)owner : null;
        this.amount = -1;
        this.counter = counter;
        updateDescription();
        this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID));
    }

    public void updateDescription() {
        description = DESCRIPTIONS[0];
    }

    private void updateImage(int counter) {
        try {
            this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID + counter));
        }
        catch(Exception e) {
           NobleWarriorMod.logger.info("Error in DoomPower; Doom image was not found for counter " + counter);
           this.counter = 0;
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer) { return; }
        counter--;
        updateImage(counter);
        if(counter <= 0) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, ID));
            if (monsterOwner != null) {
                if(monsterOwner.type == AbstractMonster.EnemyType.ELITE || monsterOwner.type == AbstractMonster.EnemyType.BOSS) {
                    addToBot(new DamageAction(monsterOwner, new DamageInfo(monsterOwner, DOOM_DAMAGE, DamageInfo.DamageType.THORNS),
                            AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                }
                else {
                    addToBot(new InstantKillAction(monsterOwner));
                }
            }
        }
    }
}
