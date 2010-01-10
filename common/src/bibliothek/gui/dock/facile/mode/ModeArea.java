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
package bibliothek.gui.dock.facile.mode;

import bibliothek.gui.DockController;
import bibliothek.gui.DockStation;
import bibliothek.gui.Dockable;
import bibliothek.gui.dock.common.CStation;
import bibliothek.gui.dock.common.CWorkingArea;

/**
 * A representation of some area that can show {@link Dockable}s.
 * @author Benjamin Sigg
 */
public interface ModeArea {
	/**
	 * Gets a unique identifier for this area.
	 * @return the unique identifier
	 * @see CStation#getUniqueId()
	 */
	public String getUniqueId();
	
	/**
	 * Tells whether <code>dockable</code> is a direct child of this station.
	 * @param dockable some element
	 * @return <code>true</code> if and only if the parent of <code>dockable</code>
	 * is identical to this station
	 */
	public boolean isChild( Dockable dockable );
	
	/**
	 * Tells whether this area contains information about <code>station</code>.
	 * @param station some station which might be represented by this area
	 * @return <code>true</code> if <code>this</code> is responsible for <code>station</code> 
	 */
	public boolean isRepresentant( DockStation station );
	
	/**
	 * Tells whether children of this area have to respect the settings for
	 * {@link CWorkingArea}s.
	 * @return whether the settings are to be respected
	 */
	public boolean respectWorkingAreas();
	
	/**
	 * Connects this area with a controller. It's up to the area to
	 * add or remove listeners if necessary.
	 * @param controller the controller or <code>null</code>
	 */
	public void setController( DockController controller );
	
	/**
	 * Adds a listener to this area.
	 * @param listener the new listener
	 */
	public void addModeAreaListener( ModeAreaListener listener );
	
	/**
	 * Removes a listener from this area.
	 * @param listener the listener to remove
	 */
	public void removeModeAreaListener( ModeAreaListener listener );
}
