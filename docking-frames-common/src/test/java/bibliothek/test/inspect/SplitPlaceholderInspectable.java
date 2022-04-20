package bibliothek.test.inspect;

import bibliothek.gui.dock.station.split.Placeholder;
import bibliothek.gui.dock.station.split.SplitNode;
import bibliothek.test.Inspect;
import bibliothek.test.Inspectable;
import bibliothek.test.InspectionGraph;

import java.lang.reflect.Field;

public class SplitPlaceholderInspectable implements Inspectable {
    private final Placeholder placeholder;
    private Field placeholders;

    public SplitPlaceholderInspectable(Placeholder placeholder) {
        this.placeholder = placeholder;
        try {
            placeholders = SplitNode.class.getDeclaredField("placeholders");
            placeholders.setAccessible(true);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


    public Inspect inspect(InspectionGraph graph) {
        return new DefaultInspect(graph) {
            @Override
            public boolean update() {
                try {
                    setName("Placeholder");
                    setValue(placeholder);

                    put("placeholder-set", placeholders.get(placeholder));
                    put("placeholder-map", placeholder.getPlaceholderMap());

                    return true;
                } catch (Exception e) {
                    throw new IllegalStateException(e);
                }
            }
        };
    }
}
