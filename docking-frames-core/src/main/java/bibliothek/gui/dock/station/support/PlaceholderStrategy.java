/*
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 *
 * Copyright (C) 2010 Benjamin Sigg
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
package bibliothek.gui.dock.station.support;

import bibliothek.gui.DockStation;
import bibliothek.gui.Dockable;
import bibliothek.gui.dock.util.PropertyKey;
import bibliothek.util.Path;

/**
 * This strategy tells which {@link Dockable}s can leave a placeholder
 * on a {@link DockStation}.
 *
 * @author Benjamin Sigg
 */
public interface PlaceholderStrategy {
    /**
     * Defines for which {@link Dockable}s which {@link Path} is used as placeholder, or which
     * placeholders are no longer valid and to be removed.
     */
    PropertyKey<PlaceholderStrategy> PLACEHOLDER_STRATEGY = new PropertyKey<>("dock.placeholder_strategy");

    /**
     * Informs this strategy that it will from no one be used by <code>station</code>.
     *
     * @param station the station which uses this strategy
     */
    void install(DockStation station);

    /**
     * Informs this strategy that it will no longer be used for <code>station</code>.
     *
     * @param station the station that is removed
     */
    void uninstall(DockStation station);

    /**
     * Adds a listener to this strategy.
     *
     * @param listener the new listener, not <code>null</code>
     */
    void addListener(PlaceholderStrategyListener listener);

    /**
     * Removes a listener from this strategy.
     *
     * @param listener the listener to remove
     */
    void removeListener(PlaceholderStrategyListener listener);

    /**
     * Gets the placeholder which represents <code>dockable</code>.
     *
     * @param dockable some child of <code>station</code>
     * @return the placeholder id or <code>null</code>
     */
    Path getPlaceholderFor(Dockable dockable);

    /**
     * Tells whether <code>placeholder</code> is associated with any {@link Dockable}.
     *
     * @param placeholder the placeholder in question
     * @return <code>true</code> if <code>placeholder</code> is still in use
     */
    boolean isValidPlaceholder(Path placeholder);
}
