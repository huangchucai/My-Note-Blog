### underscore.js 防抖设置

在实际的工作中，我们经常会遇到限制客户多次点击，多次滑动而重复提交代码的过程，为了能够有效的防止这种情况，我们今天来讨论应该如何处理节流的控制。

#### 前言

`setTimeout`和`clearTimeout`：

```javascript
// clearTimeout 虽然取消了定时器，但是timer并没有取消，只是如果你没有引用，就会被垃圾回收。
  let timer = setTimeout('console.log(11)')
  clearTimeout(timer)
  console.log(timer) // 20  一个数字
```

#### 实例重现

我们现在用户从屏幕的一端滑动另外一端,刚开始count为 1，但是最后为165，就是说，如果这里面是一个复杂的ajax请求，用户在短短几秒的过程中，请求了接口165次，假如每次接口的返回的时间是300ms,想想，结果是什么，结果就是用户会被卡死，作为一个好的开发人员，我们是不是应该阻止这样的情况发生？

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
    
    container.addEventListener(mousemove, getUserAction)
  </script>
</body>
```

解决这样的方式一般有二种情况

1. 防抖控制`debounce`
2. 节流控制`throttle` 

#### 防抖控制

上面说了，主要就是二种情况，我们今天来讨论下防抖控制

**原理：**在一段时间内（n秒），不管用户怎么点击，我都不会触发，只有等到n秒后才会执行，如果中途n秒内，用户又再次点击，那我就以用户新点击的时间开始重新计算，n秒后才执行。总之：就是在用户触发完事件后，n秒内不再触发，我才执行事件，我就是任性！！！

#### 防抖第一版

我们根据原理可以实现一个函数：

```javascript
function debounce(func, wait) {
  let timer
  return function() {
    if(timer) clearTimeout(timer)
    timer = setTimeout(func, wait)
  }
}
container.addEventListener(mousemove, debounce(getUserAction, 1000))
```

这样处理后，在一秒内，用户重新触发事件的话，都不会执行，只有在最后一次触发1s后才会触发事件。

#### 防抖第二版

研究一个函数的时候，我们都知道**参数**和**this**，很重要，所有这里我们也需要处理`getUserAction`函数内部的this和参数。

如果我们按照第一版不处理的话，`getUserAction`内部的this就是`window`了，但是根据事件触发的this规则，this应该指向事件触发的dom元素。

*加入this：*

```javascript
function debounce(func, wait) {
  let timer, context
  return function() {
    context = this
    if(timer) clearTimeout(timer)
    timer = setTimeout(() => {
      func.apply(context)
    }, wait)
  }
}
container.addEventListener(mousemove, debounce(getUserAction, 1000))
```

处理完this后，是不是要思考每一个事件处理都是有一个事件参数e，我们也需要把这个参数e传入到`getUserAction`内部，方便我们处理。

*处理参数：*

```javascript
function debounce(func, wait) {
  let timer, context
  return function(...args) {
    context = this
    if(timer) clearTimeout(timer)
    timer = setTimeout(() => {
      func.apply(context,args)
    }, wait)
  }
}
container.addEventListener(mousemove, debounce(getUserAction, 1000))
```

#### 防抖第三版

突然老板来了一个新需求，我不想等n秒后执行，我想立刻执行事件触发，但是n秒内不再触发，自己想想好像也是合理的需求。

所有我们给`debounce`添加一个参数来控制是立即执行还是n秒后再执行

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
function debounce(func, wait, immediate) {
  let timer, context, result
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
        result = func.apply(this,args)
      } 
    } else {
      timer = setTimeout(() => {
        func.apply(context,args)
      }, wait)
    }
    return result
  }
}
```

做到这里，一个基本的防抖封装已经完成了，一大部分的情况都可以处理了，但是有没有想到过一种情况，现实工作中，就是让用户点击一个按钮，取消等待，继续触发函数？

#### 防抖第五版

最后我们再思考一个小需求，我希望能取消 debounce 函数，比如说我 debounce 的时间间隔是 10 秒钟，immediate 为 true，这样的话，我只有等 10 秒后才能重新触发事件，现在我希望有一个按钮，点击后，取消防抖，这样我再去触发，就可以又立刻执行啦，是不是很开心？

```javascript
function debounce(func, wait, immediately) {
  let timer
  let debounced =  function (...args) {
    let result
    // 清除闹钟后，闹钟还是存在的
    if (timer) clearTimeout(timer)
    if (immediately) {
      let called = !timer
      timer = setTimeout(() => {
        timer = null
      }, wait)
      if (called) {
        result = func.apply(this,args)
      }
    } else {
      timer = setTimeout(() => {
        func.apply(this, args)
      }, wait)
    }
    return result
  }
  debounced.cancel = function() {
    clearTimeout(timer)
    timer = null
  }
  return debounced
}
```

那么如何使用呢？

```javascript
let action = debounce(getUserAction, 100000, true)
container.addEventListener('mousemove', action)
btn.addEventListener('click', action.cancel)
```

恭喜你，完成了一个防抖的封装。

---

###### 源码地址

[点击这里](https://github.com/huangchucai/My-Note-Blog/blob/master/2018/%E9%98%B2%E6%8A%96.html)