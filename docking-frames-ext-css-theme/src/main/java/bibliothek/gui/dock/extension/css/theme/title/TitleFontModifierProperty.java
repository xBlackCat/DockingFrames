/*
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 *
 * Copyright (C) 2013 Benjamin Sigg
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
package bibliothek.gui.dock.extension.css.theme.title;

import bibliothek.gui.dock.extension.css.CssItem;
import bibliothek.gui.dock.extension.css.CssScheme;
import bibliothek.gui.dock.extension.css.doc.CssDocKey;
import bibliothek.gui.dock.extension.css.doc.CssDocPath;
import bibliothek.gui.dock.extension.css.doc.CssDocProperty;
import bibliothek.gui.dock.extension.css.doc.CssDocText;
import bibliothek.gui.dock.extension.css.property.font.CssFontModifier;
import bibliothek.gui.dock.extension.css.property.font.CssFontTransitionProperty;
import bibliothek.gui.dock.extension.css.theme.CssDockTitle;
import bibliothek.gui.dock.title.AbstractMultiDockTitle;
import bibliothek.gui.dock.util.font.FontModifier;

@CssDocProperty(
        path = @CssDocPath(referencePath = CssDockTitle.class, referenceId = "self"),
        property = @CssDocKey(key = "fontmodifier"),
        type = CssFontModifier.class,
        description = @CssDocText(text = "Modifies the font of the title, e.g. makes it italic or changes it size"))
public class TitleFontModifierProperty extends CssFontTransitionProperty {
    private final AbstractMultiDockTitle title;

    public TitleFontModifierProperty(AbstractMultiDockTitle title, CssScheme scheme, CssItem item) {
        super(scheme, item);
        this.title = title;
    }

    @Override
    protected void setModifier(FontModifier modifier) {
        title.setFontModifier(modifier);
    }
}
