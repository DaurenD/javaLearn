package learnJava.avltree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dauren on 1/8/2015.
 */
public class AVLTree implements Treeish{
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        for (int i = 1; i <= 22; i++) {
            tree.add(i);
        }
        AVLTreePrinter.printTree(tree.top);
        tree.remove(9);
        AVLTreePrinter.printTree(tree.top);

    }



    private Node top = new Node();

    @Override
    public void add(Integer i) {
        if (i == null) {
            return;
        }

        Node placeToAdd = findPlace(i);
        placeToAdd.setValue(i);

        updateHeights(placeToAdd);
        balance(placeToAdd);
    }

    private void updateHeights(Node node) {
        while (node != null && node.parent != null) {
            if (node.isLeft()) {
                node.parent.leftHeight = node.getHeight();
            } else {
                node.parent.rightHeight = node.getHeight();
            }
            node = node.parent;
        }
    }

    private void balance(Node last) {
        Node curr = last;
        while (curr != null && !curr.isEmpty()) {
            if (!curr.isBalanced()) {
                rotate(last, curr);
                break;
            }
            curr = curr.parent;
        }
    }

    private void rotate(Node last, Node unblncd) {
        if (unblncd.leftHeight > unblncd.rightHeight) {
            rotateLeft(last, unblncd);
        } else {
            rotateRight(last, unblncd);
        }
    }

    private void rotateRight(Node last, Node unblncd) {
        if (last.isLeft()) {
            rotateLeft(last, unblncd.right);
        }

        Node pivot = unblncd.right;
        //setting parent
        if (unblncd.parent == null) {
            top = pivot;
            pivot.parent = null;
        } else {
            if (unblncd.isLeft()) {
                unblncd.parent.setLeft(pivot);
            } else {
                unblncd.parent.setRight(pivot);
            }
        }
        //setting children
        unblncd.setRight(pivot.left);
        pivot.setLeft(unblncd);

        pivot.updateHeights();
    }

    private void rotateLeft(Node last, Node unblncd) {
        if (!last.equals(unblncd)) {
            if (!last.isLeft()) {
                rotateRight(last, unblncd.left);
            }
        } else {
            if (!unblncd.left.right.isLeft()) {
                rotateRight(unblncd.left.right, unblncd.left);
            }
        }
        System.out.println();
        Node pivot = unblncd.left;

        if (unblncd.parent == null) {
            top = pivot;
            pivot.parent = null;
        } else {
            if (unblncd.isLeft()) {
                unblncd.parent.setLeft(pivot);
            } else {
                unblncd.parent.setRight(pivot);
            }
        }
        unblncd.setLeft(pivot.right);
        pivot.setRight(unblncd);

        pivot.updateHeights();
    }

    private Node findPlace(Integer value) {
        Node node = top;

        while (!node.isEmpty() && !node.value.equals(value)) {
            if (node.value > value) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        return node;
    }

    /**
     * Searches node with value @param i, replaces node with child which has max value
     * @param i - value to remove
     */
    public void remove(Integer i) {
        // Node to delete
        Node toRemove = findPlace(i);
        if (toRemove.isEmpty()) {
            System.out.println("Not found");
            return;
        }

        Node parent;

        //if toRemove located on the edge or
        if (toRemove.left.isEmpty() && toRemove.right.isEmpty()) {
            parent = removeEdge(toRemove);
        } else {
            Node newNode = getMax(toRemove.left);

            if (newNode.isEmpty()) {
                parent = removeWithRight(toRemove);
            } else {
                parent = removeNode(toRemove, newNode);
            }
        }
        updateAllHeights();
//        updateHeights(parent);
        balance(parent);
    }

    private Node removeEdge(Node toRemove) {
        if (toRemove == top) {
            top = new Node();
            return top;
        }
        Node parent = toRemove.parent;
        if (toRemove.isLeft()) {
            parent.setLeft(new Node());
        } else {
            parent.setRight(new Node());
        }
        toRemove.parent = null;
        return parent;
    }

    private Node removeWithRight(Node toRemove) {
        Node rightChild = toRemove.right;
        if (toRemove.parent != null) {
            if (toRemove.isLeft()) {
                toRemove.parent.setLeft(rightChild);
            }else {
                toRemove.parent.setRight(rightChild);
            }
        }

        return toRemove.parent;
    }

    private Node removeNode(Node toRemove, Node newNode) {
        Node left = newNode.left;
        //replace values
        toRemove.value = newNode.value;
        if (newNode.isLeft()) {
            newNode.parent.setLeft(left);
        }else {
            newNode.parent.setRight(left);
        }

        return newNode.parent;
    }

    private Node getMax(Node left) {
        while (!left.isEmpty() && !left.right.isEmpty()) {
            left = left.right;
        }

        return left;
    }

    @Override
    public boolean contains(Integer i) {
        Node node = findPlace(i);
        return !node.isEmpty() && node.value.equals(i);
    }

    @Override
    public ValueContainer getTop() {
        return top;
    }

    @Override
    public List<ValueContainer> asList() {
        return top.asList();
    }

    @Override
    public void updateAllHeights() {
        top.updateHeights();
    }


}
class Node implements ValueContainer {
     Integer value;

     Node parent;
    Node left;
    Node right;

    int leftHeight;
    int rightHeight;


    public void setValue(Integer value) {
        this.value = value;
        setLeft(new Node());
        setRight(new Node());
    }

    public void setRight(ValueContainer right) {
        if (right != null) {
            this.right = (Node) right;
            ((Node) right).parent = this;
        } else {
            setRight(new Node());
        }
    }

    public void setLeft(ValueContainer left) {
        if (left != null) {
            this.left = (Node) left;
            ((Node) left).parent = this;
        } else {
            setLeft(new Node());
        }
    }
    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public ValueContainer getParent() {
        return parent;
    }

    @Override
    public ValueContainer getLeft() {
        return left;
    }

    @Override
    public ValueContainer getRight() {
        return null;
    }

    @Override
    public void setParent(ValueContainer parent) {

    }

    @Override
    public int getHeight() {
        return isEmpty() ? 0 : 1 + (leftHeight > rightHeight ? leftHeight : rightHeight);
    }

    public void updateHeights() {
        if (isEmpty()) return;

        right.updateHeights();
        left.updateHeights();

        rightHeight = right.getHeight();
        leftHeight = left.getHeight();

    }

    @Override
    public int getLeftHeight() {
        return left.getHeight();
    }

    @Override
    public int getRightHeight() {
        return right.getHeight();
    }

    @Override
    public boolean isBalanced() {
        return Math.abs(leftHeight - rightHeight) < 2;
    }

    @Override
    public boolean isEmpty() {
        return value == null;
    }

    @Override
    public boolean isLeft() {
        return (parent != null) && parent.left.equals(this);
    }

    @Override
    public List<ValueContainer> asList() {
        if (isEmpty()) {
            return new ArrayList<ValueContainer>();
        }
        List<ValueContainer> list = (left != null) ? left.asList() : new ArrayList<ValueContainer>();

        list.add(this);

        if (right != null){
            list.addAll(right.asList());
        }
        return list;
    }

    @Override
    public String toString() {
        return isEmpty()
                ? "empty"
                : value + (isLeft() ? " (-" : " (") + getHeight() + (!isLeft() ? "-)" : ")") + " -> " + (parent != null ? parent : "top");
    }

}