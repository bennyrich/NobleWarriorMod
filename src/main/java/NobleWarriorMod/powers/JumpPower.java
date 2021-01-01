package NobleWarriorMod.powers;

import NobleWarriorMod.cards.AbstractClassCard;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
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
    private ArrayList<AbstractClassCard> cards = new ArrayList<>();
    private ArrayList<AbstractMonster> monsters = new ArrayList<>();

    public JumpPower(AbstractCreature owner, int damage, boolean isMulti, AbstractMonster target, AbstractClassCard c) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        cards.add(c);
        monsters.add(target);
        updateDescription();
        this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID));
    }

    public JumpPower(AbstractCreature owner, AbstractMonster target, AbstractClassCard c) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        cards.add(c);
        monsters.add(target);
        updateDescription();
        this.img = new Texture(NobleWarriorMod.getPowerImagePath(POWER_ID));
    }

    public void updateDescription() {
        StringBuilder descr = new StringBuilder();

        if(this.description != null) {
            descr.append(this.description);
            descr.append(" NL ");
        }

        descr.append(DESCRIPTIONS[0]);

        descr.append(cards.get(cards.size() - 1).baseDamage);

        if(monsters.get(monsters.size() - 1) == null) {
            descr.append(DESCRIPTIONS[2]);
        } else {
            if(monsters.get(0) != null) {
                descr.append(DESCRIPTIONS[1]);
                descr.append(monsters.get(monsters.size() - 1).name);
                descr.append(DESCRIPTIONS[3]);
            }
        }

        this.description = descr.toString();
    }

    public void stackPower(AbstractClassCard card, AbstractMonster target) {
        cards.add(card);
        monsters.add(target);
        updateDescription();
    }

    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        for(int i=0; i<cards.size(); i++) {
            AbstractMonster m = null;
            if (monsters.get(i) != null) {
                m = monsters.get(i);
                cards.get(i).calculateCardDamage(m);
            }

            if (cards.get(i).cost > 0) {
                cards.get(i).freeToPlayOnce = true;
            }
            cards.get(i).purgeOnUse = true;

            AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(cards.get(i), m, cards.get(i).energyOnUse, true));
        }
    }
}
