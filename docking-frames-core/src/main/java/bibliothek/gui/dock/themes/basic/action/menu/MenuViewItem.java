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

package bibliothek.gui.dock.themes.basic.action.menu;

import bibliothek.gui.dock.action.view.ViewItem;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * An item that is shown in a menu.
 *
 * @param <C> The type of component that represents this view
 * @author Benjamin Sigg
 */
public interface MenuViewItem<C extends JComponent> extends ViewItem<C> {
    /**
     * Adds a listener which will be called if this view is triggered. The
     * listener should only be called, if the user clicked directly onto
     * this view.
     *
     * @param listener the new listener
     */
    void addActionListener(ActionListener listener);

    /**
     * Removes a listener from this view.
     *
     * @param listener the listener to remove
     */
    void removeActionListener(ActionListener listener);
}
