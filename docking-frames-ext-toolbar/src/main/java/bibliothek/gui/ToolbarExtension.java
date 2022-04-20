/*
 * Bibliothek - DockingFrames
 * Library built on Java/Swing, allows the user to "drag and drop"
 * panels containing any Swing-Component the developer likes to add.
 *
 * Copyright (C) 2012 Herve Guillaume, Benjamin Sigg
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
 * Herve Guillaume
 * rvguillaume@hotmail.com
 * FR - France
 *
 * Benjamin Sigg
 * benjamin_sigg@gmx.ch
 * CH - Switzerland
 */

package bibliothek.gui;

import bibliothek.gui.dock.*;
import bibliothek.gui.dock.action.ActionType;
import bibliothek.gui.dock.action.ToolbarSeparator;
import bibliothek.gui.dock.action.view.ActionViewConverter;
import bibliothek.gui.dock.action.view.ViewTarget;
import bibliothek.gui.dock.control.relocator.DefaultDockRelocator;
import bibliothek.gui.dock.control.relocator.Inserter;
import bibliothek.gui.dock.control.relocator.Merger;
import bibliothek.gui.dock.dockable.AncestorMovingImageFactory;
import bibliothek.gui.dock.dockable.DefaultDockableMovingImageFactory;
import bibliothek.gui.dock.dockable.DockableMovingImageFactory;
import bibliothek.gui.dock.event.DockRegisterAdapter;
import bibliothek.gui.dock.frontend.DefaultFrontendPerspectiveCache;
import bibliothek.gui.dock.frontend.DockFrontendExtension;
import bibliothek.gui.dock.frontend.FrontendPerspectiveCacheExtension;
import bibliothek.gui.dock.layout.DockSituation;
import bibliothek.gui.dock.layout.DockablePropertyFactory;
import bibliothek.gui.dock.layout.PropertyTransformer;
import bibliothek.gui.dock.station.DisplayerFactory;
import bibliothek.gui.dock.station.ToolbarMiniButton;
import bibliothek.gui.dock.station.ToolbarTabDockStationFactory;
import bibliothek.gui.dock.station.screen.*;
import bibliothek.gui.dock.station.screen.magnet.AttractorStrategy;
import bibliothek.gui.dock.station.screen.window.DefaultScreenDockWindowConfiguration;
import bibliothek.gui.dock.station.toolbar.*;
import bibliothek.gui.dock.station.toolbar.group.ToolbarGroupPropertyFactory;
import bibliothek.gui.dock.station.toolbar.group.ToolbarGroupTitle;
import bibliothek.gui.dock.station.toolbar.title.ToolbarDockTitlePoint;
import bibliothek.gui.dock.themes.DockThemeExtension;
import bibliothek.gui.dock.themes.ThemeManager;
import bibliothek.gui.dock.themes.basic.action.BasicButtonHandler;
import bibliothek.gui.dock.themes.basic.action.BasicMenuHandler;
import bibliothek.gui.dock.themes.basic.action.BasicTitleViewItem;
import bibliothek.gui.dock.title.*;
import bibliothek.gui.dock.toolbar.ToolbarDockFrontendExtension;
import bibliothek.gui.dock.toolbar.expand.ExpandManager;
import bibliothek.gui.dock.toolbar.item.DockActionItem;
import bibliothek.gui.dock.toolbar.perspective.ToolbarFrontendPerspectiveCacheExtension;
import bibliothek.gui.dock.util.IconManager;
import bibliothek.gui.dock.util.Priority;
import bibliothek.gui.dock.util.TextManager;
import bibliothek.gui.dock.util.extension.Extension;
import bibliothek.gui.dock.util.extension.ExtensionName;
import bibliothek.gui.dock.wizard.WizardSplitDockStationFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.*;

/**
 * Allows seamless integration of the toolbar extension into the core and common
 * library without them having any references back to the toolbar project.
 *
 * @author Benjamin Sigg
 */
public class ToolbarExtension implements Extension {
    /**
     * unique flag for marking {@link DockTitle}s shown above a toolbar
     */
    public static final ViewTarget<BasicTitleViewItem<JComponent>> TOOLBAR_TITLE =
            new ViewTarget<>("target TOOLBAR TITLE");

    @Override
    public void install(final DockController controller) {
        final ActionViewConverter converter = controller.getActionViewConverter();
        converter.putDefault(ActionType.BUTTON, TOOLBAR_TITLE, (converter19, action, dockable) -> {
            final BasicButtonHandler handler = new BasicButtonHandler(action, dockable);
            final ToolbarMiniButton button = new ToolbarMiniButton(handler, handler);
            handler.setModel(button.getModel());
            return handler;
        });

        converter.putDefault(ActionType.MENU, TOOLBAR_TITLE, (converter18, action, dockable) -> {
            final BasicMenuHandler handler = new BasicMenuHandler(action, dockable);
            final ToolbarMiniButton button = new ToolbarMiniButton(handler, handler);
            handler.setModel(button.getModel());
            return handler;
        });

        converter.putDefault(ActionType.SEPARATOR, TOOLBAR_TITLE, (converter17, action, dockable) -> {
            if (action.shouldDisplay(ViewTarget.TITLE)) {
                return new ToolbarSeparator(action, Color.LIGHT_GRAY);
            }

            return null;
        });

        converter.putDefault(ActionType.BUTTON, DockActionItem.TOOLBAR,
                (converter16, action, dockable) -> converter16.createView(ActionType.BUTTON, action, ViewTarget.TITLE
                        , dockable));
        converter.putDefault(ActionType.CHECK, DockActionItem.TOOLBAR,
                (converter15, action, dockable) -> converter15.createView(ActionType.CHECK, action, ViewTarget.TITLE,
                        dockable));
        converter.putDefault(ActionType.DROP_DOWN, DockActionItem.TOOLBAR,
                (converter14, action, dockable) -> converter14.createView(ActionType.DROP_DOWN, action,
                        ViewTarget.TITLE, dockable));
        converter.putDefault(ActionType.MENU, DockActionItem.TOOLBAR,
                (converter13, action, dockable) -> converter13.createView(ActionType.MENU, action, ViewTarget.TITLE,
                        dockable));
        converter.putDefault(ActionType.RADIO, DockActionItem.TOOLBAR,
                (converter12, action, dockable) -> converter12.createView(ActionType.RADIO, action, ViewTarget.TITLE,
                        dockable));
        converter.putDefault(ActionType.SEPARATOR, DockActionItem.TOOLBAR,
                (converter1, action, dockable) -> converter1.createView(ActionType.SEPARATOR, action,
                        ViewTarget.TITLE, dockable));

        final IconManager icons = controller.getIcons();
        icons.setIconDefault("toolbar.item.expand.horizontal", loadIcon("expand_horizontal.png"));
        icons.setIconDefault("toolbar.item.expand.vertical", loadIcon("expand_vertical.png"));
        icons.setIconDefault("toolbar.item.expand.hover.horizontal", loadIcon("expand_hover_horizontal.png"));
        icons.setIconDefault("toolbar.item.expand.hover.vertical", loadIcon("expand_hover_vertical.png"));
        icons.setIconDefault("toolbar.item.shrink.horizontal", loadIcon("shrink_horizontal.png"));
        icons.setIconDefault("toolbar.item.shrink.vertical", loadIcon("shrink_vertical.png"));
        icons.setIconDefault("toolbar.item.shrink.hover.horizontal", loadIcon("shrink_hover_horizontal.png"));
        icons.setIconDefault("toolbar.item.shrink.hover.vertical", loadIcon("shrink_hover_vertical.png"));
        icons.setIconDefault("toolbar.item.larger.horizontal", loadIcon("larger_horizontal.png"));
        icons.setIconDefault("toolbar.item.larger.vertical", loadIcon("larger_vertical.png"));
        icons.setIconDefault("toolbar.item.larger.hover.horizontal", loadIcon("larger_hover_horizontal.png"));
        icons.setIconDefault("toolbar.item.larger.hover.vertical", loadIcon("larger_hover_vertical.png"));
        icons.setIconDefault("toolbar.item.smaller.horizontal", loadIcon("smaller_horizontal.png"));
        icons.setIconDefault("toolbar.item.smaller.vertical", loadIcon("smaller_vertical.png"));
        icons.setIconDefault("toolbar.item.smaller.hover.horizontal", loadIcon("smaller_hover_horizontal.png"));
        icons.setIconDefault("toolbar.item.smaller.hover.vertical", loadIcon("smaller_hover_vertical.png"));
        icons.setIconDefault("toolbar.customization.here", loadIcon("here.png"));
        icons.setIconDefault("toolbar.customization.check", loadIcon("check.png"));
        icons.setIconDefault("toolbar.customization.preferences", loadIcon("preferences.png"));


        // controller.addActionGuard( new ExpandedActionGuard( controller ) );
        new ExpandManager(controller);

        // add or remove a filter for preventing fullscreen
        final ToolbarFullscreenFilter filter = new ToolbarFullscreenFilter(controller);
        controller.getRegister().addDockRegisterListener(new DockRegisterAdapter() {
            @Override
            public void dockStationRegistering(DockController controller, DockStation station) {
                if (station instanceof ScreenDockStation) {
                    ((ScreenDockStation) station).addFullscreenFilter(filter);
                }
            }

            @Override
            public void dockStationUnregistered(DockController controller, DockStation station) {
                if (station instanceof ScreenDockStation) {
                    ((ScreenDockStation) station).removeFullscreenFilter(filter);
                }
            }
        });

        // install expandable strategy
        controller.getProperties().addListener(ExpandableToolbarItemStrategy.STRATEGY, (properties, property,
                                                                                        oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.uninstall(controller);
            }
            if (newValue != null) {
                newValue.install(controller);
            }
        });
        controller.getProperties().get(ExpandableToolbarItemStrategy.STRATEGY).install(controller);

        controller.getThemeManager().put(Priority.THEME, ThemeManager.STATION_PAINT +
                ".toolbar", ThemeManager.STATION_PAINT_TYPE, new ToolbarStationPaint(new Color(255, 0, 0, 125),
                new Color(128, 128, 128, 125)));
    }

    private Icon loadIcon(String name) {
        try {
            final InputStream in = getClass().getResourceAsStream("/data/bibliothek/gui/toolbar/" + name);
            if (in == null) {
                throw new FileNotFoundException("cannot find file '" + name + "'");
            }
            final ImageIcon icon = new ImageIcon(ImageIO.read(in));
            in.close();
            return icon;
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void uninstall(DockController controller) {
        final ActionViewConverter converter = controller.getActionViewConverter();
        converter.putDefault(ActionType.BUTTON, TOOLBAR_TITLE, null);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> Collection<E> load(DockController controller, ExtensionName<E> extension) {
        if (extension.getName().equals(PropertyTransformer.FACTORY_EXTENSION)) {
            return (Collection<E>) createPropertyFactoryExtension();
        }
        if (extension.getName().equals(DefaultDockRelocator.MERGE_EXTENSION)) {
            return (Collection<E>) createMergerExtension();
        }
        if (extension.getName().equals(DefaultDockRelocator.INSERTER_EXTENSION)) {
            return (Collection<E>) createInserterExtension(controller);
        }
        if (extension.getName().equals(ScreenDockStation.ATTRACTOR_STRATEGY_EXTENSION)) {
            return (Collection<E>) createAttractorStrategies();
        }
        if (extension.getName().equals(DockSituation.DOCK_FACTORY_EXTENSION)) {
            return (Collection<E>) createDockFactories();
        }
        if (extension.getName().equals(DockThemeExtension.DOCK_THEME_EXTENSION)) {
            return (Collection<E>) createDockThemeExtension();
        }
        if (extension.getName().equals(DockTitleVersion.DOCK_TITLE_VERSION_EXTENSION)) {
            return (Collection<E>) createTitleFactories((DockTitleVersion) extension.get(DockTitleVersion.DOCK_TITLE_VERSION_EXTENSION_PARAMETER));
        }
        if (extension.getName().equals(DisplayerFactory.DISPLAYER_EXTENSION)) {
            return (Collection<E>) createDisplayerFactories(controller,
                    (String) extension.get(DisplayerFactory.DISPLAYER_EXTENSION_ID));
        }
        if (extension.getName().equals(TextManager.TEXT_EXTENSION)) {
            return (Collection<E>) createBundles((Locale) extension.get(TextManager.TEXT_EXTENSION_LOCALE));
        }
        if (extension.getName().equals(DefaultScreenDockWindowConfiguration.CONFIGURATION_EXTENSION)) {
            return (Collection<E>) createWindowConfigurationExtension(controller);
        }
        if (extension.getName().equals(DefaultDockableMovingImageFactory.FACTORY_EXTENSION)) {
            return (Collection<E>) createMovingImageFactory();
        }
        if (extension.getName().equals(ScreenDockStation.STATION_EXTENSION)) {
            return (Collection<E>) createScreenDockStationExtension(controller);
        }
        if (extension.getName().equals(DefaultFrontendPerspectiveCache.CACHE_EXTENSION)) {
            return (Collection<E>) createPerspectiveCacheExtensions();
        }
        if (extension.getName().equals(DockFrontend.FRONTEND_EXTENSION)) {
            return (Collection<E>) createFrontendExtensions();
        }

        return null;
    }

    protected Collection<DockablePropertyFactory> createPropertyFactoryExtension() {
        final List<DockablePropertyFactory> result = new ArrayList<>();
        result.add(new ToolbarPropertyFactory());
        result.add(new ToolbarContainerPropertyFactory());
        result.add(new ToolbarGroupPropertyFactory());
        return result;
    }

    protected Collection<Merger> createMergerExtension() {
        final List<Merger> result = new ArrayList<>();
        result.add(new ToolbarDockStationMerger());
        result.add(new ToolbarGroupDockStationMerger());
        return result;
    }

    protected Collection<Inserter> createInserterExtension(DockController controller) {
        final List<Inserter> result = new ArrayList<>();
        result.add(new ScreenToolbarInserter(controller));
        return result;
    }

    protected Collection<AttractorStrategy> createAttractorStrategies() {
        final List<AttractorStrategy> result = new ArrayList<>();
        result.add(new ToolbarAttractorStrategy());
        return result;
    }

    protected Collection<DockFactory<?, ?, ?>> createDockFactories() {
        final List<DockFactory<?, ?, ?>> result = new ArrayList<>();
        result.add(new ToolbarGroupDockStationFactory());
        result.add(new ToolbarDockStationFactory());
        result.add(new ToolbarContainerDockStationFactory());
        result.add(new ToolbarTabDockStationFactory());
        result.add(new WizardSplitDockStationFactory());
        result.add(new ToolbarItemDockableFactory());
        return result;
    }

    protected Collection<DockTitleFactory> createTitleFactories(DockTitleVersion version) {
        if (version.getID().equals(ScreenDockStation.TITLE_ID)) {
            final List<DockTitleFactory> result = new ArrayList<>();
            result.add(new ScreenToolbarDockTitleFactory(version.getController()));
            return result;
        }
        return null;
    }

    protected Collection<DisplayerFactory> createDisplayerFactories(DockController controller, String id) {
        if (id.equals(ScreenDockStation.DISPLAYER_ID)) {
            final List<DisplayerFactory> result = new ArrayList<>();
            result.add(new ScreenToolbarDisplayerFactory(controller));
            return result;
        }
        return null;
    }

    protected Collection<ResourceBundle> createBundles(Locale language) {
        final List<ResourceBundle> result = new ArrayList<>();
        result.add(ResourceBundle.getBundle("data.bibliothek.gui.toolbar.locale.toolbar", language,
                this.getClass().getClassLoader()));
        return result;
    }

    protected Collection<DockThemeExtension> createDockThemeExtension() {
        final DockThemeExtension extension = new DockThemeExtension() {

            @Override
            public void installed(DockController controller, DockTheme theme) {
                final ThemeManager manager = controller.getThemeManager();
                manager.put(Priority.THEME, ThemeManager.DISPLAYER_FACTORY +
                        ".toolbar", ThemeManager.DISPLAYER_FACTORY_TYPE, ToolbarDockableDisplayer.FACTORY);
                manager.put(Priority.THEME, ThemeManager.DISPLAYER_FACTORY +
                        ".toolbar.simple", ThemeManager.DISPLAYER_FACTORY_TYPE, ToolbarDockableDisplayer.FACTORY);
                manager.put(Priority.THEME, ThemeManager.DISPLAYER_FACTORY +
                        ".toolbar.group", ThemeManager.DISPLAYER_FACTORY_TYPE, ToolbarDockableDisplayer.FACTORY);
                manager.put(Priority.THEME, ThemeManager.DISPLAYER_FACTORY +
                        ".toolbar.container", ThemeManager.DISPLAYER_FACTORY_TYPE, ToolbarDockableDisplayer.FACTORY);
                manager.put(Priority.THEME, ThemeManager.DISPLAYER_FACTORY +
                        ".toolbar.screen", ThemeManager.DISPLAYER_FACTORY_TYPE, ToolbarDockableDisplayer.FACTORY);

                final DockTitleManager titles = controller.getDockTitleManager();
                // titles.registerTheme(ToolbarGroupDockStation.TITLE_ID,
                // ToolbarDockTitleGrip.createFactory());
                titles.registerTheme(ToolbarGroupDockStation.TITLE_ID, ToolbarDockTitlePoint.createFactory());
                titles.registerTheme(ToolbarDockStation.TITLE_ID, NullTitleFactory.INSTANCE);

                // titles.registerTheme( ToolbarContainerDockStation.TITLE_ID,
                // ToolbarDockTitleRoundedBound.createFactory( new Color( 80,
                // 80, 80 ) ) );
                // titles.registerTheme( ScreenToolbarDockTitleFactory.TITLE_ID,
                // ToolbarDockTitleRoundedBound.createFactory( new Color( 80,
                // 80, 80 ) ) );

                titles.registerTheme(ToolbarContainerDockStation.TITLE_ID, ToolbarGroupTitle.FACTORY);
                titles.registerTheme(ScreenToolbarDockTitleFactory.TITLE_ID, ToolbarGroupTitle.FACTORY);
            }

            @Override
            public void install(DockController controller, DockTheme theme) {
                // nothing
            }

            @Override
            public void uninstall(DockController controller, DockTheme theme) {
                // nothing
            }

        };
        return Collections.singleton(extension);
    }

    protected Collection<ScreenDockWindowConfiguration> createWindowConfigurationExtension(DockController controller) {
        final List<ScreenDockWindowConfiguration> result = new ArrayList<>();
        result.add(new ToolbarWindowConfiguration(controller));
        return result;
    }

    protected Collection<DockableMovingImageFactory> createMovingImageFactory() {
        final List<DockableMovingImageFactory> result = new ArrayList<>();
        // result.add( new ToolbarMovingImageFactory( new
        // ScreencaptureMovingImageFactory( new Dimension( 200, 200 ) ) ) );
        result.add(new ToolbarMovingImageFactory(new AncestorMovingImageFactory(null, 0.5f)));
        return result;
    }

    protected Collection<ScreenDockStationExtension> createScreenDockStationExtension(DockController controller) {
        List<ScreenDockStationExtension> result = new ArrayList<>();
        result.add(new ToolbarScreenDockStationExtension(controller));
        return result;
    }

    protected Collection<FrontendPerspectiveCacheExtension> createPerspectiveCacheExtensions() {
        List<FrontendPerspectiveCacheExtension> result = new ArrayList<>();
        result.add(new ToolbarFrontendPerspectiveCacheExtension());
        return result;
    }

    protected Collection<DockFrontendExtension> createFrontendExtensions() {
        List<DockFrontendExtension> result = new ArrayList<>();
        result.add(new ToolbarDockFrontendExtension());
        return result;
    }
}
