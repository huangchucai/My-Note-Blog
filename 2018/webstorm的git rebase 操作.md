## webstorm的git rebase 操作

1. 我们首先从master分支开出一个我们平时开发的分支

   ```javascript
   git checkout -b rebase_2018 
   // 此时rebase.md中的内容是这个
   ### 这是rebase学习使用
   1. 这是master分支的内容
   ```

2. 现在我们已经切换到**rebase_2018的分支**上面了

   ```javascript
   // 此时我们切换到rebase_2018分支
   ### 学习rebase
   1. 这是master分支
   ```

3. 假如此时master分支由于别人的合并发生了变化

   ```javascript
   // 此时master分支中的rebase.md 为
   ### 这是rebase学习使用
   1. 这是master分支
   2. 这是master分支的第一次改动
   ```

4. 我们再切回rebase_2018

   ```javascript
   git checkout rebase_2018
   // 对rebase.md 进行三次修改
   ### 学习rebase
   1. 这是master分支
   2. 这是rebase分支的第一次修改
   3. 这是rebase分支的第二次修改
   4. 这是rebase分支的第三次修改
   
   
   ```

   ![image-20180708224752559](/var/folders/bp/fy2twk9s23b3l_csblqcqpg00000gp/T/abnerworks.Typora/image-20180708224752559.png)

5. 使用webstorm的rebase功能，对master进行合并

   ![image-20180708224921110](/var/folders/bp/fy2twk9s23b3l_csblqcqpg00000gp/T/abnerworks.Typora/image-20180708224921110.png)

6. 由于我们在rebase_2018内修改了rebase.md的文件内容，而master分支由于也修改了rebase.md的内容，产生了冲突。

   * 这是rebase_2018的第一次提交的内容

![image-20180708232016257](/var/folders/bp/fy2twk9s23b3l_csblqcqpg00000gp/T/abnerworks.Typora/image-20180708232016257.png)

 * 我们把中间的result的内容改成这个

   ![image-20180708232238938](/var/folders/bp/fy2twk9s23b3l_csblqcqpg00000gp/T/abnerworks.Typora/image-20180708232238938.png)

* 处理过第一次的内容冲突，接下来会进行我们在rebase_2018的第二次提交的内容

  ![image-20180708232558285](/var/folders/bp/fy2twk9s23b3l_csblqcqpg00000gp/T/abnerworks.Typora/image-20180708232558285.png)

* 我们对第二次的冲突进行处理

  ![image-20180708232835939](/var/folders/bp/fy2twk9s23b3l_csblqcqpg00000gp/T/abnerworks.Typora/image-20180708232835939.png)

* 最后的结果就变成了这样

  ![image-20180708232953610](/var/folders/bp/fy2twk9s23b3l_csblqcqpg00000gp/T/abnerworks.Typora/image-20180708232953610.png)

7. 此时我们来查看rebase_2018的提交记录

   ```javascript
   git log 
   ```

   ![image-20180708233414012](/var/folders/bp/fy2twk9s23b3l_csblqcqpg00000gp/T/abnerworks.Typora/image-20180708233414012.png)

### rebase流程图

![rebase流程图](/Users/huangchucai/Downloads/rebase流程图.png)