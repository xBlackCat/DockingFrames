/*
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 *
 * Copyright (C) 2010 Benjamin Sigg
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
package bibliothek.gui.dock.common.action.predefined;

import bibliothek.gui.dock.common.CControl;
import bibliothek.gui.dock.common.intern.CDockable;
import bibliothek.gui.dock.common.intern.action.CExtendedModeAction;
import bibliothek.gui.dock.common.mode.CLocationModeManager;
import bibliothek.gui.dock.common.mode.ExtendedMode;

/**
 * An action that un-externalizes (=normalizes) each {@link CDockable} to which it is added.
 * This action does the same as the {@link CNormalizeAction}, it just has another icon
 * and title for the user.
 *
 * @author Benjamin Sigg
 */
public class CUnexternalizeAction extends CExtendedModeAction {
    /**
     * Creates a new action
     *
     * @param control the control for which this action will be used
     */
    public CUnexternalizeAction(CControl control) {
        super(control, ExtendedMode.NORMALIZED, CLocationModeManager.ICON_MANAGER_KEY_UNEXTERNALIZE, "externalize" +
                ".out", "externalize.out.tooltip", CControl.KEY_GOTO_NORMALIZED);
    }
}
