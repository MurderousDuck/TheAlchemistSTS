package code.util;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.megacrit.cardcrawl.core.Settings;

public class TextureHelper {
    public static Texture getTexture(String textureString) {
        return TexLoader.getTexture(textureString);
    }

    private static Pixmap redPixel() {
        Pixmap pm = new Pixmap(1, 1, Pixmap.Format.RGB888);
        pm.setColor(16711680);
        pm.drawPixel(0, 0);
        return pm;
    }

    public static Texture buildTextureFromAtlasRegion(TextureAtlas.AtlasRegion atlasRegion) {
        TextureData textureData = atlasRegion.getTexture().getTextureData();
        if (!textureData.isPrepared())
            textureData.prepare();
        Pixmap pixmap = new Pixmap(atlasRegion.getRegionWidth(), atlasRegion.getRegionHeight(), textureData.getFormat());
        pixmap.drawPixmap(textureData
                .consumePixmap(), 0, 0, atlasRegion

                .getRegionX(), atlasRegion
                .getRegionY(), atlasRegion.packedWidth, atlasRegion.packedHeight);
        return new Texture(pixmap);
    }

    public static TextureAtlas.AtlasRegion buildAtlasRegionFromTexture(Texture texture) {
        return new TextureAtlas.AtlasRegion(texture, 0, 0, texture

                .getWidth(), texture
                .getHeight());
    }

    public static void draw(SpriteBatch sb, Texture texture, float cX, float cY) {
        drawScaledAndRotated(sb, texture, cX, cY, 1.0F, 0.0F);
    }

    public static void drawScaled(SpriteBatch sb, Texture texture, float cX, float cY, float scale) {
        drawScaledAndRotated(sb, texture, cX, cY, scale, 0.0F);
    }

    public static void drawRotated(SpriteBatch sb, Texture texture, float cX, float cY, float rotation) {
        drawScaledAndRotated(sb, texture, cX, cY, 1.0F, rotation);
    }

    public static void drawScaledAndRotated(SpriteBatch sb, Texture texture, float cX, float cY, float scale, float rotation) {
        float w = texture.getWidth();
        float h = texture.getHeight();
        float halfW = w / 2.0F;
        float halfH = h / 2.0F;
        sb.draw(texture, cX - halfW, cY - halfH, halfW, halfH, w, h, scale * Settings.scale, scale * Settings.scale, rotation, 0, 0, (int)w, (int)h, false, false);
    }

    public static void drawScaledAndRotated(SpriteBatch sb, TextureAtlas.AtlasRegion img, float cX, float cY, float scale, float rotation) {
        float w = img.packedWidth;
        float h = img.packedHeight;
        float halfW = w / 2.0F;
        float halfH = h / 2.0F;
        sb.draw((TextureRegion)img, cX - halfW, cY - halfH, halfW, halfH, w, h, scale * Settings.scale, scale * Settings.scale, rotation);
    }

    public static final Texture defaultTexture = new Texture(redPixel());
}
