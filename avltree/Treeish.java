package learnJava.avltree;


import java.util.List;

public interface Treeish {
    void add(Integer i);

    void remove(Integer i);

    boolean contains(Integer i);

    //TEST METHODS

    ValueContainer getTop();

    List<ValueContainer> asList();

    void updateAllHeights();
}


