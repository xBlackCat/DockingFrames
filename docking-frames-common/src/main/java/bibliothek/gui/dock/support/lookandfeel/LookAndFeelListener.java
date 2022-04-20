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
package bibliothek.gui.dock.support.lookandfeel;

import bibliothek.gui.dock.support.lookandfeel.LookAndFeelList.Info;

import javax.swing.*;

/**
 * A listener which observes a {@link LookAndFeelList} and gets informed whenever
 * the contents of the list change.
 *
 * @author Benjamin Sigg
 */
public interface LookAndFeelListener {
    /**
     * Called when the current {@link LookAndFeel} was exchanged.
     *
     * @param list        the source of the event
     * @param lookAndFeel the new current LookAndFeel
     */
    void lookAndFeelChanged(LookAndFeelList list, Info lookAndFeel);

    /**
     * Called when a selectable {@link LookAndFeel} was added to <code>list</code>.
     *
     * @param list        the source of the event
     * @param lookAndFeel the new LookAndFeel
     */
    void lookAndFeelAdded(LookAndFeelList list, Info lookAndFeel);

    /**
     * Called when a selectable {@link LookAndFeel} was removed from <code>list</code>.
     *
     * @param list        the source of the event
     * @param lookAndFeel the removed LookAndFeel
     */
    void lookAndFeelRemoved(LookAndFeelList list, Info lookAndFeel);

    /**
     * Called when the default- {@link LookAndFeel} was exchanged.
     *
     * @param list        the source of the event
     * @param lookAndFeel the new LookAndFeel
     */
    void defaultLookAndFeelChanged(LookAndFeelList list, Info lookAndFeel);

    /**
     * Called when the system-{@link LookAndFeel} was exchanged.
     *
     * @param list        the source of the event
     * @param lookAndFeel the new LookAndFeel
     */
    void systemLookAndFeelChanged(LookAndFeelList list, Info lookAndFeel);
}
