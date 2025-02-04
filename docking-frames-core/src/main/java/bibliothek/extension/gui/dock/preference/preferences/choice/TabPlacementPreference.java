/*
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 *
 * Copyright (C) 2009 Benjamin Sigg
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
package bibliothek.extension.gui.dock.preference.preferences.choice;

import bibliothek.extension.gui.dock.preference.preferences.ChoiceDockPropertyPreference;
import bibliothek.gui.dock.StackDockStation;
import bibliothek.gui.dock.station.stack.tab.layouting.TabPlacement;
import bibliothek.gui.dock.util.DockProperties;
import bibliothek.util.Path;

/**
 * Lets the user choose the value of {@link StackDockStation#TAB_PLACEMENT}.
 *
 * @author Benjamin Sigg
 */
public class TabPlacementPreference extends ChoiceDockPropertyPreference<TabPlacement> {
    /**
     * Creates a new choice.
     *
     * @param properties the properties to read and write
     * @param path       the unique identifier of this preference
     */
    public TabPlacementPreference(DockProperties properties, Path path) {
        super(properties, StackDockStation.TAB_PLACEMENT, path, new TabPlacementChoice(properties));

        setLabelId("preference.layout.tabplacement.label");
        setDescriptionId("preference.layout.tabplacement.description");
    }
}
