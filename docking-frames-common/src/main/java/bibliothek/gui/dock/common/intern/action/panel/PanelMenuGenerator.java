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
package bibliothek.gui.dock.common.intern.action.panel;

import bibliothek.gui.Dockable;
import bibliothek.gui.dock.action.view.ActionViewConverter;
import bibliothek.gui.dock.action.view.ViewGenerator;
import bibliothek.gui.dock.common.action.CPanelPopup;
import bibliothek.gui.dock.common.action.CPanelPopup.PanelPopup;
import bibliothek.gui.dock.themes.basic.action.menu.MenuViewItem;

import javax.swing.*;

/**
 * A generator for creating menus for a {@link CPanelPopup}.
 *
 * @author Benjamin Sigg
 */
public class PanelMenuGenerator implements ViewGenerator<PanelPopup, MenuViewItem<JComponent>> {

    public MenuViewItem<JComponent> create(ActionViewConverter converter, PanelPopup action, Dockable dockable) {
        return switch (action.getAction().getMenuBehavior()) {
            case HIDE -> null;
            case DECORATED_DIALOG, UNDECORATED_DIALOG -> new BasicPanelPopupMenuItemHandler(action, dockable);
            case SUBMENU -> new BasicPanelPopupMenuHandler(action, dockable);
        };
    }
}
