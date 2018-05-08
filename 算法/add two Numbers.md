### LeetCode 2. Add Two Numbers

### Description

You are given two **non-empty** linked lists representing two non-negative integers. The digits are stored in **reverse order** and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.

You may assume the two numbers do not contain any leading zero, except the number 0 itself.

**Example:**

```
Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
Output: 7 -> 0 -> 8

Explanation: 342 + 465 = 807.
```

### 翻译

有两个连结阵列分别代表两个非负整数，他们的位数是反向储存(越前面的节点位数越低)，毎一个节点代表一个位数，将这两个连结阵列加总后以连结阵列形式回传。

### 思路

1. 就只是用一个新的linked list来储存相加后的结果
2.  要注意的就是list1跟list2长度可能不一样
3. 另外就是相加后可能比9还大，需要考虑进位的情况

```javascript
/**
 * Definition for singly-linked list.
 * function ListNode(val) {
 *     this.val = val;
 *     this.next = null;
 * }
 */
/**
 * @param {ListNode} l1
 * @param {ListNode} l2
 * @return {ListNode}
 */
var addTwoNumbers = function(l1, l2) {
    var list = new ListNode(0); //儲存輸出的結果，因為list的指針要不斷往後移，因此用一個假節點方便操作
    var result = list; // 使用一個ListNode來儲存相加的結果

    var sum,carry = 0; // carry用來處理進位

    //當 list1, list2 都沒有值，而且carry也為0的時候才結束迴圈
    while(l1 || l2 || carry > 0){
        sum = 0;

        // list1與list2長度可能不同，分開處理
        if(l1!== null){
            sum += l1.val;
            l1 = l1.next;
        }

        if(l2!==null){
            sum += l2.val;
            l2 = l2.next;
        }

        // 如果之前有進位，carry = 1；沒有的話carry = 0
        sum += carry;
        list.next = new ListNode(sum%10); //相加如果超過9，只能留下個位數放入結果list，十位數的地方進位
        carry = parseInt(sum/10);

        // list指標向後
        list = list.next;
    }
    // 因為第一個節點為假節點，跳過
    return result.next;
}
```

总结： 这个算法题目对于题目理解不够，不明白什么是连结阵列？

