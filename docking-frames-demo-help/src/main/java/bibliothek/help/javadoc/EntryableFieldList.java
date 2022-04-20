package bibliothek.help.javadoc;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

import bibliothek.help.model.Entry;

/**
 * An {@link Entryable} which created a list of fields. The fields all belong
 * to the same class.
 *
 * @author Benjamin Sigg
 */
@Content(type = "field-list", encoding = Content.Encoding.DOCUMENT)
public class EntryableFieldList extends AbstractEntryable {
    /**
     * the class whose fields are listed up
     */
    private TypeElement doc;

    /**
     * Collects all fields of the class <code>doc</code>.
     *
     * @param doc a class whose fields are listed up
     */
    public EntryableFieldList(TypeElement doc) {
        this.doc = doc;

        for (VariableElement field : doc.fields()) {
            print(field.type());
            print(" ");
            italic(true);
            linkln(field.name(), "field", field.qualifiedName());
            italic(false);
            add(new EntryableField(field));
        }
    }

    public Entry toEntry() {
        return new Entry("field-list", doc.qualifiedName(), "Fields of " + doc.qualifiedName(), content());
    }
}
