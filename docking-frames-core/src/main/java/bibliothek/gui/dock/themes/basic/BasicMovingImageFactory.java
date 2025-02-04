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
package bibliothek.gui.dock.themes.basic;

import bibliothek.gui.DockController;
import bibliothek.gui.Dockable;
import bibliothek.gui.dock.dockable.DockableMovingImageFactory;
import bibliothek.gui.dock.dockable.MovingImage;
import bibliothek.gui.dock.dockable.TrueMovingImage;
import bibliothek.gui.dock.title.DockTitle;
import bibliothek.gui.dock.title.DockTitle.Orientation;
import bibliothek.gui.dock.title.DockTitleManager;
import bibliothek.gui.dock.title.DockTitleVersion;
import bibliothek.gui.dock.title.UpdatingTitleMovingImage;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * A factory whose {@link MovingImage}s display a {@link DockTitle}.
 *
 * @author Benjamin Sigg
 */
public class BasicMovingImageFactory implements DockableMovingImageFactory {
    public MovingImage create(DockController controller, DockTitle snatched) {
        if (snatched.getOrigin() != null) {
            DockTitleVersion origin = snatched.getOrigin();
            return new UpdatingTitleMovingImage(snatched.getDockable(), origin, snatched.getOrientation());
        }

        /* TODO find a way to use the preferred size */
        Component c = snatched.getComponent();
        BufferedImage image = new BufferedImage(
                Math.max(1, c.getWidth()),
                Math.max(1, c.getHeight()),
                BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = image.getGraphics();
        c.paint(graphics);
        graphics.dispose();

        TrueMovingImage moving = new TrueMovingImage();
        moving.setImage(image);
        return moving;
    }

    public MovingImage create(DockController controller, Dockable dockable) {
        DockTitleVersion version = controller.getDockTitleManager().getVersion(DockTitleManager.THEME_FACTORY_ID);
        return new UpdatingTitleMovingImage(dockable, version, Orientation.FREE_HORIZONTAL);
    }
}
