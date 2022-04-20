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
package bibliothek.gui.dock.station.split;

import bibliothek.gui.Dockable;
import bibliothek.util.Path;

import java.util.List;

/**
 * A node of an {@link AbstractSplitDockGrid}, represents a stack of {@link Dockable}s
 * and their position.
 *
 * @author Benjamin Sigg
 */
public interface GridNode<D> {
    /**
     * Gets the x-coordinate, relative to all the other nodes.
     *
     * @return the x-coordinate
     */
    double getX();

    /**
     * Gets the y-coordinate, relative to all the other nodes.
     *
     * @return the y-coordinate
     */
    double getY();

    /**
     * Gets the width, relative to all the other nodes.
     *
     * @return the width
     */
    double getWidth();

    /**
     * Gets the height, relative to all the other nodes.
     *
     * @return the height
     */
    double getHeight();

    /**
     * Gets an unmodifiable list containing all the {@link Dockable} of this node.
     *
     * @return the list of dockables, may be empty
     */
    List<D> getDockables();

    /**
     * Gets the one dockable that is selected, must be part of {@link #getDockables()}.
     *
     * @return the selected dockable or <code>null</code>
     */
    D getSelected();

    /**
     * Gets all the placeholders associated with this node.
     *
     * @return the placeholders in an unmodifiable list
     */
    List<Path> getPlaceholders();
}
