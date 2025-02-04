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

package bibliothek.gui.dock.toolbar.measurement;

import bibliothek.gui.DockController;
import bibliothek.gui.Dockable;
import bibliothek.gui.Orientation;
import bibliothek.gui.Position;
import bibliothek.gui.dock.ToolbarContainerDockStation;
import bibliothek.gui.dock.ToolbarGroupDockStation;
import bibliothek.gui.dock.station.StationDropItem;
import bibliothek.gui.dock.station.StationDropOperation;
import bibliothek.gui.dock.station.toolbar.ToolbarContainerDropInfo;

import java.awt.*;

public class ToolbarContainerDockStationSample implements DropSample {
    private final ToolbarContainerDockStation station;
    private final Dockable dockable = new ToolbarGroupDockStation();

    public ToolbarContainerDockStationSample() {
        station = new ToolbarContainerDockStation(Orientation.VERTICAL);
        station.setDockablesMaxNumber(-1);
        DockController controller = new DockController();
        controller.add(station);
    }

    @Override
    public ToolbarContainerDockStation getStation() {
        return station;
    }

    @Override
    public Component getComponent() {
        return station.getComponent();
    }

    @Override
    public Color dropAt(int mouseX, int mouseY) {
        StationDropItem item = new StationDropItem(mouseX, mouseY, mouseX, mouseY, dockable);
        StationDropOperation operation = station.prepareDrop(item);
        if (operation == null) {
            return Color.BLACK;
        }

        ToolbarContainerDropInfo info = (ToolbarContainerDropInfo) operation;

        for (int i = 0, n = station.getDockableCount(); i < n; i++) {
            if (station.getDockable(i) == info.getDockableBeneathMouse()) {
                int index = i;
                if ((info.getSideDockableBeneathMouse() == Position.SOUTH) ||
                        (info.getSideDockableBeneathMouse() == Position.EAST)) {
                    index++;
                }
                if (index % 2 == 0) {
                    return Color.RED;
                } else {
                    return Color.BLUE;
                }
            }
        }

        return Color.WHITE;
    }
}
