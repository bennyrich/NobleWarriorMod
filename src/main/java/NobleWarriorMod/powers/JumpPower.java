package NobleWarriorMod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import NobleWarriorMod.NobleWarriorMod;

import java.util.ArrayList;

public class JumpPower extends AbstractPower {
    public static final String POWER_ID = "NobleWarrior:Jump";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean isMulti;
    private ArrayList<AbstractMonster> monsters = new ArrayList<>();
    private ArrayList<Integer> damages = new ArrayList<>();

    public JumpPower(AbstractCreature owner, int damage, boolean isMulti, AbstractMonster target) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.isMulti = isMulti;
        if (isMulti) { amount = damage; } else { amount = 0; }
        if(target != null) {
            monsters.add(target);
            damages.add(damage);
        }
        updateDescription();
        this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID));
    }

    public void updateDescription() {
        StringBuilder descr = new StringBuilder(DESCRIPTIONS[0]);
        for(int i=0; i < monsters.size(); i++) {
            if(monsters.get(i) != null) {
                descr.append(damages.get(i));
                descr.append(DESCRIPTIONS[1]);
                descr.append(monsters.get(i).name);
                descr.append(DESCRIPTIONS[4]);
            }
        }
        if(monsters.size() > 0) { descr.setLength(descr.length() - 4); }

        if(isMulti) {
            descr.append(DESCRIPTIONS[5]);
            descr.append(this.amount);
            descr.append(DESCRIPTIONS[2]);
        } else {
            descr.append(DESCRIPTIONS[3]);
        }
        this.description = descr.toString();
    }

    public void stackNewTarget(int stackAmount, AbstractMonster target) {
        monsters.add(target);
        damages.add(stackAmount);
        updateDescription();
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.isMulti = true;
        this.amount += stackAmount;
        updateDescription();
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        if(isMulti) {
            addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.amount, false),
                    DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
        for(int i=0; i < monsters.size(); i++) {
            if(monsters.get(i) != null) {
                addToBot(new DamageAction(monsters.get(i), new DamageInfo(this.owner, damages.get(i), DamageInfo.DamageType.NORMAL),
                        AbstractGameAction.AttackEffect.SLASH_VERTICAL));
            }
        }
    }
}
