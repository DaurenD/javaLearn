package AVLTree;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
public class AVLTreePrinter {
    public static void printTree(ValueContainer top) {

        if (top == null || top.isEmpty()) {
            System.out.println("Empty tree");
            return;
        }
        printLayers(top.asList(), top.getHeight(), null);
    }

    private static void printLayers(List<ValueContainer> vertices, int height, Set<ValueContainer> parents) {
        if (height < 1) {
            return;
        }
        Set<ValueContainer> newParents = new HashSet<ValueContainer>();
        for (ValueContainer vertex : vertices) {

            String value = vertex.getValue().toString();
            if ((parents == null && vertex.getParent() == null) || (parents != null && parents.contains(vertex.getParent()))) {
                System.out.print(value);
                newParents.add(vertex);
            } else {
                for (int i = 0; i < value.length(); i++) {
                    System.out.print(' ');
                }
            }
        }
        System.out.println();
        boolean open = false;
        for (ValueContainer vertex : vertices) {

            int len = vertex.getValue().toString().length();
            if (newParents.contains(vertex.getParent())) {
                open = vertex.isLeft() && !vertex.getParent().getLeft().isEmpty();
                System.out.print(vertex.isLeft() ? '/' : '\\');
                len--;
            }
            for (int i = 0; i < len; i++) {
                System.out.print(open ? '\u203E' : ' ');
            }
        }
        System.out.println();
        printLayers(vertices, height - 1, newParents);
    }
}