package hcc.company.study.StackStudy;

import java.lang.reflect.Array;

/*
 * push()  放元素到栈的顶部
 * pop()   把栈的顶部元素弹出来
 * peek()  查看栈顶的元素
 * isEmpty() 判定元素的是否为空
 * */
//
public class MyStack<T> {
    private int size;
    private T[] elementData;
    private int capacity;

    public MyStack(int capacity) {
        this.size = 0;
        this.capacity = capacity;
        if (capacity == 0) {
            this.capacity = 10;
        }
        this.elementData = (T[]) new Object[this.capacity];
    }

    public MyStack() {
        this(0);
    }

    public void push(T element) {
        if (size == capacity) {
            resize();
        }
        elementData[size++] = element;
    }


    public T pop() {
        if (size == 0) {
            new IndexOutOfBoundsException("");
        }
        return elementData[--size];
    }

    public T peek() {
        if (size == 0) {
            return null;
        }
        return elementData[size - 1];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private void resize() {
        int newCapacity = capacity * 10;
        T[] newElementData = (T[]) new Object[newCapacity];
        System.arraycopy(elementData, 0, newElementData, 0, size-1);
        elementData = newElementData;
        capacity = newCapacity;
    }

}
