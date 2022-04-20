package bibliothek.test;

public interface InspectionNodeListener {
    void updated();

    void updated(InspectionNode[] oldChildren, InspectionNode[] newChildren);
}
