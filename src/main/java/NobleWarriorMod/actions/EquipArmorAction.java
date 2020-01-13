package NobleWarriorMod.actions;

import NobleWarriorMod.characters.NobleWarrior;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.MetallicizePower;

public class EquipArmorAction extends AbstractGameAction {

    public EquipArmorAction(AbstractPlayer p, int block_threshold, int metallicize) {
        this.p = p;
        this.amount = metallicize;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.BLOCK;
        this.block_threshold = block_threshold;
    }

    private AbstractPlayer p;
    private int block_threshold;

    public void update() {
        if (p.currentBlock >= block_threshold) {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new LoseBlockAction((AbstractCreature)p,
                    (AbstractCreature)p, this.amount));
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction((AbstractCreature)p, (AbstractCreature)p,
                    (AbstractPower)new MetallicizePower((AbstractCreature)p, this.amount), this.amount));
        }
        this.isDone = true;
    }
}
