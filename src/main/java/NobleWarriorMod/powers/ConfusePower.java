package NobleWarriorMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import NobleWarriorMod.NobleWarriorMod;
import basemod.ReflectionHacks;

public class ConfusePower extends AbstractPower {
    private static final String POWER_ID = "NobleWarrior:Confuse";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean justApplied;

    public ConfusePower(AbstractPlayer player, AbstractMonster owner, int amount) {
        this(player, owner, amount, false);
    }

    public ConfusePower(AbstractPlayer player, AbstractMonster owner, int amount, boolean isSourceMonster) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.type = PowerType.DEBUFF;
        this.owner = this.realOwner = owner;
        this.player = player;
        this.amount = amount;
        this.isTurnBased = true;
        this.justApplied = false;
        if (isSourceMonster) {
            this.justApplied = true;
        }
        updateDescription();
        this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID));
    }
    private AbstractMonster realOwner;
    private AbstractPlayer player;

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public void onInitialApplication() {
        if (realOwner.getIntentBaseDmg() > -1 && !owner.hasPower("Artifact")) {
            if (((Boolean)ReflectionHacks.getPrivate(owner, AbstractMonster.class, "isMultiDmg")).booleanValue()) {
                for (int i = 0; i < ((Integer)ReflectionHacks.getPrivate(owner, AbstractMonster.class, "intentMultiAmt")).intValue(); i++) {
                    AbstractMonster q = AbstractDungeon.getRandomMonster();
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)q, new DamageInfo((AbstractCreature)owner, realOwner.getIntentDmg(),
                            DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                }
            } else {
                AbstractMonster q = AbstractDungeon.getRandomMonster();
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)q, new DamageInfo((AbstractCreature)owner, realOwner.getIntentDmg(),
                        DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
        }
        addToBot((AbstractGameAction)new StunMonsterAction(realOwner, (AbstractCreature)player));
    }

    public void atStartOfTurn() {
        if (realOwner.getIntentBaseDmg() > -1) {
            if (((Boolean)ReflectionHacks.getPrivate(owner, AbstractMonster.class, "isMultiDmg")).booleanValue()) {
                for (int i = 0; i < ((Integer)ReflectionHacks.getPrivate(owner, AbstractMonster.class, "intentMultiAmt")).intValue(); i++) {
                    AbstractMonster q = AbstractDungeon.getRandomMonster();
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)q, new DamageInfo((AbstractCreature)owner, realOwner.getIntentDmg(),
                            DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
                }
            } else {
                AbstractMonster q = AbstractDungeon.getRandomMonster();
                AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)q, new DamageInfo((AbstractCreature)owner, realOwner.getIntentDmg(),
                        DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            }
        }
        addToBot((AbstractGameAction)new StunMonsterAction(realOwner, (AbstractCreature)player));
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
