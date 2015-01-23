package learnJava.avltree;

import java.util.List;

public interface ValueContainer {
    Integer getValue();

    ValueContainer getParent();

    ValueContainer getLeft();

    ValueContainer getRight();

    void setParent(ValueContainer parent);

    void setLeft(ValueContainer left);

    void setRight(ValueContainer right);

    void setValue(Integer value);

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
