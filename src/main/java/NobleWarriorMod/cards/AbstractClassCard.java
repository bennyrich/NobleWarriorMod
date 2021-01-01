package NobleWarriorMod.cards;

import NobleWarriorMod.NobleWarriorMod;
import NobleWarriorMod.enums.CardTagsEnum;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

public abstract class AbstractClassCard extends CustomCard {
    protected AbstractClassCard(String id, String name, String imagePath, int cost, String description, AbstractCard.CardType cardType,
                                AbstractCard.CardColor color, CardRarity rarity, AbstractCard.CardTarget target, AbstractCard.CardTags job) {
        super(id, name, imagePath, cost, description, cardType, color, rarity, target);

        cardHeader = "";
        previews = new ArrayList<>();
        if(job == CardTagsEnum.SQUIRE) { cardHeader = "Squire"; }
        if(job == CardTagsEnum.ARCHER) { cardHeader = "Archer"; }
        if(job == CardTagsEnum.KNIGHT) { cardHeader = "Knight"; }
        if(job == CardTagsEnum.MONK) { cardHeader = "Monk"; }
        if(job == CardTagsEnum.DANCER) { cardHeader = "Dancer"; }
        if(job == CardTagsEnum.NINJA) { cardHeader = "Ninja"; }
        if(job == CardTagsEnum.SAMURAI) { cardHeader = "Samurai"; }
        if(job == CardTagsEnum.GEOMANCER) { cardHeader = "Geomancer"; }
        if(job == CardTagsEnum.LANCER) { cardHeader = "Lancer"; }
        if(job == CardTagsEnum.THIEF) { cardHeader = "Thief"; }

    }
    private String cardHeader;
    public static boolean CAN_PLAY_ATTACK = true;
    protected ArrayList<AbstractCard> previews;
    public boolean isWaterWalked = false;

    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if(this.type == CardType.ATTACK) {
            return (CAN_PLAY_ATTACK && super.canUse(p,m));
        }
        return super.canUse(p,m);
    }

    public void atTurnStartPreDraw() {
        this.isWaterWalked = false;
        NobleWarriorMod.logger.info("Just set isWaterWalked to FALSE for " + this.name + " / " + this.cardID);
        super.atTurnStartPreDraw();
    }

    /* MIGHT NEED THIS?
    public AbstractCard makeCopy() {
        try {
            return getClass().getConstructor(new Class[0]).newInstance(new Object[0]);
        }
        catch (InstantiationException|IllegalAccessException|NoSuchMethodException|java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }
     */

    protected String GetHeaderText() { return cardHeader; }
    protected Color GetHeaderColor() { return Settings.GOLD_COLOR.cpy(); }

    @Override
    public void renderCardTip(SpriteBatch sb) {
        super.renderCardTip(sb);
        boolean renderTip = (boolean) ReflectionHacks.getPrivate(this, AbstractCard.class, "renderTip");

        int count = 0;
        if (!Settings.hideCards && renderTip) {
            if (AbstractDungeon.player != null && AbstractDungeon.player.isDraggingCard) {
                return;
            }
        }
    }

    @Override
    public void renderCardPreview(SpriteBatch sb) {
        if (AbstractDungeon.player == null || !AbstractDungeon.player.isDraggingCard) {
            int index = 0;
            for (AbstractCard c : previews) {
                float dx = (AbstractCard.IMG_WIDTH * 0.9f - 5f) * drawScale;
                float dy = (AbstractCard.IMG_HEIGHT * 0.4f - 5f) * drawScale;
                if (current_x > Settings.WIDTH * 0.75f) {
                    c.current_x = current_x + dx;
                } else {
                    c.current_x = current_x - dx;
                }
                if (index == 0) {
                    c.current_y = current_y + dy;
                } else {
                    c.current_y = current_y - dy;
                }
                c.drawScale = drawScale * 0.8f;
                c.render(sb);
                index++;
            }
        }
    }

    @Override
    public void renderCardPreviewInSingleView(SpriteBatch sb) {
        int index = 0;
        for (AbstractCard c : previews) {
            c.current_x = 485.0F * Settings.scale;
            c.current_y = (795.0F - 510.0F * index) * Settings.scale;
            c.drawScale = 0.8f;
            c.render(sb);
            index++;
        }
    }

    public void render(SpriteBatch sb) {
        super.render(sb);
        RenderHeader(sb, false);
    }

    public void renderAsPreview(SpriteBatch sb) {
        super.render(sb);
        RenderHeader(sb, false);
    }

    public void renderInLibrary(SpriteBatch sb) {
        super.renderInLibrary(sb);
        RenderHeader(sb, false);
    }

    public void renderInSingleCardPopup(SpriteBatch sb, boolean preRender) {
        RenderHeader(sb, true);
    }

    protected void RenderHeader(SpriteBatch sb, boolean isCardPopup) {
        String text = GetHeaderText();
        if (text != null && !this.isFlipped && !this.isLocked) {
            BitmapFont font;

            float offsetY, yPos, xPos;
            if (isCardPopup) {

                font = FontHelper.SCP_cardTitleFont_small;
                xPos = Settings.WIDTH / 2.0F + 10.0F * Settings.scale;
                yPos = Settings.HEIGHT / 2.0F + 393.0F * Settings.scale;
                offsetY = 0.0F;
            }
            else {

                font = FontHelper.cardTitleFont; // WAS FontHelper.cardTitleFont_small, now scale down cardTitleFont
                xPos = this.current_x;
                yPos = this.current_y;
                offsetY = 400.0F * Settings.scale * this.drawScale / 2.0F;
            }

            BitmapFont.BitmapFontData fontData = font.getData();
            float originalScale = fontData.scaleX;
            float scaleMulti = 0.8F;

            int length = text.length();
            if (length > 20) {

                scaleMulti -= 0.02F * (length - 20);
                if (scaleMulti < 0.5F)
                {
                    scaleMulti = 0.5F;
                }
            }

            fontData.setScale(scaleMulti * (isCardPopup ? 1.0F : this.drawScale));

            FontHelper.renderRotatedText(sb, font, text, xPos, yPos, 0.0F, offsetY, this.angle, true,

                    GetHeaderColor());

            fontData.setScale(originalScale);
        }
    }
}
