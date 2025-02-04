package glass.eclipse;

import bibliothek.extension.gui.dock.theme.EclipseTheme;
import bibliothek.gui.DockController;
import bibliothek.gui.DockTheme;
import bibliothek.gui.dock.common.theme.CDockTheme;
import bibliothek.gui.dock.common.theme.CEclipseTheme;
import bibliothek.gui.dock.common.theme.color.CColorBridgeExtension;
import bibliothek.gui.dock.themes.DockThemeExtension;
import bibliothek.gui.dock.util.DockProperties;
import bibliothek.gui.dock.util.Priority;
import bibliothek.gui.dock.util.PropertyKey;
import bibliothek.gui.dock.util.extension.Extension;
import bibliothek.gui.dock.util.extension.ExtensionName;
import bibliothek.gui.dock.util.property.ConstantPropertyFactory;
import glass.eclipse.theme.CGlassEclipseTabPainter;
import glass.eclipse.theme.EclipseThemeExtension;
import glass.eclipse.theme.GlassEclipseTabTransmitterFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * This extensions changes the look of the {@link bibliothek.extension.gui.dock.theme.EclipseTheme}, this
 * extension is only loadable if the Common project is in the classpath
 *
 * @author Benjamin Sigg
 */
public class CGlassExtension implements Extension {
    public static final PropertyKey<Boolean> SMALL_TAB_SIZE =
            new PropertyKey<>("Glass eclipse theme tab size", new ConstantPropertyFactory<>(false), true);

    public void install(DockController controller) {
        DockProperties properties = controller.getProperties();

        properties.set(EclipseTheme.TAB_PAINTER, CGlassEclipseTabPainter.FACTORY, Priority.DEFAULT);
        properties.set(EclipseTheme.PAINT_ICONS_WHEN_DESELECTED, true, Priority.DEFAULT);
        properties.set(SMALL_TAB_SIZE, false, Priority.DEFAULT);
    }

    public void uninstall(DockController controller) {
        // ignore
    }

    @SuppressWarnings("unchecked")
    public <E> Collection<E> load(DockController controller, ExtensionName<E> extension) {
        List<E> result = new ArrayList<>();

        if (extension.getName().equals(DockThemeExtension.DOCK_THEME_EXTENSION)) {
            Object themeParameter = extension.get(DockThemeExtension.THEME_PARAMETER);
            DockTheme trigger = null;

            if (themeParameter instanceof CDockTheme) {
                trigger = (DockTheme) themeParameter;
                themeParameter = ((CDockTheme<EclipseTheme>) themeParameter).intern();

                if (themeParameter instanceof EclipseTheme theme) {
                    result.add((E) new EclipseThemeExtension(trigger, theme));
                }
            }
        }

        //      if (extension.getName().equals(ChoiceExtension.CHOICE_EXTENSION)) {
        //         Object choice = extension.get(ChoiceExtension.CHOICE_PARAMETER);
        //         if (choice instanceof EclipseTabChoice) {
        //            result.add((E)new CGlassEclipseTabChoiceExtension());
        //            result.add((E)new CGlassEclipseSmallTabChoiceExtension());
        //         }
        //      }

        if (extension.getName().equals(CColorBridgeExtension.EXTENSION_NAME)) {
            Object parameterValue = extension.get(CColorBridgeExtension.PARAMETER_NAME);
            if (parameterValue instanceof CEclipseTheme) {
                result.add((E) new GlassEclipseTabTransmitterFactory());
            }
        }

        return result;
    }
}
