package com.blecua84.pokerapp.gui;

import javax.annotation.concurrent.NotThreadSafe;
import java.awt.*;

/**
 * Clase implementada para pintar texto alineado con respecto a un punto, habrá dos tipos de alineado; vertical y
 * horizontalmente, con tres posibilidades para cada tipo: arriba, medio y abajo para el alineado vertical.
 * Con izquierda, centro y derecha para el alineado horizontal. Los tipos de alineamiento los modelaremos como
 * Enumerados anidados, agregaremos los atributos para el tipo de fuente y para el color.
 *
 * @author blecua84
 */
@NotThreadSafe
public class TextPrinter {

    public enum VerticalAlign { TOP, MIDDLE, BOTTOM }

    public enum HorizontalAlign { LEFT, CENTER, RIGHT }

    private Font font;
    private Color color;
    private int width;
    private int height;
    private VerticalAlign vAlign = VerticalAlign.TOP;
    private HorizontalAlign hAlign = HorizontalAlign.LEFT;

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public VerticalAlign getVerticalAlign() {
        return vAlign;
    }

    public void setVerticalAlign(VerticalAlign vAlign) {
        this.vAlign = vAlign;
    }

    public HorizontalAlign getHorizontalAlign() {
        return hAlign;
    }

    public void setHorizontalAlign(HorizontalAlign hAlign) {
        this.hAlign = hAlign;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    private int getOffSetX(int widthText) {
        int result = 0;
        if (hAlign == HorizontalAlign.CENTER){
            result = (width - widthText)/2;
        } else if (hAlign == HorizontalAlign.RIGHT){
            result = width - widthText;
        }
        return result;
    }

    private int getOffSetY(int ascent, int descent) {
        int result = ascent;
        if (vAlign == VerticalAlign.MIDDLE){
            result = (height + ascent - descent)/2;
        } else if (vAlign == VerticalAlign.BOTTOM){
            result = height - descent;
        }
        return result;
    }

    public void print(Graphics g, String text, int x, int y) {
        g.setColor(color);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics(font);
        int widthText = fm.stringWidth(text);
        g.drawString(text,
                x + getOffSetX(widthText),
                y + getOffSetY(fm.getAscent(), fm.getDescent()));
    }
}
