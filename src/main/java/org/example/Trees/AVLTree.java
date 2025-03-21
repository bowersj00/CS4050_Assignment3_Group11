package org.example.Trees;
//Auther: Justin Bowers
import javafx.scene.paint.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AVLTree<T extends Comparable<T>> implements Tree<T> , Serializable {
    private Node root;
    private int size;

    private class Node implements TreeNode<T> , Serializable{
        T value;
        Node left, right;
        Integer height;

        Node(T value) {
            this.value = value;
        }

        @Override
        public T getValue() { return value; }

        @Override
        public Node getLeft() { return left; }

        @Override
        public Node getRight() { return right; }

        public String getColor() { return null; }
    }

    @Override
    public Color color() { return Color.DARKBLUE; }

    @Override
    public void insert(T value) {
        root = insert(root, value);
        size++;
    }

    //Function to perform a right rotation.
    //Returns the new root which should be set as the passed node
    private Node rightRotate(Node node) {
        //Store temporary nodes for left child and left child's right subtree
        Node leftChild = node.left;
        Node temp = leftChild.right;

        //Perform rotation
        leftChild.right = node;
        node.left = temp;

        //Update heights of modified nodes
        updateTreeHeights(node);
        updateTreeHeights(leftChild);

        //Return the new root, which should be set as the passed node
        return leftChild;
    }

    //Function to perform a left rotation.
    //Returns the new root which should be set as the passed node
    private Node leftRotate(Node node) {
        //Store temporary nodes for right child and right child's left subtree
        Node rightChild = node.right;
        Node temp = rightChild.left;

        //Perform rotation
        rightChild.left = node;
        node.right = temp;

        //Update the heights of modified nodes
        updateTreeHeights(node);
        updateTreeHeights(rightChild);

        //Return new root, which should be assigned as the passed node
        return rightChild;
    }

    //Function which calculates the height of a given node
    private Integer getHeight(Node node) {
        //Base case
        if (node == null) {
            return 0;
        } else {
            //Recursively get left and right heights, then return max plus one (for self)
            int leftDepth = getHeight(node.left);
            int rightDepth = getHeight(node.right);
            return Math.max(leftDepth, rightDepth) + 1;
        }
    }

    //Function to update the height member stored with each node
    private void updateTreeHeights(Node node) {
        node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;
    }

    //Function to return the "balance" of a node, which is the difference between its left and right children
    private int getBalanceOfNode(Node node) {
        if (node == null) { return 0; }
        return getHeight(node.getLeft()) - getHeight(node.getRight());
    }

    //Function to balance the tree
    private Node balance(Node node) {
        //Check for a larger left subtree and perform either a right or left then right rotation
        if (getBalanceOfNode(node) > 1) {
            if (getBalanceOfNode(node.left) < 0) {
                node.left = leftRotate(node.left);
            }
            return rightRotate(node);
        }

        //Check for a larger right subtree and perform either a right or left then right rotation
        if (getBalanceOfNode(node) < -1) {
            if (getBalanceOfNode(node.right) > 0) {
                node.right = rightRotate(node.right);
            }
            return leftRotate(node);
        }

        //Base case where tree is already balanced, nothing to do
        return node;
    }

    //Function to insert a new value at a node in a tree
    private Node insert(Node node, T value) {
        //Check for null, allows creation of the root of the tree
        if (node == null) {
            return new Node(value);
        }
        //Recursively insert to the correct location within binary tree
        if (value.compareTo(node.value) < 0) {
            node.left = insert(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = insert(node.right, value);
        } else {
            return node;
        }

        //Update height after insertion
        updateTreeHeights(node);

        //Balance the new tree and return the new root node after balancing
        return balance(node);
    }

    @Override
    public String type() {
        return "AVLTree";
    }

    @Override
    public boolean delete(T value) {
        int originalSize = size;
        root = delete(root, value);
        return size < originalSize;
    }

    private Node delete(Node node, T value) {
        if (node == null) {
            return null;
        }
        if (value.compareTo(node.value) < 0) {
            node.left = delete(node.left, value);
        } else if (value.compareTo(node.value) > 0) {
            node.right = delete(node.right, value);
        } else {
            if (node.left == null) {
                size--;
                return node.right;
            } else if (node.right == null) {
                size--;
                return node.left;
            }
            Node minRight = findMin(node.right);
            node.value = minRight.value;
            node.right = delete(node.right, minRight.value);
        }
        balance(node);
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    @Override
    public boolean contains(T value) {
        return contains(root, value);
    }

    private boolean contains(Node node, T value) {
        if (node == null) {
            return false;
        }
        if (value.compareTo(node.value) == 0) {
            return true;
        } else if (value.compareTo(node.value) < 0) {
            return contains(node.left, value);
        } else {
            return contains(node.right, value);
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> inorderTraversal() {
        List<T> result = new ArrayList<>();
        inorderTraversal(root, result);
        return result;
    }

    private void inorderTraversal(Node node, List<T> result) {
        if (node != null) {
            inorderTraversal(node.left, result);
            result.add(node.value);
            inorderTraversal(node.right, result);
        }
    }

    @Override
    public TreeNode<T> getRoot() {
        return root;
    }
}