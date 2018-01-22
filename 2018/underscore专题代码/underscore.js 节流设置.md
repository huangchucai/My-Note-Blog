## underscore.js 节流设置

在实际的工作中，我们经常会遇到向下滑动加载数据的需求，考虑到性能的需求，我们不可能让用户不停的请求接口，需要控制用户请求接口的频率。

#### 前言

上一节，我们讲过函数的防抖，一定很好奇防抖和节流有什么区别呢？分别有什么用处呢？

节流和防抖的区别我们可以用一个例子来解释：

**防抖：** 我们坐电梯，每次上一个人我们都要重新的等待10s，直到没有人上了，10s后启动电梯

**节流：** 不管是不是一直有人上电梯，电梯10s后启动 。

> *应用：*
>
> **防抖：**
>
> 1.  一般用于用户输入框输入完成后，一段时间后请求接口，中间输入重新的计时
> 2. 窗口改变后，计算一些页面的部分
>
> **节流：**
>
> 1. 用户下拉加载。

### 节流实现

节流一般都是通过2种方式来实现

1. 时间戳
2. setTimeout

#### 时间戳

*原理*：当用户触发的时候，我们取出当前的时间戳，然后减去之前的时间戳（刚开始的时候为0），如果大于设置的时间周期，就执行函数，并更新时间戳为当前的时间戳，如果小于，就不执行。

```html
  <style>
    .container {
      background-color: black;
      color: white;
      padding: 100px 0;
      text-align: center;
    }
  </style>
</head>

<body>
  <div class="container"></div>
  <script>
    let count = 1
    const container = document.querySelector('.container');
    function getUserAction() {
      container.innerHTML = count++;
      return 'getUserAction'
    };
    function throttle(func, wait) {
      let previous = 0
      return function () {
        let date = +new Date()
        if (date - previous > wait) {
          previous = date 
          func.apply(this, [...arguments])
        }
      }
    }
    container.addEventListener(mousemove, throttle(getUserAction, 1000))
  </script>
</body>
```

这里我们可以得到，刚开始进入页面的时候，就会执行一次函数，之后等到时间戳差满足我们规定的`wait`的时候，才会执行。

#### 使用定时器

##### 第一版

*原理：* 当事件触发的时候，我们设置一个定时器，再触发事件的时候，如果定时器存在，就不执行，直到定时器执行，然后执行回调函数，清空定时器，这样就可以进行下一个定时器。

```javascript
function throttle(func, wait) {
  let timer = null;
  return function () {
    if (!timer) {
      timer = setTimeout(() => {
        timer = null
        func.apply(this, [...arguments])
      }, wait)
    }
  }
}
container.addEventListener(mousemove, throttle(getUserAction, 1000))
```

这样处理后，在一秒后就才会执行函数，之后每隔一秒执行一次函数，并不会刚开始进来的时候就执行，但是最后离开的时候也会执行一个定时器。

##### 第二版

基于第一版和时间戳的对比：

1. 时间戳是刚开始进去就执行函数，离开不会执行函数
2. 第一版是刚开始进去不执行函数，离开的时候会执行函数。

能不能把2种综合一下，既要离开的时候执行，也要刚开始进来的时候执行。

```javascript
function throttle(func, wait) {
  let timer = null, previous = 0;
  function later() {
    timer = null
    previous = +new Date()
    func.apply(this, [...arguments])
  }
  return function () {
    let date = +new Date()
    // 距离函数执行的时间
    let remain = wait - (date - previous)
    // 刚开始的时候执行
    if (remain <= 0) {
      if (timer) {
        clearTimeout(timer)
        timer = null
      }
      previous = date
      func.apply(this, [...arguments])
    } else if (!timer) {
      // 之后都大部分会执行定时器里面的调用
      timer = setTimeout(later, remain)
    }
  }
}
```

##### 第三版

合理的api应该是让开发者可以选择，在第二版的基础上，我们添加一个options来控制不同的需求。

```javascript
leading：false 表示禁用第一次执行

trailing: false 表示禁用停止触发的回调

```

```javascript
function debounce(func, wait, immediate) {
  let timer, context
  return function(...args) {
    context = this
    if(timer) clearTimeout(timer)
    if(immediate) {
      // 控制是否已经执行过
      call = !timer
      timer = setTimeout(() => {
        timer = null
      },wait)
      if(call) {
        func.apply(this,args)
      } 
    } else {
      timer = setTimeout(() => {
      	func.apply(context,args)
      }, wait)
    }
  }
}
```

这样处理后，我们就可以根据`immediate`的值，来用2种方法来执行。

#### 防抖第四版

每一个函数都有一个返回值，`getUserAction`的返回值如果需要被利用呢？当不是直接执行的时候（`immediate=false`）讨论返回值没有意义，一直都是`undefined`(异步)，所以只有立即执行的时候，才有返回值。

```javascript
function throttle(func, wait, options = {leading: false,trailing: false}) {
  let timer = null, previous = 0;
  function later() {
    timer = null
    previous = options.leading ? +new Date() : 0
    func.apply(this, [...arguments])
  }
  return function () {
    let date = +new Date()
    if (!previous && options.leading === false) previous = date
    // 距离函数执行的时间
    let remain = wait - (date - previous)
    // 刚开始的时候执行
    if (remain <= 0) {
      if (timer) {
        clearTimeout(timer)
        timeout = null;
      }
      previous = date
      func.apply(this, [...arguments])
    } else if (!timer && options.trailing !== false) {
      timer = setTimeout(later, remain)
    }
  }
}
```

做到这里，一个基本的防抖封装已经完成了，一大部分的情况都可以处理了，但是有没有想到过一种情况，现实工作中，就是让用户点击一个按钮，取消等待，继续触发函数？

---



###### 源码地址

[点击这里](https://github.com/huangchucai/My-Note-Blog/blob/master/2018/%E9%98%B2%E6%8A%96.html)