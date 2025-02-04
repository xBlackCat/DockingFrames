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
package bibliothek.util;

import bibliothek.gui.DockController;
import bibliothek.util.workarounds.Java9Workaround;
import bibliothek.util.workarounds.Workaround;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class providing help for bugs or specialities present in some versions of the JRE or
 * in some libraries. {@link Workarounds} is implemented as singleton, as there is only one JRE and
 * usually libraries are not loaded multiple times as well.
 *
 * @author Benjamin Sigg
 */
public class Workarounds {
    private static Workarounds workarounds = new Workarounds();

    /**
     * all listeners
     */
    private final List<Workaround> code = new ArrayList<>(List.of(new Java9Workaround()));

    /**
     * Gets access to the currently used {@link Workarounds}s.
     *
     * @return the current workarounds
     */
    public static Workarounds getDefault() {
        return workarounds;
    }

    /**
     * Sets the {@link Workarounds} that should be used. This method will never be called from
     * the framework itself. Calling this method has no effect on workarounds that are already
     * applied.<br>
     * Please note that this method is not thread safe!<br>
     *
     * @param workarounds the new workarounds, not <code>null</code>
     */
    @ClientOnly
    public static void setDefault(Workarounds workarounds) {
        if (workarounds == null) {
            throw new IllegalArgumentException("workarounds must not be null");
        }
        Workarounds.workarounds = workarounds;
    }

    /**
     * Adds a new workaround to this {@link Workarounds}.
     *
     * @param workaround the new workaround, not <code>null</code>
     */
    public void addWorkaround(Workaround workaround) {
        code.add(workaround);
    }

    /**
     * Removes a workaround from this {@link Workarounds}.
     *
     * @param workaround the workaround to remove
     */
    public void removeWorkaround(Workaround workaround) {
        code.remove(workaround);
    }

    /**
     * Gets all the {@link Workaround}s that are currently active.
     *
     * @return all the workarounds
     */
    public Workaround[] getWorkarounds() {
        return code.toArray(new Workaround[0]);
    }

    /**
     * This method is necessary since 1.6.14, it marks a component as
     * transparent. If not marked then AWT components behind <code>component</code>
     * are not visible.
     *
     * @param component the component to mark completely transparent
     */
    public void markAsGlassPane(Component component) {
        for (Workaround listener : code.toArray(new Workaround[0])) {
            listener.markAsGlassPane(component);
        }
    }

    /**
     * Tells whether there is at least one {@link Workaround} that supports per-pixel transparency. Transparency
     * means that some pixels are visible, while others are not.
     *
     * @param window the window to test
     * @return whether transparency is supported
     */
    public boolean supportsTransparency(Window window) {
        for (Workaround listener : code.toArray(new Workaround[0])) {
            if (listener.supportsPerpixelTransparency(window)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Makes <code>window</code> transparent, any pixel not inside <code>shape</code> is not painted.
     *
     * @param window the window that should be transparent
     * @param shape  the visible part of the window, <code>null</code> if the entire window should be visible
     * @return <code>true</code> if the window was made transparent
     */
    public boolean setTransparent(Window window, Shape shape) {
        boolean result = false;

        for (Workaround listener : code.toArray(new Workaround[0])) {
            result = listener.setTransparent(window, shape) || result;
        }

        return result;
    }

    /**
     * Tells whether there is a least one {@link Workaround} that supports per-pixel translucency. Translucency
     * means that some pixels may have another alpha value than others.
     *
     * @param window the window to test
     * @return whether translucency is supported
     */
    public boolean supportsTranslucency(Window window) {
        for (Workaround listener : code.toArray(new Workaround[0])) {
            if (listener.supportsPerpixelTranslucency(window)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Makes <code>window</code> translucent, meaning that the opacity of each pixel is defined by the
     * alpha value or the {@link Color} that was used to paint over that pixel.
     *
     * @param window the window that should be translucent
     * @return <code>true</code> if the window is now translucent
     */
    public boolean setTranslucent(Window window) {
        boolean result = false;

        for (Workaround listener : code.toArray(new Workaround[0])) {
            result = listener.setTranslucent(window) || result;
        }

        return result;
    }

    /**
     * Calls the {@link Workaround#setup(DockController)} method of all installed {@link Workaround}s, in the order
     * of which the workarounds were installed.
     *
     * @param controller a newly created controller
     */
    public void setup(DockController controller) {
        for (Workaround listener : code.toArray(new Workaround[0])) {
            listener.setup(controller);
        }
    }
}
