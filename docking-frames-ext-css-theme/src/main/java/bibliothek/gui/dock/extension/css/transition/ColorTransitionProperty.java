/*
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 *
 * Copyright (C) 2012 Benjamin Sigg
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
package bibliothek.gui.dock.extension.css.transition;

import bibliothek.gui.dock.extension.css.CssItem;
import bibliothek.gui.dock.extension.css.CssScheme;
import bibliothek.gui.dock.extension.css.CssType;

import java.awt.*;

/**
 * A property for handing a {@link Color} with a transition.
 *
 * @author Benjamin Sigg
 */
public abstract class ColorTransitionProperty extends CssTransitionProperty<Color> {
    /**
     * Creates the new property.
     *
     * @param scheme the scheme in whose realm this property will work
     * @param item   the item to which this property belongs
     */
    public ColorTransitionProperty(CssScheme scheme, CssItem item) {
        super(scheme, item);
    }

    @Override
    public CssType<Color> getType(CssScheme scheme) {
        return scheme.getConverter(Color.class);
    }
}
