/**
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 * <p>
 * Copyright (C) 2007 Benjamin Sigg
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 * <p>
 * Benjamin Sigg
 * benjamin_sigg@gmx.ch
 * CH - Switzerland
 */

package bibliothek.gui.dock.event;

import bibliothek.gui.dock.action.DockActionSource;

/**
 * An abstract implementation of {@link DockActionSourceListener}. This class
 * can be used instead of {@link DockActionSourceListener} if only a few
 * selected methods have to be implemented.
 * @author Benjamin Sigg
 */
public abstract class DockActionSourceAdapter implements DockActionSourceListener {

    public void actionsAdded(DockActionSource source, int firstIndex, int lastIndex) {
        // do nothing
    }

    public void actionsRemoved(DockActionSource source, int firstIndex, int lastIndex) {
        // do nothing
    }
}
