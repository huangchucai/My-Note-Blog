## rebase和merge的区别

#### 场景

1. 现在有2个分支，我们的开发分支feature和主分支master

2. 我们在开发分支已经提交了部分内容

 

3. 这个时候，master分支有其他的功能提交

   ![image-20181213171832864](/var/folders/bp/fy2twk9s23b3l_csblqcqpg00000gp/T/abnerworks.Typora/image-20181213171832864.png)

#### rebase操作

1. 我们切换到我们的开发分支`git checkout feature`
2. 使用`git rebase master`进行合并

![rebase](/Users/huangchucai/Desktop/rebase.png)

总结：可以看出我们在feature原来的**提交会被删除掉**，然后基于master分支的又重新开始新的提交了

**注意： **

1. 永远**不要在公共**分支进行rebase的操作

2. rebase的操作并不会改变master分支的内容，master分支还是在c7上，如果要发布上线提mr操作`git checkout master`   `git merge feature`

   ![rebase2](/Users/huangchucai/Desktop/rebase2.png)

#### merge

重回到我们上面的场景，我们使用merge合并

1. 切换到主分支master分支`git checkout master`
2. 合并我们的开发分支`git merge feature`

![merge](/Users/huangchucai/Desktop/merge.png)

**总结： ** feature分支的提交不会被删除，和master分支的c6合并成一个新的内容，如果我们此刻基于master分支进行开发的话，就是在c7的位置进行开发

**注意： **

1. master分支会被改动
