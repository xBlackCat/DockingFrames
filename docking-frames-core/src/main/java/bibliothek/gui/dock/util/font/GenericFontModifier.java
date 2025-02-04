/*
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 *
 * Copyright (C) 2007 Benjamin Sigg
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * Benjamin Sigg
 * benjamin_sigg@gmx.ch
 * CH - Switzerland
 */
package bibliothek.gui.dock.util.font;

import java.awt.*;

/**
 * A {@link FontModifier} that can change a font in more than just one way.
 *
 * @author Benjamin Sigg
 */
public class GenericFontModifier implements FontModifier {
    /**
     * Tells how to transform some aspect of a font.
     *
     * @author Benjamin Sigg
     */
    public enum Modify {
        ON, OFF, REVERSE, IGNORE
    }

    private Modify italic = Modify.IGNORE;
    private Modify bold = Modify.IGNORE;

    private boolean sizeDelta = true;
    private int size = 0;

    /**
     * Creates a new modifier
     */
    public GenericFontModifier() {
        // nothing
    }

    /**
     * Sets the size of the new font. If {@link #setSizeDelta(boolean) delta size}
     * is set to <code>true</code> then this value is added to the original size
     * of the font, otherwise this value replaces the original size.
     *
     * @param size the new size or the delta size
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Gets the size of the new font
     *
     * @return the new size
     * @see #setSize(int)
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets whether the value of {@link #setSize(int) size} should be added
     * to the original size or replace the original size.
     *
     * @param sizeDelta <code>true</code> if the two sizes should be summed up,
     *                  <code>false</code> if the size specified in this modifier replaces
     *                  the original size
     */
    public void setSizeDelta(boolean sizeDelta) {
        this.sizeDelta = sizeDelta;
    }

    /**
     * Tells whether the size of the fonts generated by this modifier depend
     * on the original size of the used font or not.
     *
     * @return <code>true</code> if the sizes are summed up, <code>false</code>
     * if not
     * @see #setSizeDelta(boolean)
     */
    public boolean isSizeDelta() {
        return sizeDelta;
    }

    /**
     * Sets how to modify the italic aspect of a font.
     *
     * @param italic the new modification
     */
    public void setItalic(Modify italic) {
        this.italic = italic;
    }

    /**
     * Tells how this modifier modifies the italic aspect of a font.
     *
     * @return the modification
     */
    public Modify getItalic() {
        return italic;
    }

    /**
     * Sets how to modify the bold aspect of a font.
     *
     * @param bold the new modification
     */
    public void setBold(Modify bold) {
        this.bold = bold;
    }

    /**
     * Tells how this modifier modifies the bold aspect of a font.
     *
     * @return the modification
     */
    public Modify getBold() {
        return bold;
    }

    public Font modify(Font font) {
        int flags = 0;
        if (font.isBold()) {
            flags |= Font.BOLD;
        }
        if (font.isItalic()) {
            flags |= Font.ITALIC;
        }

        int newFlags = modify(flags, Font.ITALIC, italic);
        newFlags = modify(newFlags, Font.BOLD, bold);

        float size = this.size;
        if (sizeDelta) {
            size += font.getSize();
        }

        return font.deriveFont(newFlags, size);
    }

    private int modify(int flags, int flag, Modify modification) {
        return switch (modification) {
            case IGNORE -> flags;
            case ON -> flags | flag;
            case OFF -> flags & ~flag;
            case REVERSE -> flags ^ flag;
        };

    }
}
