package hcc.company.study.StackStudy;

import java.util.Stack;

/*
* 用栈实现队列
*
使用栈实现队列的下列操作:
push(x) -- 将一个元素放入队列的尾部。
pop() -- 从队列首部移除元素。
peek() -- 返回队列首部的元素。
empty() -- 返回队列是否为空。
*
*
* */
public class MyQueue {
    private Stack<Integer> firstStack;
    private Stack<Integer> secondStack;

    public MyQueue(Stack<Integer> firstStack, Stack<Integer> secondStack) {
        this.firstStack = new Stack<Integer>();
        this.secondStack = new Stack<Integer>();
    }

    public void push(int x) {
        firstStack.push(x);
    }

    public int pop() {
        shiftStack();
        return secondStack.pop();
    }

    public int peek() {
        shiftStack();
        return secondStack.peek();
    }

    private void shiftStack() {
        if (secondStack.isEmpty()) {
            // 如果第一个栈存在的话
            while (!firstStack.isEmpty()) {
                secondStack.push(firstStack.pop());
            }
        }
    }
    boolean isEmpty() {
        return firstStack.isEmpty() && secondStack.isEmpty();
    }
}
