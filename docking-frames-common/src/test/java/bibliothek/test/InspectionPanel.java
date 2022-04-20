package bibliothek.test;

import javax.swing.*;
import javax.swing.tree.TreePath;
import java.awt.*;

public class InspectionPanel extends JPanel {
    private final JTree tree;
    private final JTextPane console;
    private final InspectionTree model;

    public InspectionPanel(Object root, InspectionGraph graph) {
        setLayout(new BorderLayout());

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        add(split, BorderLayout.CENTER);

        model = new InspectionTree(graph.getNode(root), graph);
        tree = new JTree(model);
        console = new JTextPane();

        split.setTopComponent(new JScrollPane(tree));
        split.setBottomComponent(new JScrollPane(console));

        tree.setEditable(false);
        console.setEditable(false);

        tree.addTreeSelectionListener(e -> updateText());

        tree.setSelectionRow(0);

        Timer timer = new Timer(500, e -> {
            model.update(true);
            updateText();
        });
        timer.setRepeats(true);
        timer.start();
    }

    private void updateText() {
        TreePath path = tree.getSelectionPath();
        if (path == null) {
            console.setText("");
        } else {
            String text = String.valueOf(path.getLastPathComponent());
            if (!text.equals(console.getText())) {
                console.setText(text);
            }
        }
    }
}
