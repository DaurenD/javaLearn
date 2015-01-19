package AVLTree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dauren on 1/8/2015.
 */
public class AVLTree implements Treeish{
    public static void main(String[] args) {
        AVLTree tree = new AVLTree();
        for (int i = 0; i < 10; i++) {
            tree.add(i);
        }

        System.out.println(tree.top.right.right.right.toString());
        System.out.println(tree.top.getValue());
        System.out.println(tree.top.getHeight());
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

        balance(placeToAdd);
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
        if (last.getLeftHeight() > last.getRightHeight()) {
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
            pivot.parent = unblncd.parent;
            if (unblncd.isLeft()) {
                unblncd.parent.setLeft(pivot);
            } else {
                unblncd.parent.setRight(pivot);
            }
        }
        //setting children
        unblncd.setRight(pivot.left);
        pivot.setLeft(unblncd);
    }

    private void rotateLeft(Node last, Node unblncd) {
        if (!last.isLeft()) {
            rotateRight(last, unblncd.left);
        }

        Node pivot = unblncd.left;
        unblncd.setLeft(pivot.right);
        pivot.setRight(unblncd);

        if (unblncd.parent == null) {
            top = pivot;
        } else {
            pivot.parent = unblncd.parent;
            if (unblncd.isLeft()) {
                unblncd.parent.setLeft(pivot);
            } else {
                unblncd.parent.setRight(pivot);
            }
        }
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

        if (toRemove.left.isEmpty()) {
            //replace toRemove with right
            replace(toRemove, toRemove.right);
        } else {
            //find max in left subtree
            //and this node with max value can only have left child. It can be empty or not

            Node toReplace = getMax(toRemove.left);

            replace(toRemove, toReplace);
            if (!toReplace.left.isEmpty()) {
                replace(toReplace, toReplace.left);
            }
        }

    }

    private void replace(Node toReplace, Node node) {

    }

    private Node getMax(Node left) {
        while (!left.right.isEmpty()) {
            left = left.right;
        }

        return left;
    }

    @Override
    public boolean contains(Integer i) {
        return false;
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

    }

    private class Node implements ValueContainer {
        private Integer value;

        private Node parent;
        private Node left;
        private Node right;



        private void setValue(Integer value) {
            this.value = value;
            setLeft(new Node());
            setRight(new Node());
        }

        private void setRight(ValueContainer right) {
            this.right = (Node) right;
            ((Node) right).parent = this;
        }

        private void setLeft(ValueContainer left) {
            this.left = (Node) left;
            ((Node) left).parent = this;
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
        public int getHeight() {
            int leftHeight = (left == null || left.isEmpty()) ? 0 : left.getHeight();
            int rightHeight = (right == null || right.isEmpty()) ? 0 : right.getHeight();
            return 1 + (leftHeight > rightHeight? leftHeight : rightHeight);
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
            return Math.abs(getLeftHeight() - getRightHeight()) < 2;
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

            if (right != null)
                list.addAll(right.asList());
            return list;
        }

        @Override
        public String toString() {
            return isEmpty()
                    ? "empty"
                    : value + (isLeft() ? " (-" : " (") + getHeight() + (!isLeft() ? "-)" : ")") + " -> " + (parent != null ? parent : "top");
        }
    }
}
