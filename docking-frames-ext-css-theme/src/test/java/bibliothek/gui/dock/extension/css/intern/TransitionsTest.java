package bibliothek.gui.dock.extension.css.intern;

import bibliothek.gui.dock.extension.css.CssScheme;
import bibliothek.gui.dock.extension.css.DefaultCssItem;
import bibliothek.gui.dock.extension.css.intern.range.Range;
import bibliothek.gui.dock.extension.css.intern.range.RangeTransitionProperty;
import bibliothek.gui.dock.extension.css.path.DefaultCssNode;
import bibliothek.gui.dock.extension.css.path.DefaultCssPath;
import bibliothek.gui.dock.extension.css.property.paint.ColorCssProperty;
import bibliothek.gui.dock.extension.css.property.paint.ColorType;
import bibliothek.gui.dock.extension.css.transition.ColorTransitionProperty;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class TransitionsTest {
    @Test
    public void rulesWithoutAnimationShouldApplyDirectly() {
        TestCssScheme scheme = TestCssRules.getNoAnimationScheme();
        TestItem item = new TestItem(scheme);
        item.addColorProperty();
        item.toBlack();
        assertNull(item.getColor());
        scheme.add(item);
        assertEquals(Color.BLACK, item.getColor());
        item.toWhite();
        assertEquals(Color.WHITE, item.getColor());
    }

    @Test
    public void animateColorProperty() {
        TestCssScheme scheme = TestCssRules.getAnimatedColorScheme();
        TestItem item = new TestItem(scheme);
        item.addAnimatedColorProperty();
        item.toBlack();
        assertNull(item.getColor());
        scheme.add(item);
        assertEquals(Color.BLACK, item.getColor());
        item.toWhite();
        scheme.runAnimations(5000);
        assertNotEquals(Color.BLACK, item.getColor());
        assertNotEquals(Color.WHITE, item.getColor());
        scheme.runAnimations(5050);
        assertEquals(Color.WHITE, item.getColor());
    }

    @Test
    public void overlappingAnimations() {
        TestCssScheme scheme = TestCssRules.getAnimatedColorScheme();
        TestItem item = new TestItem(scheme);
        item.addAnimatedColorProperty();
        item.toRed();
        assertNull(item.getColor());
        scheme.add(item);
        assertEquals(Color.RED, item.getColor());

        item.toGreen();
        scheme.runAnimations(3000);

        assertBetween(150, 200, item.getColor().getRed());
        assertBetween(50, 150, item.getColor().getGreen());
        assertEquals(0, item.getColor().getBlue());

        item.toBlue();
        scheme.runAnimations(3000);

        assertBetween(50, 200, item.getColor().getRed());
        assertBetween(50, 200, item.getColor().getGreen());
        assertBetween(50, 150, item.getColor().getBlue());

        scheme.runAnimations(4000);

        assertEquals(0, item.getColor().getRed());
        assertBetween(50, 150, item.getColor().getGreen());
        assertBetween(100, 200, item.getColor().getBlue());

        scheme.runAnimations(3100);

        assertEquals(Color.BLUE, item.getColor());
    }


    @Test
    public void onesidedProperties() {
        TestCssScheme scheme = TestCssRules.getAnimatedColorScheme();
        DefaultCssRule rule = new DefaultCssRule(TestCssRules.selector("yellow"));
        rule.setProperty("color", ColorType.convert(Color.YELLOW));
        scheme.addRule(rule);

        TestItem item = new TestItem(scheme);
        item.addAnimatedColorProperty();
        item.to("yellow");
        assertNull(item.getColor());
        scheme.add(item);

        // going from element without transition
        assertEquals(Color.YELLOW, item.getColor());
        item.to("red");
        assertEquals(Color.RED, item.getColor());

        // going from element with transition
        item.to("yellow");

        scheme.runAnimations(5000);
        assertNotEquals(Color.YELLOW, item.getColor());
        assertNotEquals(Color.RED, item.getColor());
        scheme.runAnimations(5050);
        assertEquals(Color.YELLOW, item.getColor());

    }


    @Test
    public void dependingProperties() {
        TestCssScheme scheme = TestCssRules.getAnimatedRangeScheme();
        TestItem item = new TestItem(scheme);
        item.addAnimatedRangeProperty();
        item.to("delta");
        assertNull(item.getRange());
        scheme.add(item);
        assertEquals("delta", item.getRange().getName());
        assertEquals(1000, item.getRange().getMin());
        assertEquals(1000, item.getRange().getMax());

        item.to("beta");
        scheme.runAnimations(5000);
        assertEquals(1000, item.getRange().getMin());
        assertBetween(450, 550, item.getRange().getMax());

        scheme.runAnimations(5050);
        assertEquals("beta", item.getRange().getName());
        assertEquals(1000, item.getRange().getMin());
        assertEquals(0, item.getRange().getMax());
    }

    @Test
    public void overlappingDependingProperties() {
        TestCssScheme scheme = TestCssRules.getAnimatedRangeScheme();
        TestItem item = new TestItem(scheme);
        item.addAnimatedRangeProperty();
        item.to("delta");
        assertNull(item.getRange());
        scheme.add(item);
        assertEquals("delta", item.getRange().getName());
        assertEquals(1000, item.getRange().getMin());
        assertEquals(1000, item.getRange().getMax());

        item.to("beta");
        scheme.runAnimations(5000);
        assertEquals(1000, item.getRange().getMin());
        assertBetween(450, 550, item.getRange().getMax());

        item.to("gamma");
        scheme.runAnimations(5050);
        assertBetween(450, 550, item.getRange().getMin());
        assertBetween(450, 550, item.getRange().getMax());

        scheme.runAnimations(5050);
        assertEquals("gamma", item.getRange().getName());
        assertEquals(0, item.getRange().getMin());
        assertEquals(1000, item.getRange().getMax());
    }

    private void assertBetween(int min, int max, int actual) {
        assertTrue(min <= actual, min + " <= " + actual);
        assertTrue(max >= actual, max + " >= " + actual);
    }

    private static class TestItem extends DefaultCssItem {
        private final Map<String, Object> values = new HashMap<>();
        private final CssScheme scheme;

        public TestItem(CssScheme scheme) {
            super(new DefaultCssPath(new DefaultCssNode("base")));
            this.scheme = scheme;
        }

        public void toWhite() {
            to("white");
        }

        public void toBlack() {
            to("black");
        }

        public void toRed() {
            to("red");
        }

        public void toGreen() {
            to("green");
        }

        public void toBlue() {
            to("blue");
        }

        public void to(String color) {
            DefaultCssNode node = new DefaultCssNode("base");
            node.setIdentifier(color);
            setPath(new DefaultCssPath(node));
        }

        public Color getColor() {
            return (Color) values.get("color");
        }

        public Range getRange() {
            return (Range) values.get("range");
        }

        public void addAnimatedColorProperty() {
            putProperty("color", new ColorTransitionProperty(scheme, this) {
                @Override
                public void set(Color value) {
                    System.out.println("color: " + value.getRed() + " " + value.getGreen() + " " + value.getBlue());
                    values.put("color", value);
                }
            });
        }

        public void addAnimatedRangeProperty() {
            putProperty("range", new RangeTransitionProperty(scheme, this) {
                protected void propertyChanged(Range value) {
                    System.out.println(
                            value.getClass().getSimpleName() + ": " + value.getName() + " " + value.getMin() + " " +
                                    value.getMax());
                    values.put("range", value);
                }
            });
        }

        public void addColorProperty() {
            putProperty("color", new ColorCssProperty() {
                @Override
                public void set(Color value) {
                    values.put("color", value);
                }
            });
        }
    }
}
