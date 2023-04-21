package code.ui;

import code.ModFile;
import code.alchemy.ConcoctionBelt;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ConcoctionBeltPopUp {
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("PotionPopUp");

    public static final String[] TEXT = uiStrings.TEXT;

    public static final String THROW_LABEL = TEXT[0];

    public static final String DRINK_LABEL = TEXT[1];

    public static final String DISCARD_LABEL = TEXT[2];

    public static Texture OLD_POTION_BG = ImageMaster.loadImage("alchemyResources/images/ui/OldPotionBG.png");

    public static Texture OLD_POTION_MID = ImageMaster.loadImage("alchemyResources/images/ui/OldPotionMid.png");

    public static Texture OLD_POTION_TOP = ImageMaster.loadImage("alchemyResources/images/ui/OldPotionTop.png");

    public int slot;

    public AbstractPotion potion;

    public boolean isHidden = true;

    public boolean targetMode = false;

    private static final int RAW_W = 282;

    private static final int RAW_H = 286;

    private static final float HB_W = 184.0F;

    private static final float HB_H = 52.0F;

    private Hitbox hbTop = new Hitbox(184.0F, 52.0F);

    private Hitbox hbBot = new Hitbox(184.0F, 52.0F);

    private float x;

    private float y;

    private static final int SEGMENTS = 20;

    private Vector2[] points = new Vector2[20];

    private Vector2 controlPoint;

    private float arrowScale;

    private float arrowScaleTimer = 0.0F;

    private static final float ARROW_TARGET_SCALE = 1.2F;

    private static final int TARGET_ARROW_W = 256;

    private AbstractMonster hoveredMonster = null;

    private boolean autoTargetFirst = false;

    private boolean highlightTop = false;

    private final Color topHoverColor = new Color(0.5F, 0.9F, 1.0F, 0.0F);

    private final Color botHoverColor = new Color(1.0F, 0.4F, 0.3F, 0.0F);

    public ConcoctionBeltPopUp() {
        for (int i = 0; i < this.points.length; i++)
            this.points[i] = new Vector2();
    }

    public void setHitboxPosition() {
        if (!this.isHidden) {
            this.x = this.potion.posX;
            this.y = this.potion.posY + -160.0F * Settings.scale;
            this.hbTop.move(this.x, this.y + 44.0F * Settings.scale);
            this.hbBot.move(this.x, this.y + -14.0F * Settings.scale);
        }
    }

    public void open(int slot, AbstractPotion potion, boolean keyboard) {
        AbstractDungeon.topPanel.selectPotionMode = false;
        this.slot = slot;
        this.potion = potion;
        this.isHidden = false;
        setHitboxPosition();
        this.hbTop.clickStarted = false;
        this.hbBot.clickStarted = false;
        this.hbTop.clicked = false;
        this.hbBot.clicked = false;
        this.highlightTop = false;
        finishTargeting();
        if (keyboard)
            if (potion.targetRequired) {
                startTargeting();
                close();
            } else {
                this.highlightTop = true;
            }
    }

    public void close() {
        this.isHidden = true;
    }

    public void update() {
        if (!this.isHidden) {
            updateControllerInput();
            this.hbTop.update();
            this.hbBot.update();
            updateInput();
        } else if (this.targetMode) {
            updateControllerTargetInput();
            updateTargetMode();
        }
        if (AbstractDungeon.player.isDraggingCard && !this.isHidden)
            close();
    }

    private void updateControllerTargetInput() {
        if (Settings.isControllerMode) {
            int offsetEnemyIndex = 0;
            boolean autoTarget = this.autoTargetFirst;
            this.autoTargetFirst = false;
            if (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                offsetEnemyIndex--;
            if (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
                offsetEnemyIndex++;
            if (offsetEnemyIndex != 0 || autoTarget) {
                AbstractMonster newTarget;
                ArrayList<AbstractMonster> sortedMonsters = AbstractDungeon.getCurrRoom().monsters.monsters.stream().filter(m -> !m.isDying).sorted(AbstractMonster.sortByHitbox).collect(Collectors.toCollection(ArrayList::new));
                if (sortedMonsters.isEmpty())
                    return;
                for (AbstractMonster m : sortedMonsters) {
                    if (m.hb.hovered) {
                        this.hoveredMonster = m;
                        break;
                    }
                }
                if (this.hoveredMonster == null) {
                    if (offsetEnemyIndex == 1 || autoTarget) {
                        newTarget = sortedMonsters.get(0);
                    } else {
                        newTarget = sortedMonsters.get(sortedMonsters.size() - 1);
                    }
                } else {
                    int currentTargetIndex = sortedMonsters.indexOf(this.hoveredMonster);
                    int newTargetIndex = currentTargetIndex + offsetEnemyIndex;
                    newTargetIndex = (newTargetIndex + sortedMonsters.size()) % sortedMonsters.size();
                    newTarget = sortedMonsters.get(newTargetIndex);
                }
                if (newTarget != null) {
                    Hitbox target = newTarget.hb;
                    Gdx.input.setCursorPosition((int)target.cX, Settings.HEIGHT - (int)target.cY);
                    this.hoveredMonster = newTarget;
                }
                if (this.hoveredMonster != null && this.hoveredMonster.halfDead)
                    this.hoveredMonster = null;
            }
        }
    }

    private void updateControllerInput() {
        if (Settings.isControllerMode)
            if (CInputActionSet.cancel.isJustPressed()) {
                CInputActionSet.cancel.unpress();
                close();
            } else if (!this.hbTop.hovered && !this.hbBot.hovered) {
                if (this.potion.canUse()) {
                    Gdx.input.setCursorPosition((int)this.hbTop.cX, Settings.HEIGHT - (int)this.hbTop.cY);
                } else {
                    Gdx.input.setCursorPosition((int)this.hbBot.cX, Settings.HEIGHT - (int)this.hbBot.cY);
                }
            } else if (this.hbTop.hovered) {
                if (CInputActionSet.up.isJustPressed() || CInputActionSet.down.isJustPressed() || CInputActionSet.altUp.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                    Gdx.input.setCursorPosition((int)this.hbBot.cX, Settings.HEIGHT - (int)this.hbBot.cY);
            } else if (this.hbBot.hovered && this.potion.canUse() && (CInputActionSet.up.isJustPressed() || CInputActionSet.down.isJustPressed() || CInputActionSet.altUp.isJustPressed() || CInputActionSet.altDown.isJustPressed())) {
                Gdx.input.setCursorPosition((int)this.hbTop.cX, Settings.HEIGHT - (int)this.hbTop.cY);
            }
    }

    private void updateTargetMode() {
        if (InputHelper.justClickedRight || AbstractDungeon.isScreenUp || InputHelper.mY > Settings.HEIGHT - 80.0F * Settings.scale || AbstractDungeon.player.hoveredCard != null || InputHelper.mY < 140.0F * Settings.scale || CInputActionSet.cancel.isJustPressed()) {
            CInputActionSet.cancel.unpress();
            finishTargeting();
        }
        this.hoveredMonster = null;
        for (AbstractMonster m : (AbstractDungeon.getMonsters()).monsters) {
            if (m.hb.hovered && !m.isDying) {
                this.hoveredMonster = m;
                break;
            }
        }
        if (InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()) {
            InputHelper.justClickedLeft = false;
            CInputActionSet.select.unpress();
            if (this.hoveredMonster != null) {
                usePotionInSack(this.hoveredMonster, this.potion);
                ModFile.concoctionBelt.removePotion(this.slot);
                finishTargeting();
            }
        }
    }

    public static void usePotionInSack(AbstractCreature c, AbstractPotion p) {
        p.use(c);
    }

    private void updateInput() {
        if (this.hbTop.hovered || this.hbBot.hovered)
            this.highlightTop = false;
        if (InputHelper.justClickedLeft) {
            InputHelper.justClickedLeft = false;
            if (this.hbTop.hovered) {
                this.hbTop.clickStarted = true;
            } else if (this.hbBot.hovered) {
                this.hbBot.clickStarted = true;
            } else {
                close();
            }
        }
        if (((!ConcoctionBelt.selectPotionActions[this.slot].isJustPressed() || (!this.highlightTop && !this.hbTop.hovered)) && !this.hbTop.clicked && (!this.hbTop.hovered ||
                !CInputActionSet.select.isJustPressed())) || (AbstractDungeon.isScreenUp && !(this.potion instanceof com.megacrit.cardcrawl.potions.FruitJuice))) {
            if ((this.hbBot.clicked || (this.hbBot.hovered && CInputActionSet.select.isJustPressed())) && this.potion.canDiscard()) {
                CInputActionSet.select.unpress();
                this.hbBot.clicked = false;
                CardCrawlGame.sound.play("POTION_DROP_2");
                ModFile.concoctionBelt.removePotion(this.slot);
                this.slot = -1;
                this.potion = null;
                close();
            }
        } else {
            CInputActionSet.select.unpress();
            this.hbTop.clicked = false;
            if (this.potion.canUse()) {
                if (this.potion.targetRequired) {
                    startTargeting();
                } else {
                    usePotionInSack(null, this.potion);
                    CardCrawlGame.sound.play("POTION_1");
                    ModFile.concoctionBelt.removePotion(this.slot);
                }
                close();
            }
        }
    }

    private void startTargeting() {
        AbstractDungeon.player.releaseCard();
        AbstractDungeon.player.inspectMode = false;
        AbstractDungeon.topPanel.potionUi.targetMode = false;
        this.targetMode = true;
        GameCursor.hidden = true;
        this.autoTargetFirst = true;
    }

    private void finishTargeting() {
        this.targetMode = false;
        GameCursor.hidden = false;
    }

    public void render(SpriteBatch sb) {
        if (!this.isHidden) {
            sb.setColor(Color.WHITE);
            sb.draw(OLD_POTION_BG, this.x - 141.0F, this.y - 143.0F, 141.0F, 143.0F, 282.0F, 286.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 282, 286, false, false);
            this.topHoverColor.a = (this.hbTop.hovered || this.highlightTop) ? 0.5F : MathHelper.fadeLerpSnap(this.topHoverColor.a, 0.0F);
            this.botHoverColor.a = this.hbBot.hovered ? 0.5F : MathHelper.fadeLerpSnap(this.botHoverColor.a, 0.0F);
            sb.setBlendFunction(770, 1);
            sb.setColor(this.topHoverColor);
            sb.draw(OLD_POTION_TOP, this.x - 141.0F, this.y - 143.0F, 141.0F, 143.0F, 282.0F, 286.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 282, 286, false, false);
            sb.setColor(this.botHoverColor);
            sb.draw(OLD_POTION_MID, this.x - 141.0F, this.y - 143.0F, 141.0F, 143.0F, 282.0F, 286.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 282, 286, false, false);
            sb.setBlendFunction(770, 771);
            Color c = Settings.CREAM_COLOR;
            if (!this.potion.canUse() || AbstractDungeon.isScreenUp)
                c = Color.GRAY;
            String label = DRINK_LABEL;
            if (this.potion.isThrown)
                label = THROW_LABEL;
            FontHelper.renderFontCenteredWidth(sb, FontHelper.topPanelInfoFont, label, this.x, this.y + 55.0F * Settings.scale, c);
            FontHelper.renderFontCenteredWidth(sb, FontHelper.topPanelInfoFont, DISCARD_LABEL, this.x, this.y + -2.0F * Settings.scale, Color.SALMON.cpy());
            this.hbTop.render(sb);
            this.hbBot.render(sb);
            if (this.hbTop.hovered) {
                if (this.potion.isThrown) {
                    TipHelper.renderGenericTip(this.x + 124.0F * Settings.scale, this.y + 50.0F * Settings.scale, PotionPopUp.LABEL[0], PotionPopUp.MSG[0]);
                } else {
                    TipHelper.renderGenericTip(this.x + 124.0F * Settings.scale, this.y + 50.0F * Settings.scale, PotionPopUp.LABEL[1], PotionPopUp.MSG[1]);
                }
            } else if (this.hbBot.hovered) {
                TipHelper.renderGenericTip(this.x + 124.0F * Settings.scale, this.y + 50.0F * Settings.scale, PotionPopUp.LABEL[2], PotionPopUp.MSG[2]);
            }
        }
        if (this.targetMode) {
            if (this.hoveredMonster != null)
                this.hoveredMonster.renderReticle(sb);
            renderTargetingUi(sb);
        }
    }

    private void renderTargetingUi(SpriteBatch sb) {
        float x = InputHelper.mX;
        float y = InputHelper.mY;
        this.controlPoint = new Vector2(this.potion.posX - (x - this.potion.posX) / 4.0F, y + (y - this.potion.posY - 40.0F * Settings.scale) / 2.0F);
        if (this.hoveredMonster == null) {
            this.arrowScale = Settings.scale;
            this.arrowScaleTimer = 0.0F;
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
        } else {
            this.arrowScaleTimer += Gdx.graphics.getDeltaTime();
            if (this.arrowScaleTimer > 1.0F)
                this.arrowScaleTimer = 1.0F;
            this.arrowScale = Interpolation.elasticOut.apply(Settings.scale, Settings.scale * 1.2F, this.arrowScaleTimer);
            sb.setColor(new Color(1.0F, 0.2F, 0.3F, 1.0F));
        }
        Vector2 tmp = new Vector2(this.controlPoint.x - x, this.controlPoint.y - y);
        tmp.nor();
        drawCurvedLine(sb, new Vector2(this.potion.posX, this.potion.posY + ((this.controlPoint.y > this.potion.posY) ? 35.0F : -35.0F) * Settings.scale), new Vector2(x, y), this.controlPoint);
        sb.draw(ImageMaster.TARGET_UI_ARROW, x - 128.0F, y - 128.0F, 128.0F, 128.0F, 256.0F, 256.0F, this.arrowScale, this.arrowScale, tmp.angle() + 90.0F, 0, 0, 256, 256, false, false);
    }

    private void drawCurvedLine(SpriteBatch sb, Vector2 start, Vector2 end, Vector2 control) {
        float radius = 7.0F * Settings.scale;
        for (int i = 0; i < this.points.length - 1; i++) {
            float angle;
            this.points[i] = (Vector2)Bezier.quadratic((Vector)this.points[i], i / 20.0F, (Vector)start, (Vector)control, (Vector)end, (Vector)new Vector2());
            radius += 0.4F * Settings.scale;
            if (i != 0) {
                Vector2 tmp = new Vector2((this.points[i - 1]).x - (this.points[i]).x, (this.points[i - 1]).y - (this.points[i]).y);
                angle = tmp.nor().angle() + 90.0F;
            } else {
                Vector2 tmp = new Vector2(this.controlPoint.x - (this.points[i]).x, this.controlPoint.y - (this.points[i]).y);
                angle = tmp.nor().angle() + 270.0F;
            }
            sb.draw(ImageMaster.TARGET_UI_CIRCLE, (this.points[i]).x - 64.0F, (this.points[i]).y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, radius / 18.0F, radius / 18.0F, angle, 0, 0, 128, 128, false, false);
        }
    }
}
