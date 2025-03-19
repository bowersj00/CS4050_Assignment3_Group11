package org.example.Trees;

import javafx.scene.paint.Color;

public class MinHeap<T extends Comparable<T>> extends Heap<T> {

    //protected ArrayList<T> heap = new ArrayList<>();;
    protected void heapifyUp(int index) {
        // Bubble up the element while it's smaller than its parent
        while (index > 0 && heap.get(index).compareTo(heap.get(getParentIndex(index))) < 0) {
            swap(index, getParentIndex(index));
            index = getParentIndex(index);
        }
    }

    @Override
    protected void heapifyDown(int index) {
        int size = heap.size();
        int largest = index;

        while (true) {
            // get the left and right child
            int left = getLeftChildIndex(index);
            int right = getRightChildIndex(index);

            //check if either left or right is smaller if so then swap em
            if (left < size && heap.get(left).compareTo(heap.get(largest)) < 0) {
                largest = left;
            }
            if (right < size && heap.get(right).compareTo(heap.get(largest)) < 0) {
                largest = right;
            }
            // actually do the swapping and continue until neither are smaller
            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }

    @Override
    public String type() {
        return "Min Heap";
    }

    @Override
    public Color color() {
        return Color.DARKCYAN;
    }
}
