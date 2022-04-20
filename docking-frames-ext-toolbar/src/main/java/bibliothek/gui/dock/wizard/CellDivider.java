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

package bibliothek.gui.dock.wizard;

import bibliothek.gui.Dockable;
import bibliothek.gui.dock.SplitDockStation.Orientation;
import bibliothek.gui.dock.station.split.Divideable;
import bibliothek.gui.dock.station.split.Leaf;

import java.awt.*;

/**
 * This divider marks the outer {@link Dockable} of one column as being resized.
 *
 * @author Benjamin Sigg
 */
public class CellDivider implements Divideable {
    private final WizardSplitDockStation station;
    private final Leaf leaf;

    public CellDivider(WizardSplitDockStation station, Leaf leaf) {
        this.station = station;
        this.leaf = leaf;
    }

    public Leaf getLeaf() {
        return leaf;
    }

    @Override
    public double getDividerAt(int x, int y) {
        Rectangle bounds = leaf.getBounds();
        int gap = station.getDividerSize();

        return switch (station.getSide()) {
            case LEFT, RIGHT -> (y - bounds.y + gap / 2) / (double) (bounds.height + gap / 2);
            case BOTTOM, TOP -> (x - bounds.x + gap / 2) / (double) (bounds.width + gap / 2);
            default -> throw new IllegalArgumentException("unknown side: " + station.getSide());
        };
    }

    @Override
    public Rectangle getDividerBounds(double divider, Rectangle bounds) {
        if (bounds == null) {
            bounds = new Rectangle();
        }
        Rectangle leafBounds = leaf.getBounds();
        int gap = station.getDividerSize();

        switch (station.getSide()) {
            case LEFT, RIGHT -> {
                bounds.x = leafBounds.x;
                bounds.width = leafBounds.width;
                bounds.height = gap;
                bounds.y = (int) (divider * (leafBounds.height + gap / 2) + leafBounds.y - gap / 2);
                return bounds;
            }
            case BOTTOM, TOP -> {
                bounds.y = leafBounds.y;
                bounds.height = leafBounds.height;
                bounds.width = gap;
                bounds.x = (int) (divider * (leafBounds.width + gap / 2) + leafBounds.x - gap / 2);
                return bounds;
            }
            default -> throw new IllegalArgumentException("unknown side: " + station.getSide());
        }
    }

    @Override
    public Orientation getOrientation() {
        return switch (station.getSide()) {
            case LEFT, RIGHT -> Orientation.VERTICAL;
            case TOP, BOTTOM -> Orientation.HORIZONTAL;
            default -> throw new IllegalStateException("unknown side: " + station.getSide());
        };
    }

    @Override
    public double getDivider() {
        return 1;
    }

    @Override
    public double getActualDivider() {
        return getDivider();
    }

    @Override
    public void setDivider(double dividier) {
        // ignored
    }

    @Override
    public double validateDivider(double divider) {
        return station.getWizardSplitLayoutManager().validateDivider(divider, leaf);
    }

}
