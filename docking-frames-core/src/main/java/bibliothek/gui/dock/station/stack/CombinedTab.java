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

package bibliothek.gui.dock.station.stack;

import bibliothek.gui.dock.DockElementRepresentative;
import bibliothek.gui.dock.station.stack.tab.Tab;
import bibliothek.gui.dock.station.stack.tab.TabConfiguration;

import javax.swing.*;
import java.awt.*;

/**
 * A tab of a {@link CombinedStackDockComponent}. Every tab represents one
 * Component which can be selected.
 *
 * @author Benjamin Sigg
 */
public interface CombinedTab extends DockElementRepresentative, Tab {
    /**
     * Gets the component which paints and represents this tab.
     */
    Component getComponent();

    /**
     * Sets the text of this tab.
     *
     * @param text the text to display
     */
    void setText(String text);

    /**
     * Sets the image of this tab.
     *
     * @param icon an icon that should be shown, can be <code>null</code>
     */
    void setIcon(Icon icon);

    /**
     * Sets the tooltip of this tab.
     *
     * @param tooltip the tooltip text, can be <code>null</code>
     */
    void setTooltip(String tooltip);

    /**
     * Enables or disables this tab. A disabled should not react to any mouse input, and it
     * should be visually distinct from enabled tabs.
     *
     * @param enabled whether to enable or disable this tab
     */
    void setEnabled(boolean enabled);

    /**
     * Fine tunes this tab.
     *
     * @param configuration the new configuration to use
     * @see CombinedStackDockComponent#getConfiguration(bibliothek.gui.Dockable)
     */
    void setConfiguration(TabConfiguration configuration);
}
