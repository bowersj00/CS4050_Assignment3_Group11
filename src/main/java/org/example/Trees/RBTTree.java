package org.example.Trees;
//Auther: Ting-Ting Yao

import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RBTTree<T extends Comparable<T>> implements Tree<T> , Serializable {
    private Node root;
    private int size;

    private class Node implements TreeNode<T> , Serializable{
        T value;
        Node left, right, parent;
        String color;

        Node(T value, String color, Node parent) {
            this.value = value;
            this.color = color;
            this.parent = parent;
            this.left = null;
            this.right = null;
        }

        @Override
        public T getValue() { return value; }

        @Override
        public TreeNode<T> getLeft() { return left; }

        @Override
        public TreeNode<T> getRight() { return right; }

        public String getColor() { return color; }
    }

    @Override
    public Color color() {
        return Color.PURPLE;
    }

    @Override
    public void insert(T value) {
        if (root == null) {
            root = new Node(value, "BLACK", null); // Root must always be black
            size++;
            return;
        }
        Node insertedNode = insert(root, value, null);
        //root = insert(root, value);
        fixInsert(insertedNode);
        size++;
    }

    private Node insert(Node node, T value, Node parent) {
        if (node == null) {
            return new Node(value, "RED", parent);
        }
        if (value.compareTo(node.value) < 0) {
            node.left = insert(node.left, value, node);
            node.left.parent = node;
        } else if (value.compareTo(node.value) > 0) {
            node.right = insert(node.right, value, node);
            node.right.parent = node;
        }
        return node;
    }

    private RBTTree.Node rightRotate(RBTTree.Node node) {
        RBTTree.Node leftChild = node.left;
        node.left = leftChild.right;
        if (leftChild.right != null){
            leftChild.right.parent = node;
        }

        leftChild.parent = node.parent;
        if (node.parent == null) {
            root = leftChild;
        } else if (node == node.parent.left) {
            node.parent.left = leftChild;
        } else {
            node.parent.right = leftChild;
        }

        //Perform rotation
        leftChild.right = node;
        node.parent = leftChild;

        //Return the new root, which should be set as the passed node
        return leftChild;
    }

    //Function to perform a left rotation.
    //Returns the new root which should be set as the passed node
    private RBTTree.Node leftRotate(RBTTree.Node node) {
        RBTTree.Node rightChild = node.right;
        node.right = rightChild.left;
        if (rightChild.left != null) {
            rightChild.left.parent = node;
        }

        rightChild.parent = node.parent;
        if (node.parent == null) {
            root = rightChild;
        } else if (node == node.parent.left) {
            node.parent.left = rightChild;
        } else {
            node.parent.right = rightChild;
        }

        //Perform rotation
        rightChild.left = node;
        node.parent = rightChild;

        //Return new root, which should be assigned as the passed node
        return rightChild;
    }

    private void fixInsert(Node node) {
        while (node != root && node.parent.color.equals("RED")) {
            Node grandparent = node.parent.parent;
            if (node.parent == grandparent.left) {
                Node uncle = grandparent.right;
                //case 1: recolor nodes
                if (uncle != null && uncle.color.equals("RED")) {
                    grandparent.color = "RED";
                    node.parent.color = "BLACK";
                    uncle.color = "BLACK";
                    node = grandparent;
                } else { //case 2: left and right rotation
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    //case 3: right rotation
                    node.parent.color = "BLACK";
                    grandparent.color = "RED";
                    rightRotate(grandparent);
                }
            } else {
                Node uncle = grandparent.left;
                //case 1: recolor nodes
                if (uncle != null && uncle.color.equals("RED")) {
                    grandparent.color = "RED";
                    node.parent.color = "BLACK";
                    uncle.color = "BLACK";
                    node = grandparent;
                } else {
                    //case 2: right and left rotation
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    //case 3: left rotation
                    node.parent.color = "BLACK";
                    grandparent.color = "RED";
                    leftRotate(grandparent);
                }
            }
        }
        root.color = "BLACK";
    }

    @Override
    public String type() {
         return "RBT";
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