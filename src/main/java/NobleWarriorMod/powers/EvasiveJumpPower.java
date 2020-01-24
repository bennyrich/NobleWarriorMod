package NobleWarriorMod.powers;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.cards.AbstractClassCard;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EvasiveJumpPower extends AbstractPower {
    public static final String POWER_ID = "NobleWarrior:EvasiveJump";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public EvasiveJumpPower(AbstractPlayer p) {
        this.name = NAME;
        this.ID = POWER_ID;
        owner = p;
        amount = -1;
        updateDescription();
        this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID));
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL && this.owner != null)
        {
            return 0F;
        }
        return damage;
    }

    public void atEndOfTurn(boolean isPlayer) {
        if(isPlayer) { AbstractClassCard.CAN_PLAY_ATTACK = true; }
    }

    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }
}
