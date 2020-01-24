package NobleWarriorMod.powers;

import NobleWarriorMod.NobleWarriorMod;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MountingFuryPower extends AbstractPower {
    public static final String POWER_ID = "NobleWarrior:MountingFury";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int plusDamage;

    public MountingFuryPower(AbstractPlayer p, int startingDamage, int plusDamage) {
        this.name = NAME;
        this.ID = POWER_ID;
        owner = p;
        amount = startingDamage;
        this.plusDamage = plusDamage;
        updateDescription();
        this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID));
    }

    public void stackNew(int startingDamage, int plusDamage) {
        this.amount += startingDamage;
        this.plusDamage += plusDamage;
        updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + plusDamage + DESCRIPTIONS[2];
    }

    public void atEndOfTurn(boolean isPlayer) {
        if(!isPlayer) { return; }
        addToBot(new DamageAllEnemiesAction(null, DamageInfo.createDamageMatrix(this.amount, true),
                DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        amount += plusDamage;
        updateDescription();
    }
}
