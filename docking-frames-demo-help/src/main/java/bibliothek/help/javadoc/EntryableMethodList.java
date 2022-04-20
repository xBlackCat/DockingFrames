package bibliothek.help.javadoc;

import bibliothek.help.model.Entry;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

/**
 * An {@link Entryable} that creates a list of methods. All methods
 * are owned by the same class or interface.
 *
 * @author Benjamin Sigg
 */
@Content(type = "method-list", encoding = Content.Encoding.DOCUMENT)
public class EntryableMethodList extends AbstractEntryable {
    /**
     * the class whose methods are collected
     */
    private TypeElement doc;

    /**
     * Creates a new list of methods.
     *
     * @param doc the owner of the methods
     */
    public EntryableMethodList(TypeElement doc) {
        this.doc = doc;

        for (ExecutableElement method : doc.methods()) {
            add(new EntryableMethod(method));

            print(method.returnType());
            print(" ");
            italic(true);
            link(method.name(), "method", method.qualifiedName() + method.signature());
            italic(false);
            print("(");
            VariableElement[] args = method.parameters();
            for (int i = 0; i < args.length; i++) {
                if (i > 0) {
                    print(", ");
                }
                print(args[i].type());
            }
            println(")");
        }
    }

    public Entry toEntry() {
        return new Entry("method-list", doc.qualifiedName(),
                "Methods of " + doc.qualifiedName(), content(), "class:" + doc.qualifiedName());
    }
}
