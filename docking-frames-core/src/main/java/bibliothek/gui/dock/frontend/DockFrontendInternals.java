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
package bibliothek.gui.dock.frontend;

import bibliothek.gui.DockFrontend;
import bibliothek.gui.DockFrontend.DockInfo;
import bibliothek.gui.DockFrontend.RootInfo;
import bibliothek.gui.Dockable;
import bibliothek.gui.dock.DockFactory;
import bibliothek.gui.dock.event.VetoableDockFrontendListener;
import bibliothek.gui.dock.layout.AdjacentDockFactory;
import bibliothek.gui.dock.layout.DockSituationIgnore;
import bibliothek.gui.dock.layout.DockablePropertyFactory;

/**
 * A set of data and methods that may be exclusively used while changing
 * the layout of a {@link DockFrontend}.
 *
 * @author Benjamin Sigg
 */
public interface DockFrontendInternals {
    /**
     * The frontend that grants this permissions.
     *
     * @return the source of this permissions
     */
    DockFrontend getFrontend();

    /**
     * Removes all child-parent relations expect the ones filtered out
     * by <code>ignore</code>.
     *
     * @param ignore a filter, never <code>null</code>
     */
    void clean(DockSituationIgnore ignore);

    /**
     * Gets information about all the roots that are registered.
     *
     * @return all the roots
     */
    RootInfo[] getRoots();

    /**
     * Gets information about all the {@link Dockable}s that are registered.
     *
     * @return all the dockables
     */
    DockInfo[] getDockables();

    /**
     * Searches for information about the Dockable with unique identifier <code>key</code>.
     *
     * @param key the name of some element
     * @return information or <code>null</code> if nothing was found
     */
    DockInfo getInfo(String key);

    /**
     * Searches information about <code>dockable</code>.
     *
     * @param dockable some dockable to search
     * @return information or <code>null</code> if nothing was found
     */
    DockInfo getInfo(Dockable dockable);

    /**
     * Gets a list of all {@link DockFactory}s that were added using {@link DockFrontend#registerFactory(DockFactory)}.
     *
     * @return all the factories
     */
    DockFactory<?, ?, ?>[] getDockFactories();

    /**
     * Gets a list of all {@link DockFactory}s that were added using
     * {@link DockFrontend#registerBackupFactory(DockFactory)}.
     *
     * @return all the factories
     */
    DockFactory<?, ?, ?>[] getBackupDockFactories();

    /**
     * Gets a list of all {@link AdjacentDockFactory}s that were added using
     * {@link DockFrontend#registerAdjacentFactory(AdjacentDockFactory)}.
     *
     * @return all the factories
     */
    AdjacentDockFactory<?>[] getAdjacentDockFactories();

    /**
     * Gets a list of all {@link AdjacentDockFactory}s that were added using
     * {@link DockFrontend#registerFactory(DockablePropertyFactory)}.
     *
     * @return all the factories
     */
    DockablePropertyFactory[] getPropertyFactories();

    /**
     * Gets information how to handle cases where information about a {@link Dockable} is found but the dockable
     * itself is missing.
     *
     * @return the strategy, not <code>null</code>
     */
    MissingDockableStrategy getMissingDockableStrategy();

    /**
     * Allows access to all the {@link VetoableDockFrontendListener}s for questioning about some operations.
     *
     * @return all the listeners that could have a veto
     */
    VetoManager getVetos();
}
