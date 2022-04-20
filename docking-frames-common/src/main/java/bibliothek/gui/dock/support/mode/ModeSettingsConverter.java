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
package bibliothek.gui.dock.support.mode;

import bibliothek.util.xml.XElement;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * A converter converts properties from the outside to the inner world. It is used by the {@link ModeSettings}
 * to store data in memory, a converter should ensure that the data does not reference any object from the
 * application (such that garbage collection works).
 *
 * @param <A> the type of properties outside
 * @param <B> the type of properties that represent the outside properties
 * @author Benjamin Sigg
 */
public interface ModeSettingsConverter<A, B> {
    /**
     * Converts a property from the outside world into the world of
     * this setting.
     *
     * @param b a property from outside
     * @return a property from inside
     */
    A convertToWorld(B b);

    /**
     * Converts a property from the inside world into the world outside.
     *
     * @param a the property from inside
     * @return a property from outside
     */
    B convertToSetting(A a);

    /**
     * Writes a single property of this setting.
     *
     * @param b   the property to write
     * @param out the stream to write into
     * @throws IOException if an I/O-error occurs
     */
    void writeProperty(B b, DataOutputStream out) throws IOException;

    /**
     * Reads a single property.
     *
     * @param in the stream to read from
     * @return the property that has been read.
     * @throws IOException if an I/O-error occurs
     */
    B readProperty(DataInputStream in) throws IOException;

    /**
     * Writes a single property as xml element.
     *
     * @param b       the property to write
     * @param element the element to write into, the attributes of the
     *                element must not be changed
     */
    void writePropertyXML(B b, XElement element);

    /**
     * Reads a single property.
     *
     * @param element the element to read the property from
     * @return the new property
     */
    B readPropertyXML(XElement element);
}
