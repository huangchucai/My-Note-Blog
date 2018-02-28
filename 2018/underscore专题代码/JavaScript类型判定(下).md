## JavaScript类型判定(下)

### 前言

上一篇文章主要是写了一些基本类型的判定方法，这篇文章将主要是讲解一些复杂的判定，比如：plainObject、空对象、window对象、isArrayLike等。

### plainObject

楼主也是第一次见过这个名词，在jQuery中被称为**“纯粹的对象”**，就是指只通过“{}”和new Object直接创建的对象，该对象含有零个或者多个键值对。

不同的地方对于它的定义和实现也不一样，这里主要以jQuery 为主，为什么会出现plainObject呢？主要是区分JavaScript的其他对象，例如： null, Array等typeof都为object的。当然你可能回想这些完全可以通过我们上一篇讲解的type函数来区分，但是自定义构造函数生成的对象，我们就不能判定了。

一些判定的的规则例子：

```javascript
function Person(name) {
   this.name = name;
}
console.log($.isPlainObject({})) 	//true
console.log($.isPlainObject(new Object)) 	//true
console.log($.isPlainObject(Object.create(null)))	//true
console.log($.isPlainObject(Object.assign({a: 1}, {b: 2})))		// true
console.log($.isPlainObject(new Person('yayu')))	// false
console.log($.isPlainObject(Object.create({})))		// false
```

从上面的例子可以看出，除了{}和new Object创建的对象外，没有一个原型链的对象也是一个**纯粹的对象**。

```javascript
function isPlainObject(obj) {
  const hasOwn = Object.prototype.hasOwnProperty; //获取对象的原型的方法
  const toString = Function.prototype.toString; // 函数的toString方法
  if (!obj && toString.call(obj) !== '[object Object]') {
    return false
  }
  const prototype = Object.getPrototypeOf(obj) // 获取到对象的原型
  // 这里是处理Object.create(null)的情况
  if (!prototype) {
    return true
   }
  const Ctr = hasOwn.call(prototype, 'constructor') && prototype.constructor // 构造函数存在并返回构造函数
  return typeof Ctr === 'function' && toString.call(Ctr) === toString.call(Object) // 判定构造函数等于Object
}

```

这里最主要的一点是，我们判定Ctr是不是Object构造函数是使用的`Function.prototype.toString`，而并不是`Object.prototype.toString`方法，二种是不同的，函数的原型重写了`toString`方法并且this必须是函数，所以我们判定构造函数的 Ctr的类型为function。[Function.prototype.toString链接地址](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Function/toString)

```javascript
hasOwn.toString.call(Ctr) 函数的toString    // function Object() { [native code] }
Object.prototype.toString.call(Ctr)    //  [object Function]
```

### EmptyObject

判定一个对象是不是空对象有很多方法，下面分别使用

1. jQuery中提供的isEmptyObject方法

   ```javascript
   function isEmptyObject(obj) {
   	var name 
       // 如果for in循环开始，就证明不是空对象
       for (name in obj) {
           return false
       }
       return true
   }
   ```

2. 通过Object.getOwnPropertyNames()来获取属性

   ```javascript
   function isEmptyObject(obj) {
       return Object.getOwnPropertyNames(obj).length ? false : true
   }
   ```

   上面的都是默认传入的是对象，可以通过上一篇文章的type函数结合。

### Window对象

window对象作为客户端JavaScript的全局对象，它有一个window属性指向自身。我们可以利用这一点判定是不是window对象

```javascript
function isWindow(obj) {
    return obj != null && obj.window === obj;  //这里要判定不是null和undefined
}
```

### isArrayLike

看这个名字就知道，这个函数是判定类数组对象和数组的区分的，但是jQuery中，类数组和数组对象都返回true

```javascript
function isArrayLike(obj) {
    // 首先必须要有length属性
    let length = !!obj && "length" in obj && obj.length
    // 排除本身就有length属性 function  window
    let typeRes = type(obj)
    if(typeRes === 'function' || typeRes === 'window') {
        return false
    }
    return typeRes === 'array' || length === 0 || typeof length === 'number' && length > 0 && (length-1) in obj
}
```

最后一行我们判定了isArrayLike满足的三个条件之一

1. 是数组
2. 长度为0
3. length属性必须是一个大于0的数字类型并且length-1必须是obj的属性（obj[length-1]必须存在）

第一条可能没有任何疑问，但是第二条有人可能就有疑问了，例如：

```javascript
let obj = {a: 1, b: 2, length: 0}
isArrayLike(obj)  // true
```

这里为什么会返回true呢？合不合理呢？先不说合不合理，看下面一种情况

```javascript
function a () {
   console.log(isArrayLike(arguments)) 
}
a()
```

如果我们去掉length === 0这个情况，这里就会输出false，所以这里就是一个取舍，为了兼容没有参数的类数组对象。具体的还是可以根据需求来实现。

对于第三条可能很多人都不懂，这是应该对象和数组对于一些特殊的值的处理方式不一样

```javascript
let arr1 = [,,3]  // arr1.length = 3  
// arr1[0]  undefined
```

装换成对应的类数组对象

```javascript
let arr1Like = {
    2: 3,
    length: 3
}	
```

也就是说，对于数组中使用逗号的时候，类数组对象会直接跳过，认为这个元素不存在，但是最后一个元素是必须的，length属性就是等于最后一个元素的key加上1

```javascript
let arr2 = [1,,]
console.log(arr2.length) // 2   会过滤掉最后一个没有值的

let arr2Like = {
    0: 1,
    length: 1
}
```

从上面的例子中可以看出，length和元素的key是有加一的联系

### isElement

isElement是判定参数是不是DOM元素

```javascript
function isElement(obj) {
    return !!(obj && obj.nodeType === 1))
}
```

这里基本的判定和复杂的判定我们都有部分的处理，相信大家在工作过程中都可以用得着，大家一起学习，欢迎交流。

