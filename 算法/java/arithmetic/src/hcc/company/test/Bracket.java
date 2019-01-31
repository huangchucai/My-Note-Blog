package hcc.company.test;

import java.util.Stack;


/*
*
描述
给定一个字符串所表示的括号序列，包含以下字符： '(', ')', '{', '}', '[' and ']'， 判定是否是有效的括号序列。

样例
括号必须依照 "()" 顺序表示， "()[]{}" 是有效的括号，但 "([)]"则是无效的括号
*
*
* */
public class Bracket {
    /**
     * @param s: A string
     * @return: whether the string is a valid parentheses
     */
    public boolean isValidParentheses(String s) {
        // write your code here
        Stack<Character> stack =  new Stack<Character>();
        char[] arr = s.toCharArray();
        for(Character c: arr) {
            switch (c) {
                case '{':
                case '[':
                case '(':
                    stack.push(c);
                    break;
                case '}':
                    if(stack.empty() || (stack.peek() != '{')) {
                        return false;
                    }
                    stack.pop();
                    break;
                case ']':
                    if(stack.empty() || (stack.peek() != '[')) {
                        return false;
                    }
                    stack.pop();
                    break;
                case ')':
                    if(stack.empty() || (stack.peek() != '(')) {
                        return false;
                    }
                    stack.pop();
                    break;
            }
        }
        return stack.empty();
    }
    public static void main(String[] args) {
        Bracket bracket = new Bracket();
        System.out.println(bracket.isValidParentheses("{}"));
        System.out.println(bracket.isValidParentheses("(){}[]"));
        System.out.println(bracket.isValidParentheses("()}{}[]"));
        System.out.println(bracket.isValidParentheses("(){}[]}"));
        System.out.println(bracket.isValidParentheses("}"));
    }
}
