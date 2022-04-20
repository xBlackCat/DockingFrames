package bibliothek.help.javadoc;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;

import bibliothek.help.model.Entry;

import javax.lang.model.element.PackageElement;

import jdk.javadoc.doclet.DocletEnvironment;

/**
 * An {@link Entryable} that creates a list of all packages.
 *
 * @author Benjamin Sigg
 */
@Content(type = "package-list", encoding = Content.Encoding.DOCUMENT)
public class EntryablePackageList extends AbstractEntryable {
    /**
     * Creates a new list of packages.
     *
     * @param root the whole documentation of a java-project.
     */
    public EntryablePackageList(DocletEnvironment root) {
        linkln("All", "class-list", ".all");
        add(new EntryableClassList(root));
        println();

        PackageElement[] docs = root.specifiedPackages();
        Arrays.sort(docs, new Comparator<PackageElement>() {
            private Collator collator = Collator.getInstance();

            public int compare(PackageElement o1, PackageElement o2) {
                return collator.compare(o1.name(), o2.name());
            }
        });

        for (PackageElement child : docs) {
            linkln(child.name(), "class-list", child.name());
            add(new EntryableClassList(child));
        }
    }

    public Entry toEntry() {
        return new Entry("package-list", "root", "All packages", content(), "class-list:.all", "empty:");
    }

}
