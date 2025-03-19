package org.example.Trees;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class MaxHeap<T extends Comparable<T>> extends Heap<T> {

    //protected ArrayList<T> heap = new ArrayList<>();;
    protected void heapifyUp(int index) {
        // Bubble up the element while it's greater than its parent
        while (index > 0 && heap.get(index).compareTo(heap.get(getParentIndex(index))) > 0) {
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

            //find the larger of the left and right and swap
            if (left < size && heap.get(left).compareTo(heap.get(largest)) > 0) {
                largest = left;
            }
            if (right < size && heap.get(right).compareTo(heap.get(largest)) > 0) {
                largest = right;
            }
            // actually do the swapping and continue until neither are larger
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
        return "Max Heap";
    }

    @Override
    public Color color() {
        return Color.PINK;
    }
}
