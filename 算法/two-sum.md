### LeetCode 1. Two Sum

### Description

Given an array of integers, return **indices** of the two numbers such that they add up to a specific target.

You may assume that each input would have **exactly** one solution, and you may not use the *same* element twice.

**Example:**

```
Given nums = [2, 7, 11, 15], target = 9,

Because nums[0] + nums[1] = 2 + 7 = 9,
return [0, 1].
```

### 翻译

给一个整数的数组，返回其中2个数字相加等于目标值的索引

### 思路

这一题是算法的入门题目，首先可以想到的是利用双循环，如果nums[i] + num[j] = target就返回i，j

```javascript
var twoSum = function(nums, target) {
    const indexArr = []
    for(let i = 0; i < nums.length; i++) {
        let firstNum = nums[i]
        for(let j = i + 1; j < nums.length; j++) {
            if(firstNum + nums[j] === target) {
                indexArr.push(i,j)
                return indexArr
            }
        }
    }
};
```

### 进阶

看了一下网上了参考，只能说大神还是很多的，可以利用对象来减少一次循环

```java
var twoSum = function(nums, target) {
  const map = {} // 存放出现过的值和索引  {值 ： 索引}
  for(let i = 0; i < nums.length; i++) {
    let value = nums[i]
    if(map[target - value] >= 0) {
      // 如果target - value 可以在map中找到，证明之前出现过，所以应该在i的前面
      return [map[target - value], i]
    } else {
      map[value] = i
    }
  }
}
```

