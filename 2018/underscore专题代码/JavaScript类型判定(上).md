## JavaScript类型判定(上)

### 前言

在web开发的时候，我们经常会遇到判定是数字还是字符串等类型判定，对于复杂的一些数组，函数，正则，错误类型也是需求频繁，更有甚者需要判定`plainObject`和空对象，window对象。

### typeof运算符

> typeof是一元操作符，放在其单个操作数的前面，操作数可以是任意类型。返回值为表示操作类型的一个字符串。

```javascript
typeof 'hcc'  // string
```

在es6出来前，有6大基本类型（es6多了一个symbol），对于普通的类型判定，`typeof`很有效果，但是一些复杂的类型，`typeof`就会出现问题。

6大基本类型，分别是： Undefined   Null  Boolean  Number  String  Object

对应的typeof的值：      undefined  object  boolean   number   string  object  (**全部都是小写**)

```javascript
function a() {}
typeof a // function
const date = new Date()
const error = new Error()
typeof date // object 
typeof error // object
```

### Object.prototype.toString

对于这个toString这个方法，es5官方（[链接地址](https://es5.github.io/#x15.2.4.2)）是这样解释的： 

When the `**toString**` method is called, the following steps are taken:

1. If the **this** value is **undefined**, return "**[object Undefined]**".
2. If the **this** value is **null**, return "**[object Null]**".
3. Let *O* be the result of calling [ToObject](https://es5.github.io/#x9.9) passing the **this **value as the argument.
4. Let *class* be the value of the [[Class]] internal property of *O*.
5. Return the String value that is the result of concatenating the three Strings "**[object **", *class*, and "**]**".

翻译过来就是： 

1. 如果this是**undefined**，则返回 "**[object Undefined]**".
2. 如果this是**null**， 则返回"**[object Null]**".
3. 让 O 成为 ToObject(this) 的结果
4. 让 class 成为 O 的内部属性 [[Class]] 的值
5. 最后返回由 "[object " 和 class 和 "]" 三个部分组成的字符串

通过规范，我们应该知道，通过调用`Object.prototype.toString`会返回一个由”[object 和class和]“三部分组成的字符串，而class则是我们需要判定对象的内部属性。

```javascript
Object.prototype.toString.call(undefined) // "[object Undefined]"
Object.prototype.toString.call(null) // "[object Null]"
let date = new Date() 
Object.prototype.toString.call(date)  // "[object Date]"
```

`Object.prototype.toString`到底可以识别多少种类型呢？

```javascript
const number = 1 			 // [object Number]
const string = 'hcc'		 // [object String]
const boolean = false 	     // [object Boolean]
const und = undefined		 // [object Undefined]
const nul = null			 // [object Null]
const obj = {a: 1}			 // [object Object]
const array = [1,2,3]		 // [object Array]
const date = new Date()		 // [object Date]
const error = new Error()	 // [object Error]
const reg = /a/g			 // [object RegExp]
const func = function a() {} // [object Function]

function checkType() {
    for(let i=0; i<arguments.length;i++) {
        console.log(Object.prototype.toString.call(arguments[i]))
    }
}
checkType(number, string, boolean, und, nul, obj, array, date, error, reg, func)

// 附加
console.log(Object.prototype.toString.call(Math)); // [object Math]
console.log(Object.prototype.toString.call(JSON)); // [object JSON]
function a() {
    console.log(Object.prototype.toString.call(arguments)); // [object Arguments]
}
a();
```

### type函数的封装

根据Object.prototype.toString的使用，我们可以封装一个type函数，来判定不同类型的值。

```javascript
let classType = {}
"Number Boolean String Undefined Null Object Array Date Error RegExp Function".split(' ').map(type => {
    classType[`[object ${type}]`] = type.toLowerCase()
})
function type(obj) {
    return typeof obj === 'object' || typeof obj === 'function' ? classType[Object.prototype.toString.call(obj)] || 'object' : typeof obj
}
```

### 拓展

#### isFunction函数

通过上面的type函数，我们可以轻松的判定一个值是不是函数

```javascript
function isFunction(obj) {
    return type(obj) === 'function'
}
```

#### isArray函数

```javascript
const isArray = Array.isArray || function(obj) {return type(obj) === 'array'}
```

#### 结语

下一篇文章，我们将讲解plainObject和空对象、window对象