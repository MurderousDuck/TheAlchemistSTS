package code.alchemy;

import code.ModFile;
import code.ui.ConcoctionBeltPopUp;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputAction;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;

import java.util.ArrayList;

public class ConcoctionBelt {
    private static UIStrings uiStrings = null;

    public static String[] TEXT = null;

    private static Texture panel = null;

    public ArrayList<AbstractPotion> potions = null;

    private Hitbox hb;

    private float flashRedTimer = 0.0F;

    public ConcoctionBeltPopUp potionUi;

    private boolean init = false;

    public boolean showing = false;

    private static final int width = 240;

    private static final int height = 100;

    private static final float above = 250.0F;

    private int moveState = 0;

    private float dx;

    private float dy;

    private float startx;

    private float starty;

    public static InputAction[] selectPotionActions = new InputAction[4];

    public static int potionPotency = 0;

    public static final Color POTENCY_COLOR = new Color(0.8F, 1.0F, 1.0F, 1.0F);

    public ConcoctionBelt() {
        this.potionUi = new ConcoctionBeltPopUp();
        this.hb = new Hitbox(240.0F * Settings.scale, 60.0F * Settings.scale);
        uiStrings = CardCrawlGame.languagePack.getUIString(ModFile.makeID("ConcoctionBelt"));
        TEXT = uiStrings.TEXT;
        loadImage();
        loadKeySettings();
    }

    public void loadKeySettings() {
        for (int i = 0; i < selectPotionActions.length; i++)
            selectPotionActions[i] = new InputAction(ModFile.potionSackKeys[i]);
    }

    private static void loadImage() {
        if (panel == null)
            panel = new Texture("alchemyResources/images/ui/PotionSack.png");
    }

    public void update() {
        if (!this.init) {
            this.hb.move(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY + 150.0F * Settings.scale);
            this.potions = new ArrayList<>();
            for (int j = 0; j < 4; j++)
                this.potions.add(newPotionSlot(j));
            this.init = true;
        }
        if (this.flashRedTimer != 0.0F) {
            this.flashRedTimer -= Gdx.graphics.getDeltaTime();
            if (this.flashRedTimer < 0.0F)
                this.flashRedTimer = 0.0F;
        }
        this.potionUi.update();
        this.hb.update();
        AbstractPotion openP = null;
        boolean keyboard = false;
        for (AbstractPotion p : this.potions) {
            p.update();
            if (!(p instanceof PotionSlot)) {
                if (p.hb.justHovered)
                    if (MathUtils.randomBoolean()) {
                        CardCrawlGame.sound.play("POTION_1", 0.1F);
                    } else {
                        CardCrawlGame.sound.play("POTION_3", 0.1F);
                    }
                if (p.hb.hovered) {
                    p.scale = Settings.scale * 1.4F;
                    if ((AbstractDungeon.player.hoveredCard == null && InputHelper.justClickedLeft) || CInputActionSet.select.isJustPressed()) {
                        CInputActionSet.select.unpress();
                        InputHelper.justClickedLeft = false;
                        openP = p;
                    }
                    continue;
                }
                p.scale = MathHelper.scaleLerpSnap(p.scale, Settings.scale);
            }
        }
        for (int i = 0; i < selectPotionActions.length; i++) {
            if (this.potions != null && !(this.potions.get(i) instanceof PotionSlot) && selectPotionActions[i].isJustPressed()) {
                openP = this.potions.get(i);
                keyboard = true;
            }
        }
        if (this.moveState == 0 && openP != null)
            this.potionUi.open(openP.slot, openP, keyboard);
    }

    public void render(SpriteBatch sb) {
        if (!this.init || !this.showing || this.potions == null)
            return;
        float r = 0.0F;
        if (this.flashRedTimer != 0.0F)
            r += this.flashRedTimer / 2.0F;
        if (this.hb.hovered) {
            sb.setColor(new Color(1.0F, 1.0F - 0.4F * r, 1.0F - 0.4F * r, 1.0F));
        } else {
            sb.setColor(new Color(0.6F + 0.4F * r, 0.6F, 0.6F, 0.8F + 0.2F * r));
        }
        sb.draw(panel, this.hb.x, this.hb.y, 0.0F, 0.0F, 240.0F, 60.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 240, 60, false, false);
        boolean potion_hovered = false;
        for (AbstractPotion p : this.potions) {
            p.render(sb);
            if (p.hb.hovered && !(p instanceof PotionSlot)) {
                TipHelper.queuePowerTips(InputHelper.mX + 60.0F * Settings.scale, InputHelper.mY, p.tips);
                potion_hovered = true;
            }
        }
        if (this.hb.hovered && !potion_hovered)
            TipHelper.renderGenericTip(InputHelper.mX + 60.0F * Settings.scale, InputHelper.mY, TEXT[1], TEXT[0]);
        for (int i = 0; i < this.potions.size(); i++) {
            AbstractPotion p = this.potions.get(i);
            if (!(p instanceof PotionSlot)) {
                float textSpacingUpper = 30.0F * Settings.scale;
                float textSpacingLower = -35.0F * Settings.scale;
                float textY = p.hb.cY + textSpacingUpper;
                float textYPower = p.hb.cY + textSpacingLower;
                String shortcut = selectPotionActions[i].getKeyString();
                if (shortcut.length() > 3)
                    shortcut = shortcut.substring(0, 3);
                FontHelper.renderFontCentered(sb, FontHelper.topPanelAmountFont, shortcut, p.posX, textY, Settings.CREAM_COLOR);
            }
        }
        this.hb.render(sb);
    }

    public boolean addPotion(AbstractPotion potion) {
        this.showing = true;
        int index = 0;
        for (AbstractPotion p : this.potions) {
            if (p instanceof PotionSlot)
                break;
            index++;
        }
        if (index < this.potions.size()) {
            this.potions.set(index, potion);
            setPotionPosition(index, potion);
            potion.flash();
            AbstractPotion.playPotionSound();
            return true;
        }
        flashRed();
        return false;
    }

    public void setPotion(int index, Concoction potion) {
        this.potions.set(index, potion);
        setPotionPosition(index, potion);
        potion.flash();
        AbstractPotion.playPotionSound();
    }

    public void removePotion(int slot) {
        this.potions.set(slot, newPotionSlot(slot));
    }

    public void removeAllPotions() {
        if (this.potions != null)
            for (int i = 0; i < this.potions.size(); i++)
                removePotion(i);
    }

    private PotionSlot newPotionSlot(int slot) {
        PotionSlot ps = new PotionSlot(slot);
        setPotionPosition(slot, ps);
        return ps;
    }

    private void flashRed() {
        this.flashRedTimer = 1.0F;
    }

    private void setPotionPosition(int index, AbstractPotion potion) {
        potion.setAsObtained(index);
        movePotion(index, potion);
    }

    private void movePotion(int index, AbstractPotion potion) {
        float x = this.hb.cX + (index - 1.5F) * Settings.POTION_W;
        float y = this.hb.cY;
        potion.move(x, y);
        potion.hb.move(x, y);
    }

    public void show() {
        this.showing = true;
    }

    public void hide() {
        this.showing = false;
        this.potionUi.close();
    }
}

