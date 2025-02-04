/*
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 *
 * Copyright (C) 2011 Benjamin Sigg
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
package bibliothek.gui.dock.util;

import bibliothek.gui.DockController;

/**
 * A {@link SilentPropertyValue} is a {@link PropertyValue} that does not react to
 * changes of its value.
 *
 * @param <A> the type of the wrapped value
 * @author Benjamin Sigg
 */
public class SilentPropertyValue<A> extends PropertyValue<A> {
    /**
     * Creates a new value.
     *
     * @param key        the key used to access the value in {@link DockProperties}
     * @param controller the controller from which properties are to be read
     */
    public SilentPropertyValue(PropertyKey<A> key, DockController controller) {
        super(key, controller);
    }

    /**
     * Creates a new value.
     *
     * @param key the key used to access the value in {@link DockProperties}
     */
    public SilentPropertyValue(PropertyKey<A> key) {
        super(key);
    }

    @Override
    protected void valueChanged(A oldValue, A newValue) {
        // ignored
    }

}
