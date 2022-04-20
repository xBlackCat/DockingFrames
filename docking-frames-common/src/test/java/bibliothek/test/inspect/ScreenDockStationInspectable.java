package bibliothek.test.inspect;

import bibliothek.gui.dock.ScreenDockStation;
import bibliothek.gui.dock.station.support.PlaceholderList;
import bibliothek.test.Inspect;
import bibliothek.test.Inspectable;
import bibliothek.test.InspectionGraph;

import java.lang.reflect.Field;

public class ScreenDockStationInspectable implements Inspectable {
    private final ScreenDockStation station;
    private Field dockables;

    public ScreenDockStationInspectable(ScreenDockStation station) {
        this.station = station;

        try {
            dockables = ScreenDockStation.class.getDeclaredField("dockables");
            dockables.setAccessible(true);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    public Inspect inspect(InspectionGraph graph) {
        return new DefaultInspect(graph) {
            private int size = 0;

            @Override
            public boolean update() {
                try {
                    PlaceholderList<?, ?, ?> list = (PlaceholderList<?, ?, ?>) dockables.get(station);

                    setName("ScreenDockStation");
                    setValue(station);

                    int count = 0;
                    for (PlaceholderList<?, ?, ?>.Item item : list.list()) {
                        put(String.valueOf(count++), item);
                    }

                    while (size > count) {
                        remove(String.valueOf(--size));
                    }

                    size = count;

                    return true;
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }
}
