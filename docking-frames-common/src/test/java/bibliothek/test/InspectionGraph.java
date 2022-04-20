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
package bibliothek.test;

import bibliothek.test.inspect.NullInspectable;
import bibliothek.test.inspect.ObjectInspectable;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

/**
 * A graph built by the contents of an {@link Inspect}.
 *
 * @author Benjamin Sigg
 */
public class InspectionGraph {
    private int id = 0;

    private final Map<Class<?>, Adapter<?, Inspectable>> inspectableAdapters = new HashMap<>();
    private final Map<Class<?>, Adapter<?, String>> stringAdapters = new HashMap<>();

    private final Map<Object, Inspectable> inspectables = new IdentityHashMap<>();
    private final Map<Inspectable, Inspect> inspects = new IdentityHashMap<>();
    private final Map<Inspect, InspectionNode> nodes = new IdentityHashMap<>();

    public InspectionGraph() {
        putInspectableAdapter(Inspectable.class, value -> value);

        putInspectableAdapter(Object.class, ObjectInspectable.class);

        putStringAdapter(Object.class, String::valueOf);
    }

    public void updateAll() {
        InspectionNode[] update = nodes.values().toArray(new InspectionNode[0]);

        for (InspectionNode node : update) {
            node.update();
        }
    }

    public void retainAll(Set<InspectionNode> usedNodes) {
        nodes.values().removeIf(inspectionNode -> !usedNodes.contains(inspectionNode));

        inspects.values().removeIf(inspect -> !nodes.containsKey(inspect));

        inspectables.values().removeIf(inspectable -> !inspects.containsKey(inspectable));
    }

    public <C> void putInspectableAdapter(Class<C> clazz, Adapter<C, Inspectable> adapter) {
        inspectableAdapters.put(clazz, adapter);
    }

    public <C> void putInspectableAdapter(Class<C> source, Class<? extends Inspectable> destination) {
        putInspectableAdapter(source, new ReflectionAdapter<>(source, destination));
    }

    public <C> void putStringAdapter(Class<C> clazz, Adapter<C, String> adapter) {
        stringAdapters.put(clazz, adapter);
    }

    private <X> X get(Class<?> clazz, Map<Class<?>, X> adapters) {
        while (clazz != null) {
            X adapter = adapters.get(clazz);
            if (adapter != null) {
                return adapter;
            }

            for (Class<?> interfaze : clazz.getInterfaces()) {
                adapter = adapters.get(interfaze);
                if (adapter != null) {
                    return adapter;
                }
            }

            clazz = clazz.getSuperclass();
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public Inspectable getInspectable(Object object) {
        if (object == null) {
            return NullInspectable.INSTANCE;
        }

        Inspectable result = inspectables.get(object);
        if (result == null) {
            Adapter<?, Inspectable> adapter = get(object.getClass(), inspectableAdapters);
            result = ((Adapter<Object, Inspectable>) adapter).adapt(object);
            inspectables.put(object, result);
        }
        return result;
    }

    public Inspect getInspect(Inspectable inspectable) {
        Inspect inspect = inspects.get(inspectable);
        if (inspect == null) {
            inspect = inspectable.inspect(this);
            inspects.put(inspectable, inspect);
        }
        return inspect;
    }

    public InspectionNode getNode(Inspect inspect) {
        InspectionNode node = nodes.get(inspect);
        if (node == null) {
            node = new InspectionNode(this, inspect, id++);
            nodes.put(inspect, node);
            node.update();
        }
        return node;
    }

    public InspectionNode getNode(Object value) {
        return getNode(getInspect(getInspectable(value)));
    }

    @SuppressWarnings("unchecked")
    public String toString(Object object) {
        if (object == null) {
            return "null";
        }
        Adapter<?, String> adapter = get(object.getClass(), stringAdapters);
        return ((Adapter<Object, String>) adapter).adapt(object);
    }
}
