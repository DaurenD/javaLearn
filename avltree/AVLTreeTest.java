package learnJava.avltree;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class AVLTreeTest {



@Test
    public void testNull() {
        Treeish tree = getIntegerAVLTree();
        tree.add(1);
        tree.add(2);
        tree.add(null);
        tree.add(3);

        isSorted(tree, 3);
    }

    @Test(timeout = 1500)
    public void testBigTree(){
        processTest(getConsecutiveNumbers(1000000), false);
    }

    private int[] getConsecutiveNumbers(int size) {
        int[] input = new int[size];
        for (int i=0; i<input.length; i++) {
            input[i] = i;
        }
        return input;
    }

    @Test
    public void testSortedArray(){
        processTest(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
    }

    @Test
    public void testSortedDescArray(){
        processTest(new int[]{14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1});
    }

    @Test
    public void testRotateLeftTop(){
        processTest(new int[]{10, 4, 12, 11, 13, 14});
    }

    @Test
    public void testRotateLeftTopWithSameElements(){
        processTest(new int[]{10, 4, 12, 11, 12, 14});
    }

    @Test
    public void testRotateLeftTopSimple(){
        processTest(new int[]{2, 4, 6});
    }

    @Test
    public void testRotateLeftRightTop(){
        processTest(new int[]{2, 6, 5});
    }

    @Test
    public void testRotateRightLeftTop(){
        processTest(new int[]{4, 2, 3});
    }

    @Test
    public void testRotateLeftLeft(){
        processTest(new int[]{4, 2, 6, 8, 9});
    }

    @Test
    public void testRotateBigLeft(){
        processTest(new int[]{4, 2, 6, 10, 8, 15, 9, 20});
    }

    @Test
    public void testRotateLeftRight(){
        processTest(new int[]{4, 2, 6, 10, 8});
    }

    @Test
    public void testRotateRightTop(){
        processTest(new int[]{10, 12, 8, 9, 6, 4});
    }

    @Test
    public void testRotateRightTopSimple(){
        processTest(new int[]{10, 8, 3});
    }

    @Test
    public void testRotateRightRight(){
        processTest(new int[]{10, 12, 8, 9, 6, 15, 4, 7, 2});
    }

    @Test
    public void testRotateRightLeft(){
        processTest(new int[]{10, 12, 5, 2, 3});
    }

    @Test
    public void testRemoveLeaf() {
        processDeleteTest(3, new int[]{3, 1, 2});
    }

    @Test
    public void testRemoveNotExisting() {
        processDeleteTest(3, new int[]{4});
    }

    @Test
    public void testRemoveSmallTop() {
        processDeleteTest(3, new int[]{2, 1, 3});
    }

    @Test
    public void testRemoveTops() {
        processDeleteTest(5, 5);
    }

    @Test
    public void testRemoveNoRightNode() {
        processDeleteTest(6, new int[]{5});
    }

    @Test
    public void testRemoveRight() {
        processDeleteTest(6, new int[]{2, 1});
    }

    @Test
    public void testRemoveFromLeft() {
        processDeleteTest(22, new int[]{16, 15});
    }

    @Test
    public void testRemoveWithTurn() {
        processDeleteTest(6, new int[]{6, 5});
    }

    @Test
    public void testRemoveTrickyCase() {
        processDeleteTest(22, new int[]{9, 15, 13, 14});
    }

    @Test
    public void testRemoveTrickyCaseOnTheOtherSide() {
        processDeleteTest(22, new int[]{15, 9, 10, 11});
    }

    private void processTest(int[] values) {
        processTest(values, true);
    }

    private void processTest(int[] values, boolean testEachStep) {
        Treeish tree = getIntegerAVLTree();
        for (int i : values) {
            tree.add(i);
            if (testEachStep) {
                isSorted(tree, i);
            }
        }

        if (!testEachStep) {
            isSorted(tree, -1);
        } else {
            AVLTreePrinter.printTree(tree.getTop());
        }
    }

    private void processDeleteTest(int size, int[] toDelete) {
        Treeish tree = buildTreeForDelete(size);

        AVLTreePrinter.printTree(tree.getTop());

        for (int i : toDelete) {
            checkRemoved(tree, i);
        }
    }

    private void processDeleteTest(int size, int steps) {
        Treeish tree = buildTreeForDelete(size);

        AVLTreePrinter.printTree(tree.getTop());

        for (int i=0; i<steps; i++) {
            checkRemoved(tree, tree.getTop().getValue());
        }
    }

    private void checkRemoved(Treeish tree, int i) {
        System.out.println("Removing " + i);

        tree.remove(i);

        AVLTreePrinter.printTree(tree.getTop());

        Assert.assertFalse("Tree should not contain deleted item " + i, tree.contains(i));

        isSortedAfterDelete(tree, i);
    }

    private Treeish buildTreeForDelete(int size) {
        Treeish tree = getIntegerAVLTree();

        for (int i = 1; i <= size; i++) {
            tree.add(i);
        }
        return tree;
    }

    private void isSorted(Treeish tree, Integer value) {
        isSorted(tree, value, "Adding");
    }

    private void isSortedAfterDelete(Treeish tree, Integer value) {
        isSorted(tree, value, "Deleting");
    }

    private void isSorted(Treeish tree, Integer value, String operation) {
        ValueContainer prev = null;
        List<ValueContainer> vertices = tree.asList();

        tree.updateAllHeights();

        for (ValueContainer vertex : vertices) {
            if (prev == null) {
                prev = vertex;
                continue;
            }

            if (!vertex.isBalanced()) {
                Assert.assertNull(operation + ": " + value + ": Unbalanced (left: " + vertex.getLeftHeight() + ", right: " + vertex.getRightHeight() + ") @ " + vertex, vertex);
            }

            if (prev.getValue().compareTo(vertex.getValue()) > 0) {
                Assert.fail(operation + ": " + value + ": Not sorted: " + getValuesList(vertices));
            }
        }
    }

    private List<Integer> getValuesList(List<ValueContainer> vertices) {
        List<Integer> values = new ArrayList<Integer>();

        for (ValueContainer vertex : vertices) {
            values.add(vertex.getValue());
        }

        return values;
    }

    private Treeish getIntegerAVLTree() {


        return new AVLTree();
    }


}

