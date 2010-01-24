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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bibliothek.extension.gui.dock.util.Path;
import bibliothek.gui.DockController;
import bibliothek.gui.DockStation;
import bibliothek.gui.Dockable;
import bibliothek.gui.dock.common.CControl;
import bibliothek.gui.dock.common.mode.ExtendedMode;
import bibliothek.gui.dock.control.DockRegister;
import bibliothek.gui.dock.event.DockHierarchyEvent;
import bibliothek.gui.dock.event.DockHierarchyListener;
import bibliothek.gui.dock.event.DockRegisterAdapter;
import bibliothek.gui.dock.event.DockRelocatorAdapter;
import bibliothek.gui.dock.facile.mode.status.DefaultExtendedModeEnablement;
import bibliothek.gui.dock.facile.mode.status.ExtendedModeEnablement;
import bibliothek.gui.dock.facile.mode.status.ExtendedModeEnablementFactory;
import bibliothek.gui.dock.support.mode.ModeManager;
import bibliothek.gui.dock.support.mode.ModeManagerListener;
import bibliothek.gui.dock.support.util.Resources;
import bibliothek.gui.dock.util.DockProperties;
import bibliothek.gui.dock.util.IconManager;
import bibliothek.gui.dock.util.PropertyKey;
import bibliothek.gui.dock.util.PropertyValue;
import bibliothek.gui.dock.util.property.ConstantPropertyFactory;

/**
 * {@link ModeManager} for the location of a {@link Dockable}. This manager is able to
 * work together with {@link CControl} or together with {@link DockController}. Clients
 * using it together with a {@link DockController} need to set icons for the 
 * modes manually, they can use the {@link IconManager} and the keys provided in
 * each mode (e.g. {@link NormalMode#ICON_IDENTIFIER}).
 * @author Benjamin Sigg
 */
public class LocationModeManager<M extends LocationMode> extends ModeManager<Location, M>{
    /** the key used for the {@link IconManager} to read the {@link javax.swing.Icon} for the "minimize"-action */
    public static final String ICON_MANAGER_KEY_MINIMIZE = "locationmanager.minimize";
    /** the key used for the {@link IconManager} to read the {@link javax.swing.Icon} for the "maximize"-action */
    public static final String ICON_MANAGER_KEY_MAXIMIZE = "locationmanager.maximize";
    /** the key used for the {@link IconManager} to read the {@link javax.swing.Icon} for the "normalize"-action */
    public static final String ICON_MANAGER_KEY_NORMALIZE = "locationmanager.normalize";
    /** the key used for the {@link IconManager} to read the {@link javax.swing.Icon} for the "externalize"-action */
    public static final String ICON_MANAGER_KEY_EXTERNALIZE = "locationmanager.externalize";
    
	/**
	 * {@link PropertyKey} for the {@link ExtendedModeEnablement} that should be used
	 * by a {@link LocationModeManager} to activate and deactivate the modes.
	 */
	public static final PropertyKey<ExtendedModeEnablementFactory> MODE_ENABLEMENT = 
		new PropertyKey<ExtendedModeEnablementFactory>( "locationmodemanager.mode_enablement", 
				new ConstantPropertyFactory<ExtendedModeEnablementFactory>( DefaultExtendedModeEnablement.FACTORY ), true  );
	
	/** a set of listeners that will be automatically added or removed from a {@link LocationMode} */
	private Map<Path, List<LocationModeListener>> listeners = new HashMap<Path, List<LocationModeListener>>();
	
	/** registers new dockables */
	private RegisterListener registerListener = new RegisterListener();
	
	/** registers when dockables change their position */
	private HierarchyListener hierarchyListener = new HierarchyListener();
	
	/** registers dragged and dropped dockables */
	private RelocatorListener relocatorListener = new RelocatorListener();
	
	/** the current {@link ExtendedModeEnablementFactory} */
	private PropertyValue<ExtendedModeEnablementFactory> extendedModeFactory = new PropertyValue<ExtendedModeEnablementFactory>( MODE_ENABLEMENT ) {
		@Override
		protected void valueChanged( ExtendedModeEnablementFactory oldValue, ExtendedModeEnablementFactory newValue ){
			updateEnablement();
		}
	};
	
	/** tells which modes are available for which element */
	private ExtendedModeEnablement enablement;

	/**
	 * Creates a new manager.
	 * @param controller the controller in whose realm this manager will work
	 */
	public LocationModeManager( DockController controller ){
		super( controller );
		registerListener.connect( controller );
		controller.getRelocator().addDockRelocatorListener( relocatorListener );
		
		updateEnablement();
		extendedModeFactory.setProperties( controller );
		
		addModeManagerListener( new LocationModeListenerAdapter() );
		
        IconManager icons = controller.getIcons();
        icons.setIconDefault( ICON_MANAGER_KEY_MAXIMIZE, Resources.getIcon( "maximize" ) );
        icons.setIconDefault( ICON_MANAGER_KEY_MINIMIZE, Resources.getIcon( "minimize" ) );
        icons.setIconDefault( ICON_MANAGER_KEY_NORMALIZE, Resources.getIcon( "normalize" ) );
        icons.setIconDefault( ICON_MANAGER_KEY_EXTERNALIZE, Resources.getIcon( "externalize" ) );
	}
	
	public void destroy(){
		registerListener.connect( null );
		getController().getRelocator().removeDockRelocatorListener( relocatorListener );
		
		for( LocationMode mode : this.modes() ){
			mode.setController( null );
		}
		
		super.destroy();
		extendedModeFactory.setProperties( (DockProperties)null );
	}
	
	/**
	 * Updates the current {@link ExtendedModeEnablement} using the factory
	 * provided by {@link #MODE_ENABLEMENT}.
	 */
	protected void updateEnablement(){
		if( enablement != null ){
			enablement.destroy();
			enablement = null;
		}
		if( getController() != null ){
			enablement = extendedModeFactory.getValue().create( this );
		}
		rebuildAll();
	}
	
	/**
	 * Sets the current mode of <code>dockable</code>.
	 * @param dockable the dockable whose mode is to be set
	 * @param extendedMode the mode
	 * @throws IllegalArgumentException if <code>extendedMode</code> is unknown
	 */
	public void setMode( Dockable dockable, ExtendedMode extendedMode ){
		M mode = getMode( extendedMode.getModeIdentifier() );
		if( mode == null )
			throw new IllegalArgumentException( "No mode '" + extendedMode.getModeIdentifier() + "' available" );

		apply( dockable, mode, false );
	}

	/**
	 * Gets the current mode of <code>dockable</code>.
	 * @param dockable the element whose mode is searched
	 * @return the mode or <code>null</code> if not found
	 */
	public ExtendedMode getMode( Dockable dockable ){
		LocationMode mode = getCurrentMode( dockable );
		if( mode == null )
			return null;
		return mode.getExtendedMode();
	}
	
	/**
	 * Ignores the call, the position of {@link Dockable}s is set elsewhere.
	 */
	@Override
	protected void applyDuringRead( String key, Path old, Path current, Dockable dockable ){
		// ignore
	}
	
	/**
	 * Using the current {@link ExtendedModeEnablement} this method tells whether
	 * mode <code>mode</code> can be applied to <code>dockable</code>.
	 * @param dockable some element, not <code>null</code>
	 * @param mode some mode, not <code>null</code>
	 * @return the result of {@link ExtendedModeEnablement#isAvailable(Dockable, ExtendedMode)}
	 */
	public boolean isModeAvailable( Dockable dockable, ExtendedMode mode ){
		if( enablement == null )
			return false;
		
		return enablement.isAvailable( dockable, mode );
	}
	
	/**
	 * Adds a listener to the mode with unique identifier <code>identifier</code>. If the
	 * mode is exchanged then this listener is automatically removed and may be re-added
	 * to the new mode.
	 * @param identifier the identifier of some mode (not necessarily registered yet).
	 * @param listener the new listener, not <code>null</code>
	 */
	public void addListener( Path identifier, LocationModeListener listener ){
		if( listener == null )
			throw new IllegalArgumentException( "listener must not be null" );
		
		List<LocationModeListener> list = listeners.get( identifier );
		if( list == null ){
			list = new ArrayList<LocationModeListener>();
			listeners.put( identifier, list );
		}
		list.add( listener );
		
		LocationMode mode = getMode( identifier );
		if( mode != null ){
			mode.addLocationModeListener( listener );
		}
	}
	
	/**
	 * Removes a listener from the mode <code>identifier</code>.
	 * @param identifier the name of a mode
	 * @param listener the listener to remove
	 */
	public void removeListener( Path identifier, LocationModeListener listener ){
		List<LocationModeListener> list = listeners.get( identifier );
		if( list == null )
			return;
		
		list.remove( listener );
		if( list.isEmpty() )
			listeners.remove( identifier );
		
		LocationMode mode = getMode( identifier );
		if( mode != null ){
			mode.removeLocationModeListener( listener );
		}
	}
	
	@Override
	public M getCurrentMode( Dockable dockable ){
		while( dockable != null ){
			for( M mode : modes() ){
				if( mode.isCurrentMode( dockable ))
					return mode;
			}
			DockStation station = dockable.getDockParent();
			dockable = station == null ? null : station.asDockable();
		}
		
		return null;
	}
	
    /**
     * Ensures that <code>dockable</code> is not hidden behind another 
     * {@link Dockable}. That does not mean that <code>dockable</code> becomes
     * visible, just that it is easier reachable without the need to change
     * modes of any <code>Dockable</code>s.  
     * @param dockable the element which should not be hidden
     */
    public void ensureNotHidden( final Dockable dockable ){
    	runTransaction( new Runnable() {
			public void run(){
		    	for( LocationMode mode : modes() ){
		    		mode.ensureNotHidden( dockable );
		    	}	
			}
		});
    }
    
    /**
     * Empty method evaluating the correct location of a {@link Dockable}. To be
     * overridden by subclasses to handle elements which have additional restrictions.
     * @param dockable the element to check
     */
    public void ensureValidLocation( Dockable dockable ){
    	// nothing
    }
	
	/**
	 * Adds and removes listeners from {@link LocationMode}s according to the map
	 * {@link LocationModeManager#listeners}.
	 * @author Benjamin Sigg
	 */
	private class LocationModeListenerAdapter implements ModeManagerListener<Location, LocationMode>{
		public void modeAdded(	ModeManager<? extends Location, ? extends LocationMode> manager, LocationMode mode ){
			mode.setManager( LocationModeManager.this );
			mode.setController( getController() );
			
			List<LocationModeListener> list = listeners.get( mode.getUniqueIdentifier() );
			if( list != null ){
				for( LocationModeListener listener : list ){
					mode.addLocationModeListener( listener );
				}
			}
		}
		
		public void modeRemoved( ModeManager<? extends Location, ? extends LocationMode> manager, LocationMode mode ){
			mode.setManager( null );
			mode.setController( null );
			
			List<LocationModeListener> list = listeners.get( mode.getUniqueIdentifier() );
			if( list != null ){
				for( LocationModeListener listener : list ){
					mode.removeLocationModeListener( listener );
				}
			}
		}
		
		public void dockableAdded( ModeManager<? extends Location, ? extends LocationMode> manager, Dockable dockable ){
			// ignore
		}

		public void dockableRemoved( ModeManager<? extends Location, ? extends LocationMode> manager, Dockable dockable ){
			// ignore
		}

		public void modeChanged( ModeManager<? extends Location, ? extends LocationMode> manager, Dockable dockable, LocationMode oldMode, LocationMode newMode ){
			// ignore	
		}		
	}

	/**
	 * This listener registers when {@link Dockable}s enter and leave and adds or
	 * removes a {@link DockHierarchyListener}. 
	 * @author Benjamin Sigg
	 */
	private class RegisterListener extends DockRegisterAdapter{
		private DockController controller;
		
		public void connect( DockController controller ){
			if( this.controller != null ){
				DockRegister register = this.controller.getRegister();
				register.removeDockRegisterListener( this );
				for( Dockable dockable : register.listDockables() ){
					dockable.removeDockHierarchyListener( hierarchyListener );
					rebuild( dockable );
				}
			}
			this.controller = controller;
			if( controller != null ){
				DockRegister register = controller.getRegister();
				register.addDockRegisterListener( this );
				for( Dockable dockable : register.listDockables() ){
					dockable.addDockHierarchyListener( hierarchyListener );
				}
			}
		}
		
		@Override
		public void dockableRegistered( DockController controller, Dockable dockable ){
			dockable.addDockHierarchyListener( hierarchyListener );
			rebuild( dockable );
		}
		
		@Override
		public void dockableUnregistered( DockController controller, Dockable dockable ){
			dockable.removeDockHierarchyListener( hierarchyListener );
		}
	}
	
	/**
	 * Reacts on dockables that are changing their position by calling
	 * {@link LocationModeManager#rebuild(Dockable)}.
	 * @author Benjamin Sigg
	 */
	private class HierarchyListener implements DockHierarchyListener{
		public void controllerChanged( DockHierarchyEvent event ){
			// ignore
		}

		public void hierarchyChanged( DockHierarchyEvent event ){
            if( !isOnTransaction() ){
                refresh( event.getDockable(), true );
            }	
		}		
	}
	
	/**
	 * Detects the drag-operation and calls {@link LocationModeManager#store(Dockable)}.
	 * @author Benjamin Sigg
	 */
	private class RelocatorListener extends DockRelocatorAdapter{
		@Override
        public void drag( DockController controller, Dockable dockable, DockStation station ) {
			store( dockable );
        }
	}
}
