package AVLTree;

import java.util.List;

public interface ValueContainer {
    Integer getValue();

    ValueContainer getParent();

    ValueContainer getLeft();

    int getHeight();

    int getLeftHeight();

    int getRightHeight();

    boolean isBalanced();

    boolean isEmpty();

    /**
     * @return является ли вершина левой для совего родителя
     */
    boolean isLeft();

    List<ValueContainer> asList();

}
