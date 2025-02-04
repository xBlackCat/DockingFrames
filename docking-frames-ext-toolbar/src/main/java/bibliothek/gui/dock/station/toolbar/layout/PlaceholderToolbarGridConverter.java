/*
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 *
 * Copyright (C) 2012 Herve Guillaume, Benjamin Sigg
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
 * Herve Guillaume
 * rvguillaume@hotmail.com
 * FR - France
 *
 * Benjamin Sigg
 * benjamin_sigg@gmx.ch
 * CH - Switzerland
 */

package bibliothek.gui.dock.station.toolbar.layout;

import bibliothek.gui.Dockable;
import bibliothek.gui.dock.station.support.ConvertedPlaceholderListItem;
import bibliothek.gui.dock.station.support.PlaceholderListItem;
import bibliothek.gui.dock.station.support.PlaceholderMap;

/**
 * A converter used by the {@link PlaceholderToolbarGrid} when reading a
 * {@link PlaceholderMap}.
 *
 * @param <D> the type that represents a dockable
 * @param <P> the type that is actually stored in the grid
 * @author Benjamin Sigg
 */
public interface PlaceholderToolbarGridConverter<D, P extends PlaceholderListItem<D>> {
    /**
     * Converts the {@link Dockable} <code>dockable</code> into an item that can
     * be shown in the view. This method is also responsible for actually adding
     * the item to the view.
     *
     * @param dockable the dockable to convert
     * @param item     the item that is converted, may contain additional information
     * @return the item that is shown in the view
     */
    P convert(D dockable, ConvertedPlaceholderListItem item);

    /**
     * Called after the item <code>item</code> has been added to the grid.
     *
     * @param item the item that was added
     */
    void added(P item);
}
