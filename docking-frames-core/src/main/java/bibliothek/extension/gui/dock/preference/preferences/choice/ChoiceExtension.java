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
package bibliothek.extension.gui.dock.preference.preferences.choice;

import bibliothek.gui.dock.util.TextManager;
import bibliothek.util.Path;

/**
 * An extension to a {@link Choice}, adding more options to the choice.
 *
 * @param <V> the kind of objects this choice offers
 * @author Benjamin Sigg
 */
public interface ChoiceExtension<V> {
    /**
     * The name of this extension
     */
    Path CHOICE_EXTENSION = new Path("dock.choice");

    /**
     * The parameter containing the choise that is extended
     */
    String CHOICE_PARAMETER = "choice";

    /**
     * Gets the number of available choices.
     *
     * @return the number of choices
     */
    int size();

    /**
     * Gets a name for the <code>index</code>'th choice.
     *
     * @param index the index of the choice
     * @return the name of that choice, should be human readable
     */
    String getText(int index);

    /**
     * Tells whether {@link #getText(int)} for index <code>index</code> returns
     * a key for the {@link TextManager} or is just ordinary text.
     *
     * @param index the index of the item
     * @return whether the text is a key or not
     */
    boolean isTextKey(int index);

    /**
     * Gets a unique identifier for the <code>index</code>'th choice.
     *
     * @param index the index of the choice
     * @return the unique identifier
     */
    String getId(int index);

    /**
     * Gets the <code>index</code>'th value of this choice.
     *
     * @param index the index of the choice
     * @return the value of the choice
     */
    V getChoice(int index);

    /**
     * Overrides the standard choice.
     *
     * @return the identifier of the standard choice, <code>null</code> indicates that this
     * extension does not override the default choices
     */
    String getDefaultChoice();
}
