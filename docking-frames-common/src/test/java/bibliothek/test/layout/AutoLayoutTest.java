package bibliothek.test.layout;

import bibliothek.gui.Dockable;
import bibliothek.gui.dock.common.CContentArea;
import bibliothek.gui.dock.common.CControl;
import bibliothek.gui.dock.common.CGrid;
import bibliothek.gui.dock.common.DefaultSingleCDockable;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class AutoLayoutTest {

    public static void main(String[] args) throws InterruptedException, InvocationTargetException {
        CControl control = new CControl();
        final Item[] items = new Item[10];
        for (int i = 0; i < items.length; i++) {
            items[i] = new Item(String.valueOf(i));
            control.addDockable(items[i]);
        }
        CContentArea area = control.getContentArea();

        CGrid grid = new CGrid();
        for (int i = 0; i < items.length; i++) {
            switch (i % 4) {
                case 0 -> grid.add(0, 0, 1, 1, items[i]);
                case 1 -> grid.add(0, 1, 1, 1, items[i]);
                case 2 -> grid.add(1, 0, 1, 1, items[i]);
                case 3 -> grid.add(1, 1, 1, 1, items[i]);
            }
        }
        area.deploy(grid);

        EventQueue.invokeAndWait(() -> {
            Random random = new Random(123);
            for (int i = 0; i < 100000; i++) {
                int index = random.nextInt() % items.length;
                index = (index + items.length) % items.length;
                boolean state = random.nextBoolean();

                int count = 0;
                for (Item item : items) {
                    if (item.isVisible()) {
                        count++;
                    }
                }

                System.out.println(i + ": " + count);
                items[index].setVisible(state);
                items[index].test();
            }
        });
    }

    private static class Item extends DefaultSingleCDockable {
        private boolean expected = false;

        public Item(String id) {
            super(id);
            setTitleText(id);
        }

        @Override
        public void setVisible(boolean visible) {
            super.setVisible(visible);
            expected = visible;
        }

        public void test() {
            if (isVisible() != expected) {
                throw new IllegalStateException();
            }
            if (expected) {
                Dockable dockable = intern();
                if (dockable.getDockParent() == null) {
                    throw new IllegalStateException();
                }
            } else {
                Dockable dockable = intern();
                if (dockable.getDockParent() != null) {
                    throw new IllegalStateException();
                }
            }
        }
    }
}