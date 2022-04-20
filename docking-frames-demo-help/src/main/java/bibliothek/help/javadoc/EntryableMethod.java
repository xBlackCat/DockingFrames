package bibliothek.help.javadoc;

import bibliothek.help.model.Entry;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * An {@link Entryable} that collects detailed information about a method.
 *
 * @author Benjamin Sigg
 */
@Content(type = "method", encoding = Content.Encoding.DOCUMENT)
public class EntryableMethod extends AbstractEntryable {
    /**
     * The method that is analyzed
     */
    private ExecutableElement doc;

    /**
     * Collects detailed information about the method <code>doc</code>.
     *
     * @param doc the method to analyze
     */
    public EntryableMethod(ExecutableElement doc) {
        this.doc = doc;

        bold(true);
        println("Containing: ");
        bold(false);
        linkln(doc.containingClass().qualifiedName(), "class", doc.containingClass().qualifiedName());
        println();
        bold(true);
        println("Name:");
        bold(false);
        print(doc.modifiers());
        print(" ");
        print(doc.returnType());
        print(" ");
        print(doc.name());
        print("(");
        VariableElement[] args = doc.parameters();
        for (int i = 0; i < args.length; i++) {
            if (i > 0) {
                print(", ");
            }
            print(args[i].type());
            print(" ");
            print(args[i].name());
        }
        println(")");

        if (doc.thrownExceptionTypes().length > 0) {
            println();
            bold(true);
            println("Throws:");
            bold(false);
            for (TypeMirror type : doc.thrownExceptionTypes()) {
                print(type);
                println();
            }
        }

        if (doc.commentText() != null) {
            println();
            bold(true);
            println("Comment:");
            bold(false);
            println(doc.commentText());
        }
    }

    public Entry toEntry() {
        return new Entry("method", doc.qualifiedName() + doc.signature(),
                "Method " + doc.qualifiedName() + doc.signature(), content(),
                "class:" + doc.containingClass().qualifiedName());
    }
}
