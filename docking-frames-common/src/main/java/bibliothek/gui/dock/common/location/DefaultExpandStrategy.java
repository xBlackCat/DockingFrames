package bibliothek.gui.dock.common.location;

import bibliothek.gui.DockController;
import bibliothek.gui.dock.common.CLocation;
import bibliothek.gui.dock.layout.DockableProperty;
import bibliothek.gui.dock.station.flap.FlapDockProperty;
import bibliothek.gui.dock.station.screen.ScreenDockProperty;
import bibliothek.gui.dock.station.split.SplitDockFullScreenProperty;
import bibliothek.gui.dock.station.split.SplitDockPathProperty;
import bibliothek.gui.dock.station.split.SplitDockPlaceholderProperty;
import bibliothek.gui.dock.station.split.SplitDockProperty;
import bibliothek.gui.dock.station.stack.StackDockProperty;
import bibliothek.gui.dock.util.extension.ExtensionName;
import bibliothek.util.Path;

import java.util.List;

/**
 * The default implementation of {@link CLocationExpandStrategy}. This strategy
 * just contains a list for all known {@link DockableProperty}s and can react
 * to each property accordingly
 *
 * @author Benjamin Sigg
 */
public class DefaultExpandStrategy implements CLocationExpandStrategy {
    /**
     * Unique id of an extension of {@link CLocationExpandStrategy}s that are utilized before this strategy
     * is used.
     */
    public static final Path STRATEGY_EXTENSION = new Path("dock.expandStrategy");

    /**
     * Name of a parameter pointing to <code>this</code> in an {@link ExtensionName}
     */
    public static final String EXTENSION_PARAM = "strategy";

    /**
     * Extensions that will be asked for providing a {@link CLocation} before <code>this</code> is evaluated
     */
    private final List<CLocationExpandStrategy> extensions;

    /**
     * Creates a new expand strategy loading extensions if available.
     *
     * @param controller the controller in whose realm this strategy is used
     */
    public DefaultExpandStrategy(DockController controller) {
        extensions =
                controller.getExtensions().load(new ExtensionName<>(STRATEGY_EXTENSION, CLocationExpandStrategy.class
                        , EXTENSION_PARAM, this));
    }

    public CLocation expand(CLocation location, DockableProperty property) {
        for (CLocationExpandStrategy extension : extensions) {
            CLocation result = extension.expand(location, property);
            if (result != null) {
                return result;
            }
        }

        if (property instanceof FlapDockProperty) {
            return expand(location, (FlapDockProperty) property);
        }
        if (property instanceof ScreenDockProperty) {
            return expand(location, (ScreenDockProperty) property);
        }
        if (property instanceof SplitDockFullScreenProperty) {
            return expand(location, (SplitDockFullScreenProperty) property);
        }
        if (property instanceof SplitDockPathProperty) {
            return expand(location, (SplitDockPathProperty) property);
        }
        if (property instanceof SplitDockPlaceholderProperty) {
            return expand(location, (SplitDockPlaceholderProperty) property);
        }
        if (property instanceof SplitDockProperty) {
            return expand(location, (SplitDockProperty) property);
        }
        if (property instanceof StackDockProperty) {
            return expand(location, (StackDockProperty) property);
        }
        return null;
    }

    /**
     * Creates a new location by creating the child location of <code>location</code> using
     * <code>property</code> for that step.
     *
     * @param location the location to expand
     * @param property the property that is the source of the next location
     * @return the new location or <code>null</code> if no conversion is possible
     */
    protected CLocation expand(CLocation location, FlapDockProperty property) {
        if (!(location instanceof CFlapLocation)) {
            location = new CFlapLocation(location);
        }
        return new CFlapIndexLocation((CFlapLocation) location, property.getIndex());
    }

    /**
     * Creates a new location by creating the child location of <code>location</code> using
     * <code>property</code> for that step.
     *
     * @param location the location to expand
     * @param property the property that is the source of the next location
     * @return the new location or <code>null</code> if no conversion is possible
     */
    protected CLocation expand(CLocation location, ScreenDockProperty property) {
        if (property.isFullscreen()) {
            return new CMaximalExternalizedLocation(location, property.getX(), property.getY(), property.getWidth(),
                    property.getHeight());
        } else {
            return new CExternalizedLocation(location, property.getX(), property.getY(), property.getWidth(),
                    property.getHeight());
        }
    }

    /**
     * Creates a new location by creating the child location of <code>location</code> using
     * <code>property</code> for that step.
     *
     * @param location the location to expand
     * @param property the property that is the source of the next location
     * @return the new location or <code>null</code> if no conversion is possible
     */
    protected CLocation expand(CLocation location, SplitDockFullScreenProperty property) {
        // as this property is never created by the framework itself, there is no point
        // in handling it.
        return null;
    }

    /**
     * Creates a new location by creating the child location of <code>location</code> using
     * <code>property</code> for that step.
     *
     * @param location the location to expand
     * @param property the property that is the source of the next location
     * @return the new location or <code>null</code> if no conversion is possible
     */
    protected CLocation expand(CLocation location, SplitDockPathProperty property) {
        if (!(location instanceof CSplitLocation)) {
            location = new CSplitLocation(location);
        }

        CSplitLocation split = (CSplitLocation) location;

        if (property.size() > 0) {
            AbstractTreeLocation tree = null;
            SplitDockPathProperty.Node node = property.getNode(0);
            tree = switch (node.location()) {
                case BOTTOM -> split.south(node.size(), node.id());
                case LEFT -> split.west(node.size(), node.id());
                case RIGHT -> split.east(node.size(), node.id());
                case TOP -> split.north(node.size(), node.id());
            };

            for (int i = 1, n = property.size(); i < n; i++) {
                node = property.getNode(i);
                tree = switch (node.location()) {
                    case BOTTOM -> tree.south(node.size(), node.id());
                    case LEFT -> tree.west(node.size(), node.id());
                    case RIGHT -> tree.east(node.size(), node.id());
                    case TOP -> tree.north(node.size(), node.id());
                };
            }
            location = tree.leaf(property.getLeafId());
        } else {
            location = split.rectangle(0, 0, 1, 1);
        }
        return location;
    }

    /**
     * Creates a new location by creating the child location of <code>location</code> using
     * <code>property</code> for that step.
     *
     * @param location the location to expand
     * @param property the property that is the source of the next location
     * @return the new location or <code>null</code> if no conversion is possible
     */
    protected CLocation expand(CLocation location, SplitDockPlaceholderProperty property) {
        return expand(location, property.getBackup());
    }

    /**
     * Creates a new location by creating the child location of <code>location</code> using
     * <code>property</code> for that step.
     *
     * @param location the location to expand
     * @param property the property that is the source of the next location
     * @return the new location or <code>null</code> if no conversion is possible
     */
    protected CLocation expand(CLocation location, SplitDockProperty property) {
        if (!(location instanceof CSplitLocation)) {
            location = new CSplitLocation(location);
        }
        return new CRectangleLocation((CSplitLocation) location, property.getX(), property.getY(), property.getWidth(), property.getHeight());
    }

    /**
     * Creates a new location by creating the child location of <code>location</code> using
     * <code>property</code> for that step.
     *
     * @param location the location to expand
     * @param property the property that is the source of the next location
     * @return the new location or <code>null</code> if no conversion is possible
     */
    protected CLocation expand(CLocation location, StackDockProperty property) {
        return new CStackLocation(location, property.getIndex());
    }
}
