/*
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 *
 * Copyright (C) 2008 Benjamin Sigg
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
package bibliothek.extension.gui.dock;

import bibliothek.extension.gui.dock.preference.PreferenceModel;
import bibliothek.extension.gui.dock.preference.PreferenceTreeModel;
import bibliothek.extension.gui.dock.preference.model.*;
import bibliothek.extension.gui.dock.theme.BubbleTheme;
import bibliothek.extension.gui.dock.theme.EclipseTheme;
import bibliothek.gui.DockController;
import bibliothek.gui.dock.station.flap.button.ButtonContent;
import bibliothek.util.Path;
import bibliothek.util.PathCombiner;

import javax.swing.*;

/**
 * A {@link PreferenceTreeModel} that contains all the preferences that are
 * used in this framework.
 *
 * @author Benjamin Sigg
 */
public class DockingFramesPreference extends PreferenceTreeModel {
    /**
     * Creates a new model. This constructor sets the behavior of how to
     * create paths for preferences to {@link PathCombiner#SECOND}. This
     * behavior allows reordering of models and preferences in future releases,
     * however forces any preference to have a truly unique path in a global
     * scale.
     *
     * @param controller the controller whose preferences this model should
     *                   represent
     */
    public DockingFramesPreference(DockController controller) {
        this(controller, PathCombiner.SECOND);
    }

    /**
     * Creates a new model.
     *
     * @param controller the controller whose preferences this model should
     *                   represent
     * @param combiner   how to create preference paths for nested preferences
     */
    public DockingFramesPreference(DockController controller, PathCombiner combiner) {
        super(combiner, controller);
        putLinked(new Path("shortcuts"), "preference.shortcuts",
                new KeyStrokePreferenceModel(controller.getProperties()));
        putLinked(new Path("buttonContent"), "preference.buttonContent", new ButtonContentPreferenceModel(controller));
        putLinked(new Path("layout"), "preference.layout", new LayoutPreferenceModel(controller.getProperties()));
        putLinked(new Path("theme.BubbleTheme"), "theme.bubble",
                new BubbleThemePreferenceModel(controller.getProperties()));
        putLinked(new Path("theme.EclipseTheme"), "theme.eclipse",
                new EclipseThemePreferenceModel(controller.getProperties()));
    }


    /**
     * Grants access to the preferences concerning the global {@link KeyStroke}s.
     *
     * @return the model, not <code>null</code>
     * @throws IllegalStateException if the model was removed or replaced by the client
     */
    public KeyStrokePreferenceModel getKeyStrokePreferences() {
        PreferenceModel model = getModel(new Path("shortcuts"));
        if (model instanceof KeyStrokePreferenceModel) {
            return (KeyStrokePreferenceModel) model;
        } else {
            throw new IllegalStateException("this model has been removed");
        }
    }

    /**
     * Grants access to the preferences concerning layout options like "where are the tabs placed?".
     *
     * @return the model, not <code>null</code>
     * @throws IllegalStateException if the model was removed or replaced by the client
     */
    public LayoutPreferenceModel getLayoutPreferences() {
        PreferenceModel model = getModel(new Path("layout"));
        if (model instanceof LayoutPreferenceModel) {
            return (LayoutPreferenceModel) model;
        } else {
            throw new IllegalStateException("this model has been removed");
        }
    }

    /**
     * Grants access to the preferences concerning the {@link ButtonContent}.
     *
     * @return the model, not <code>null</code>
     * @throws IllegalStateException if the model was removed or replaced by the client
     */
    public ButtonContentPreferenceModel getButtonContent() {
        PreferenceModel model = getModel(new Path("buttonContent"));
        if (model instanceof ButtonContentPreferenceModel) {
            return (ButtonContentPreferenceModel) model;
        } else {
            throw new IllegalStateException("this model has been removed");
        }
    }

    /**
     * Grants access to the preferences concerning the {@link BubbleTheme}.
     *
     * @return the model, not <code>null</code>
     * @throws IllegalStateException if the model was removed or replaced by the client
     */
    public BubbleThemePreferenceModel getBubbleThemePreferences() {
        PreferenceModel model = getModel(new Path("layout.BubbleTheme"));
        if (model instanceof BubbleThemePreferenceModel) {
            return (BubbleThemePreferenceModel) model;
        } else {
            throw new IllegalStateException("this model has been removed");
        }
    }

    /**
     * Grants access to the preferences concerning the {@link EclipseTheme}.
     *
     * @return the model, not <code>null</code>
     * @throws IllegalStateException if the model was removed or replaced by the client
     */
    public EclipseThemePreferenceModel getEclipseThemePreferences() {
        PreferenceModel model = getModel(new Path("layout.EclipseTheme"));
        if (model instanceof EclipseThemePreferenceModel) {
            return (EclipseThemePreferenceModel) model;
        } else {
            throw new IllegalStateException("this model has been removed");
        }
    }

}
