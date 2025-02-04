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
package bibliothek.gui.dock.themes.color;

import bibliothek.gui.DockStation;
import bibliothek.gui.Dockable;
import bibliothek.gui.dock.StackDockStation;
import bibliothek.gui.dock.util.color.AbstractDockColor;
import bibliothek.gui.dock.util.color.DockColor;
import bibliothek.util.Path;

import java.awt.*;

/**
 * Color related to a single {@link Dockable} that is in a tab for example
 * on a {@link StackDockStation}.
 *
 * @author Benjamin Sigg
 */
public abstract class TabColor extends AbstractDockColor {
    /**
     * the kind of color {@link TabColor} is
     */
    public static final Path KIND_TAB_COLOR = KIND_DOCK_COLOR.append("TabColor");

    private final DockStation station;
    private final Dockable dockable;

    /**
     * Creates a new TabColor.
     *
     * @param id       the identifier of the color that is searched
     * @param kind     the kind of {@link DockColor} this is.
     * @param station  the station on which the color will be used
     * @param dockable the {@link Dockable} for whose tab this color is used
     * @param backup   a backup color in case that no color can be found
     */
    public TabColor(String id, Path kind, DockStation station, Dockable dockable, Color backup) {
        super(id, kind, backup);
        this.station = station;
        this.dockable = dockable;
    }

    /**
     * Creates a new TabColor.
     *
     * @param id       the identifier of the color that is searched
     * @param station  the station on which the color will be used
     * @param dockable the {@link Dockable} for whose tab this color is used
     * @param backup   a backup color in case that no color can be found
     */
    public TabColor(String id, DockStation station, Dockable dockable, Color backup) {
        this(id, KIND_TAB_COLOR, station, dockable, backup);
    }

    /**
     * Gets the station on which the tab is shown.
     *
     * @return the station, might be <code>null</code>
     */
    public DockStation getStation() {
        return station;
    }

    /**
     * Gets the element for which the tab is shown.
     *
     * @return the element
     */
    public Dockable getDockable() {
        return dockable;
    }
}
