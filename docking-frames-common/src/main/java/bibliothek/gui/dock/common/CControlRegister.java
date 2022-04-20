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
package bibliothek.gui.dock.common;

import bibliothek.gui.dock.common.intern.CDockable;
import bibliothek.gui.dock.common.mode.ExtendedMode;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Grants access to the {@link CDockable}s and other information that is
 * associated with a {@link CControl}.
 *
 * @author Benjamin Sigg
 */
public interface CControlRegister {
    /**
     * Gets the control for which this register is used.
     *
     * @return the control
     */
    CControl getControl();

    /**
     * Gets the number of {@link CDockable}s that are registered.
     *
     * @return the number of dockables
     */
    int getDockableCount();

    /**
     * Gets the index'th dockable that is registered
     *
     * @param index the index of the element
     * @return the selected dockable
     */
    CDockable getDockable(int index);

    /**
     * Gets an unmodifiable list of all {@link CDockable}s which are known to
     * this register. The list might or might not be updated when the contents
     * of this register changes.
     *
     * @return an unmodifiable list of elements
     */
    List<CDockable> getDockables();

    /**
     * Gets an unmodifiable list of all {@link SingleCDockable}s which are known to
     * this register. The list might or might not be updated when the contents
     * of this register changes.
     *
     * @return an unmodifiable list of elements
     */
    List<SingleCDockable> getSingleDockables();

    /**
     * Gets an unmodifiable list of all {@link MultipleCDockable}s which are known to
     * this register. The list might or might not be updated when the contents
     * of this register change.
     *
     * @return an unmodifiable list of elements
     */
    List<MultipleCDockable> getMultipleDockables();

    /**
     * Gets an unmodifiable list of all {@link CStation}s which are known to
     * this register. The list might or might not be updated when the content
     * of this register changes.
     *
     * @return an unmodifiable list of elements
     */
    List<CStation<?>> getStations();

    /**
     * Gets an unmodifiable map of all {@link MultipleCDockableFactory}s that
     * are known to this register. The map may or may not be updated when the
     * content of this register changes.
     *
     * @return the unmodifiable map of factories
     */
    Map<String, MultipleCDockableFactory<?, ?>> getFactories();

    /**
     * Searches the factory with identifier <code>id</code>.
     *
     * @param id the id of the factory
     * @return the factory or <code>null</code>
     */
    MultipleCDockableFactory<?, ?> getFactory(String id);

    /**
     * Gets a list of keys for all {@link SingleCDockableFactory}s which
     * are currently registered.
     *
     * @return the list of keys
     */
    Set<String> listSingleBackupFactories();

    /**
     * Gets a list of identifiers of all {@link MultipleCDockableFactory}s
     * which are currently registered.
     *
     * @return the list of factories
     */
    Set<String> listMultipleDockableFactories();

    /**
     * Gets a list of all {@link MultipleCDockable}s that are registered at this
     * control and whose {@link MultipleCDockable#getFactory()} method returns
     * <code>factory</code>.
     *
     * @param factory the factory to look out for
     * @return the list of dockables, never <code>null</code> but might be empty
     */
    List<MultipleCDockable> listMultipleDockables(MultipleCDockableFactory<?, ?> factory);

    /**
     * Gets a list of all visible {@link CDockable}s in the given mode.
     *
     * @param mode the mode which each <code>CDockable</code> must have
     * @return the list of <code>CDockable</code>s
     */
    List<CDockable> listDockablesInMode(ExtendedMode mode);

    /**
     * Gets a list of all identifiers of {@link SingleCDockable} for which
     * the control has location information within the current {@link CControl#load(String) setting}.
     *
     * @return the list of ids, never <code>null</code>
     */
    Set<String> listSingleDockables();

    /**
     * Gets an unmodifiable list of all {@link CStationContainer}s known
     * to this register. The list might or might not be updated when the contents
     * of this register change.
     *
     * @return the unmodifiable list
     */
    List<CStationContainer> getStationContainers();

    /**
     * Searches the {@link CStationContainer} which contains <code>child</code>.
     *
     * @param child the child whose parent is searched
     * @return the parent of <code>child</code> or <code>null</code>
     */
    CStationContainer getContainer(CStation<?> child);

    /**
     * Transforms an identifier to an identifier for a {@link SingleCDockable}.<br>
     * Note that this method must never create an identifier that passes
     * {@link #isMultiId(String)}.
     *
     * @param id some identifier
     * @return an identifier marked as being for a {@link SingleCDockable}
     */
    String toSingleId(String id);

    /**
     * Checks whether <code>id</code> could be created by {@link #toSingleId(String)}.
     *
     * @param id the id to check
     * @return <code>true</code> if there is an input for {@link #toSingleId(String)}
     * that would result in <code>id</code>
     */
    boolean isSingleId(String id);

    /**
     * Undoes the changes of {@link #toSingleId(String)}. It must be <code>true</code>
     * that <code>singleToNormalId( toSingleId( id )) = id</code>. The behavior
     * of this method is unspecified if {@link #isSingleId(String)} returns
     * <code>false</code> for <code>id</code>.
     *
     * @param id some id create by {@link #toSingleId(String)}.
     * @return the original id
     */
    String singleToNormalId(String id);

    /**
     * Transforms an identifier to an identifier for a {@link MultipleCDockable}.<br>
     * Note that this method must never create an identifier that passes
     * {@link #isSingleId(String)}.
     *
     * @param id some identifier
     * @return an identifier marked as being for a {@link MultipleCDockable}
     */
    String toMultiId(String id);

    /**
     * Checks whether <code>id</code> could be created by {@link #toMultiId(String)}.
     *
     * @param id the id to check
     * @return <code>true</code> if there is an input for {@link #toMultiId(String)}
     * that would result in <code>id</code>
     */
    boolean isMultiId(String id);

    /**
     * Undoes the changes of {@link #toMultiId(String)}. It must be <code>true</code>
     * that <code>multiToNormalId( toMultiId( id )) = id</code>. The behavior
     * of this method is unspecified if {@link #isMultiId(String)} returns
     * <code>false</code> for <code>id</code>.
     *
     * @param id some id create by {@link #toMultiId(String)}.
     * @return the original id
     */
    String multiToNormalId(String id);
}
