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
package bibliothek.gui.dock.extension.css.property;

import bibliothek.gui.dock.extension.css.CssProperty;
import bibliothek.gui.dock.extension.css.CssPropertyContainer;
import bibliothek.gui.dock.extension.css.CssPropertyContainerListener;

/**
 * This simple {@link CssPropertyContainer} that does not have sub-properties.
 *
 * @author Benjamin Sigg
 */
public abstract class SimpleCssPropertyContainer implements CssPropertyContainer {
    @Override
    public void addPropertyContainerListener(CssPropertyContainerListener listener) {
        // ignore
    }

    public void removePropertyContainerListener(CssPropertyContainerListener listener) {
        // ignore
    }

    @Override
    public CssProperty<?> getProperty(String key) {
        return null;
    }

    public String[] getPropertyKeys() {
        return new String[]{};
    }
}
