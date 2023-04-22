package code.ui;

import basemod.ClickableUIElement;
import code.ModFile;
import code.TheAlchemist;
import code.util.TexLoader;
import code.util.TextureHelper;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;

public class HerbPouchButton extends ClickableUIElement {
    private static final float X_OFF = 0.0F;

    private static final float Y_OFF = 228.0F;

    private static final float HB_WIDTH = 128.0F;

    private static final float HB_HEIGHT = 128.0F;

    private static final float COUNT_X = 48.0F * Settings.scale;

    private static final float COUNT_Y = -16.0F * Settings.scale;

    private static final float COUNT_OFFSET_X = 48.0F * Settings.scale;

    private static final float COUNT_OFFSET_Y = -18.0F * Settings.scale;

    private static final float DECK_TIP_X = 0.0F * Settings.scale;

    private static final float DECK_TIP_Y = 128.0F * Settings.scale;

    private final Texture herbPouch = TexLoader.getTexture(ModFile.makeImagePath("herbPouch.png"));
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString(ModFile.makeID("HerbPouch"));

    public static final String[] TEXT = uiStrings.TEXT;

    private final BobEffect bob;

    private boolean isOpen = false;

    public HerbPouchButton() {
        super((Texture)null, 0.0F, Settings.HEIGHT - 800.0F * Settings.scale, 128.0F, 128.0F);
        this.bob = new BobEffect(1.1F);
    }

    protected void onHover() {}

    protected void onUnhover() {}

    protected void onClick() {
        if (this.isOpen && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.EXHAUST_VIEW) {
            this.isOpen = false;
            CardCrawlGame.sound.play("DECK_CLOSE");
            AbstractDungeon.closeCurrentScreen();
        } else if (!AbstractDungeon.isScreenUp) {
            if (ModFile.herbPouch.isEmpty()) {
                AbstractPlayer p = AbstractDungeon.player;
                AbstractDungeon.effectList.add(new ThoughtBubble(p.dialogX, p.dialogY, 3.0F, TEXT[2], true));
            } else {
                ExhaustPileViewScreenPatches.showHerbs = true;
                AbstractDungeon.exhaustPileViewScreen.open();
                this.isOpen = true;
            }
        }
    }

    public void setX(float x) {
        super.setX(x - 0.0F);
    }

    public void update() {
        super.update();
        this.bob.update();
        if (Gdx.input.isKeyJustPressed(ModFile.herbPouchKey))
            onClick();
    }

    public void render(SpriteBatch sb) {
        if (ModFile.herbPouch != null && (AbstractDungeon.player.chosenClass == TheAlchemist.Enums.THE_ALCHEMIST || ModFile.herbPouch.size() > 0) &&
                !AbstractDungeon.overlayMenu.combatDeckPanel.isHidden) {
            float x = this.hitbox.x + this.hitbox.width / 2.0F;
            float y = this.hitbox.y + this.hitbox.height / 2.0F;
            sb.setColor(Color.WHITE);
            TextureHelper.draw(sb, this.herbPouch, x, y + this.bob.y);
            String msg = Integer.toString(ModFile.herbPouch.size());
            sb.setColor(Color.WHITE);
            TextureHelper.draw(sb, ImageMaster.DECK_COUNT_CIRCLE, x + COUNT_OFFSET_X, y + COUNT_OFFSET_Y);
            FontHelper.renderFontCentered(sb, FontHelper.turnNumFont, msg, x + COUNT_X, y + COUNT_Y);
            this.hitbox.render(sb);
            if (this.hitbox.hovered && (AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp)
                TipHelper.renderGenericTip(x + DECK_TIP_X, y + DECK_TIP_Y, TEXT[0], TEXT[1]);
        }
    }
}