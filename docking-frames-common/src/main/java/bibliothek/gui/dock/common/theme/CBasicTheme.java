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
package bibliothek.gui.dock.common.theme;

import bibliothek.gui.DockController;
import bibliothek.gui.dock.action.view.ActionViewConverter;
import bibliothek.gui.dock.action.view.ViewTarget;
import bibliothek.gui.dock.common.CControl;
import bibliothek.gui.dock.common.action.CPanelPopup;
import bibliothek.gui.dock.common.intern.action.panel.BasicPanelPopupGenerator;
import bibliothek.gui.dock.common.intern.action.panel.PanelDropDownGenerator;
import bibliothek.gui.dock.common.intern.action.panel.PanelMenuGenerator;
import bibliothek.gui.dock.common.intern.color.BasicButtonTitleTransmitter;
import bibliothek.gui.dock.common.intern.color.BasicTabTransmitter;
import bibliothek.gui.dock.common.intern.color.BasicTitleTransmitter;
import bibliothek.gui.dock.themes.BasicTheme;
import bibliothek.gui.dock.themes.NoStackTheme;
import bibliothek.gui.dock.themes.color.TabColor;
import bibliothek.gui.dock.themes.color.TitleColor;
import bibliothek.util.ClientOnly;

/**
 * A bridge between a {@link BasicTheme} and the common-project.
 *
 * @author Benjamin Sigg
 */
@ClientOnly
public class CBasicTheme extends CDockTheme<BasicTheme> {
    /**
     * Creates a new theme.
     *
     * @param control the controller for which this theme will be used
     * @param theme   the theme that gets encapsulated
     */
    public CBasicTheme(CControl control, BasicTheme theme) {
        super(theme);
        init(control);
    }

    /**
     * Creates a new theme. This theme can be used directly with a
     * {@link CControl}.
     *
     * @param control the controller for which this theme will be used.
     */
    public CBasicTheme(CControl control) {
        this(new BasicTheme());
        init(control);
    }

    /**
     * Creates a new theme.
     *
     * @param theme the delegate which will do most of the work
     */
    private CBasicTheme(BasicTheme theme) {
        super(theme, new NoStackTheme(theme));
    }

    /**
     * Initializes the properties of this theme.
     *
     * @param control the controller for which this theme will be used
     */
    private void init(final CControl control) {
        initDefaultFontBridges(control);

        putColorBridgeFactory(TabColor.KIND_TAB_COLOR, manager -> {
            BasicTabTransmitter transmitter = new BasicTabTransmitter(manager);
            transmitter.setControl(control);
            return transmitter;
        });
        putColorBridgeFactory(TitleColor.KIND_TITLE_COLOR, manager -> {
            BasicTitleTransmitter transmitter = new BasicTitleTransmitter(manager);
            transmitter.setControl(control);
            return transmitter;
        });
        putColorBridgeFactory(TitleColor.KIND_FLAP_BUTTON_COLOR, manager -> {
            BasicButtonTitleTransmitter transmitter = new BasicButtonTitleTransmitter(manager);
            transmitter.setControl(control);
            return transmitter;
        });
    }

    @Override
    public void install(DockController controller) {
        super.install(controller);
        ActionViewConverter converter = controller.getActionViewConverter();
        converter.putTheme(CPanelPopup.PANEL_POPUP, ViewTarget.TITLE, new BasicPanelPopupGenerator());
        converter.putTheme(CPanelPopup.PANEL_POPUP, ViewTarget.MENU, new PanelMenuGenerator());
        converter.putTheme(CPanelPopup.PANEL_POPUP, ViewTarget.DROP_DOWN, new PanelDropDownGenerator());
    }

    @Override
    public void uninstall(DockController controller) {
        ActionViewConverter converter = controller.getActionViewConverter();
        converter.putTheme(CPanelPopup.PANEL_POPUP, ViewTarget.TITLE, null);
        converter.putTheme(CPanelPopup.PANEL_POPUP, ViewTarget.MENU, null);
        converter.putTheme(CPanelPopup.PANEL_POPUP, ViewTarget.DROP_DOWN, null);
        super.uninstall(controller);
    }
}
