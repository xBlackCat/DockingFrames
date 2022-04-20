/*
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 *
 * Copyright (C) 2013 Benjamin Sigg
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
package bibliothek.gui.dock.component;

import bibliothek.util.Filter;
import bibliothek.util.container.Tuple;

import java.util.*;

/**
 * Default implementation of {@link DockComponentManager}.
 *
 * @author Benjamin Sigg
 */
public class DefaultDockComponentManager implements DockComponentManager {
    /**
     * default configuration, to be applied if there is no other configuration found
     */
    private DockComponentConfiguration defaultConfiguration;

    /**
     * all the configurations, in the order in which they are to be applied
     */
    private final List<Tuple<Filter<DockComponentRoot>, DockComponentConfiguration>> configurations =
            new ArrayList<>();

    /**
     * all the registered roots
     */
    private final Map<DockComponentRoot, Handle> roots = new HashMap<>();

    public void setDefaultConfiguration(DockComponentConfiguration configuration) {
        this.defaultConfiguration = configuration;
        reconfigure();
    }

    public void addConfiguration(Filter<DockComponentRoot> filter, DockComponentConfiguration configuration) {
        configurations.add(Tuple.of(filter, configuration));
        reconfigure();
    }

    public void removeConfiguration(DockComponentConfiguration configuration) {
        Iterator<Tuple<Filter<DockComponentRoot>, DockComponentConfiguration>> iterator = configurations.iterator();

        while (iterator.hasNext()) {
            if (iterator.next().getB() == configuration) {
                iterator.remove();
                reconfigure();
                return;
            }
        }
    }

    private void reconfigure() {
        for (Handle handle : roots.values()) {
            handle.reconfigure();
        }
    }

    public void register(DockComponentRoot root) {
        if (!roots.containsKey(root)) {
            Handle handle = new Handle(root);
            roots.put(root, handle);
            handle.reconfigure();
        }
    }

    public void unregister(DockComponentRoot root) {
        Handle handle = roots.remove(root);
        if (handle != null) {
            handle.destroy();
        }
    }

    private DockComponentConfiguration getConfigurationFor(DockComponentRoot root) {
        for (Tuple<Filter<DockComponentRoot>, DockComponentConfiguration> config : configurations) {
            if (config.getA().includes(root)) {
                return config.getB();
            }
        }
        return defaultConfiguration;
    }

    /**
     * Represents one registered {@link DockComponentRoot} of a {@link DefaultDockComponentManager}
     *
     * @author Benjamin Sigg
     */
    private class Handle {
        private final DockComponentRoot root;

        public Handle(DockComponentRoot root) {
            this.root = root;
        }

        /**
         * Finds out what the best configuration for {@link #root} is, and applies that configuration
         */
        public void reconfigure() {
            DockComponentConfiguration preferred = getConfigurationFor(root);
            DockComponentConfiguration configuration = root.getComponentConfiguration();

            if (preferred != configuration) {
                root.setComponentConfiguration(preferred);
            }
        }

        /***
         * Removes the configuration from {@link #root}
         */
        public void destroy() {
            root.setComponentConfiguration(null);
        }
    }
}
