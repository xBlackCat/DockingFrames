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

import bibliothek.gui.dock.SplitDockStation.Orientation;
import bibliothek.gui.dock.station.split.Divideable;

import java.awt.*;

/**
 * Represents a special {@link Divideable} that is at the end of the columns and allows the user to resize
 * the entire station.
 *
 * @author Benjamin Sigg
 */
public class ColumnDividier implements Divideable {
    private final WizardSplitDockStation station;

    public ColumnDividier(WizardSplitDockStation station) {
        this.station = station;
    }

    @Override
    public double getDividerAt(int x, int y) {
        double width = station.getWidth();
        double height = station.getHeight();

        return switch (station.getSide()) {
            case RIGHT, LEFT -> x / width;
            case BOTTOM, TOP -> y / height;
            default -> throw new IllegalStateException("unknown side: " + station.getSide());
        };
    }

    @Override
    public Rectangle getDividerBounds(double divider, Rectangle bounds) {
        if (bounds == null) {
            bounds = new Rectangle();
        }

        int gap = station.getDividerSize();
        int width = station.getWidth() - gap;
        int height = station.getHeight() - gap;

        switch (station.getSide()) {
            case RIGHT, LEFT -> {
                bounds.x = (int) (divider * width);
                bounds.y = 0;
                bounds.width = gap;
                bounds.height = height + gap;
            }
            case TOP, BOTTOM -> {
                bounds.x = 0;
                bounds.width = width + gap;
                bounds.y = (int) (divider * height);
                bounds.height = gap;
            }
        }
        return bounds;
    }

    @Override
    public Orientation getOrientation() {
        return station.getSide().getHeaderOrientation();
    }

    @Override
    public double getDivider() {
        return switch (station.getSide()) {
            case RIGHT, BOTTOM -> 0;
            case LEFT, TOP -> 1;
            default -> throw new IllegalStateException("unknown side: " + station.getSide());
        };
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
        return station.getWizardSplitLayoutManager().validateColumnDivider(divider);
    }

}
