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
package bibliothek.gui.dock.extension.css.doc;

import bibliothek.gui.dock.extension.css.CssNode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Describes a single element of a {@link CssDocPath}. See also {@link CssNode}.
 *
 * @author Benjamin Sigg
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CssDocPathNode {
    /**
     * The name of this node (as required for a css selector)
     *
     * @return the name
     */
    CssDocKey name() default @CssDocKey();

    /**
     * The identifier of this node (as required for a css selector)
     *
     * @return the identifier
     */
    CssDocText identifier() default @CssDocText();

    /**
     * All the classes of this node (as required for a css selector)
     *
     * @return the classes
     */
    CssDocKey[] classes() default @CssDocKey();

    /**
     * All the pseudo classes of this node (as required for a css selector)
     *
     * @return the pseudo classes
     */
    CssDocKey[] pseudoClasses() default @CssDocKey();

    /**
     * All the properties of this node (as required for a css selector)
     *
     * @return the properties
     */
    CssDocKey[] properties() default @CssDocKey();

    /**
     * A description of this node.
     *
     * @return the description
     */
    CssDocText description() default @CssDocText();

    /**
     * Will replace <code>this</code> with the node(s) found in the referenced class.
     *
     * @return replacement of <code>this</code>
     */
    Class<?> reference() default Object.class;
}
