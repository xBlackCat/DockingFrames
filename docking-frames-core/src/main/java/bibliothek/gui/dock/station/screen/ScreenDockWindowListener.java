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
package bibliothek.gui.dock.station.screen;

/**
 * A listener that can be added to a {@link ScreenDockWindow}.
 *
 * @author Benjamin
 */
public interface ScreenDockWindowListener {
    /**
     * This method is called after the {@link ScreenDockWindow#isVisible() visibility state} changed.
     *
     * @param window the caller
     */
    void visibilityChanged(ScreenDockWindow window);

    /**
     * This method is called after the windows fullscreen-state changed.
     *
     * @param window the caller
     */
    void fullscreenStateChanged(ScreenDockWindow window);

    /**
     * This method is called after the size and position of <code>window</code> changed.
     *
     * @param window the caller
     */
    void shapeChanged(ScreenDockWindow window);

    /**
     * Called if the user wants to close the window (e.g. by pressing alt+F4, or clicking on the "x" button)
     *
     * @param window the source of the event
     */
    void windowClosing(ScreenDockWindow window);
}
