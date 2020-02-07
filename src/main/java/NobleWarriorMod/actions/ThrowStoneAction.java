package NobleWarriorMod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public class ThrowStoneAction extends AbstractGameAction {
    public ThrowStoneAction(AbstractPlayer p, int damage, DamageInfo.DamageType damageType) {
        this.player = p;
        this.damage = damage;
        this.lowestHealth = 0;
        this.damageType = damageType;
        this.monsters = AbstractDungeon.getMonsters().monsters;
    }
    private int damage;
    private DamageInfo.DamageType damageType;
    private AbstractPlayer player;
    private int lowestHealth;
    private ArrayList<AbstractMonster> monsters;
    private AbstractMonster targetMonster;

    public void update() {
        monsters.forEach(monster -> {
            if(!monster.isDead && (lowestHealth == 0 || monster.currentHealth < lowestHealth)) {
                lowestHealth = monster.currentHealth;
                targetMonster = monster;
            }
        });
        if(targetMonster != null) {
            addToBot(new DamageAction(targetMonster, new DamageInfo(player, this.damage, this.damageType), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
        this.isDone = true;
    }
}
