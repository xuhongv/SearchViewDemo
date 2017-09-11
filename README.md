### Android自定义View之蓝牙搜索的涟漪雷达效果: 我在搜索呢，你在哪里呀？
 
 博文：http://blog.csdn.net/xh870189248/article/details/77936096
 
 如有转载，请注明来处，博主也是一个一个字敲出来的。
 
- 终于可以运用到实战了，我们看看下面的效果图，还是挺不错的。

- 其实这个效果图我在大学读书时候参看了别人代码思路，具体的源码来处我还是不记得了（**罪过罪过**），这里我有必要感谢那位原创。本篇博文绝对原创！

----------
![这里写图片描述](http://img.blog.csdn.net/20170911162044628?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGg4NzAxODkyNDg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


----------
### 一、流程：


----------
-  第一步：这是一个继承相对布局的自定义view，所以可以往里面加子view。我们仔细看动画效果，好像是一各一个圆在外扩张，对，没错，就是一个一个圆在放大动画和透明度动画一起在操作这个view，所以我们先画一个圆。成功画出一个圆出来，我们通过RelativeLayout的布局管理器LayoutParams来设置让他居中显示即可。

 - 注意：通过父布局的宽高来确定圆心和半径，取二者的最小值。为什么要去最小值？这个问题提的好。假设父布局的宽是长于高的，你取了宽的一半为半径，你想想，一个圆的直径都比父布局的高还长，那还是 圆吗，你可以想想。成功画个圆出来就这样啦！


----------

```
 final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleY", 1.0f, rippleScale);
            //为无限次数循环动画
            scaleYAnimator.setRepeatCount(ValueAnimator.INFINITE);
            //每轮动画之后设置为重新开始。
            scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);
            //动画延迟
            scaleYAnimator.setStartDelay(i * rippleDelay);
            animatorList.add(scaleYAnimator);
```

----------
![这里写图片描述](http://img.blog.csdn.net/20170911195715359?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGg4NzAxODkyNDg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

----------
- 第二步：进行动画效果，大家看到这么多圆在动，相比知道这个肯定不是一个圆在运动。对的，是自定义若干个圆在动，那么怎么处理这么多的圆在动呢？

  - 知识点一：我们通过AnimatorSet动画集合把透明度动画和伸缩动画一起播放；
  
  - 知识点二：我们每执行一轮动画之后，设置动画集的次数属性为无限播放次数、动画播放一轮之后，重新动画之前的效果，并不是反方向返回动画哦。

  - 知识点三：每执行一轮动画，设置每个圆圈出现的时间间隔 = 每轮动画的时间除以每轮动画出现的圆的个数，这样就保证了每个圆出现的时间肯定一致的！！！同时每个圆设置加速插值器即可。
  

 


----------


```
      //缩放、渐变动画，rippleAmount是每轮动画要出现的圆的个数
        for (int i = 0; i < rippleAmount; i++) {

            mRipplView rippleView = new mRipplView(getContext());
            addView(rippleView, rippleParams);
            rippleViewList.add(rippleView);

            //伸缩动画
            float rippleScale = 6.0f;
            final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleX", 1.0f, rippleScale);
            scaleXAnimator.setRepeatCount(ValueAnimator.INFINITE);
            scaleXAnimator.setRepeatMode(ObjectAnimator.RESTART);
            scaleXAnimator.setStartDelay(i * rippleDelay);
            animatorList.add(scaleXAnimator);

            final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(rippleView, "ScaleY", 1.0f, rippleScale);
            scaleYAnimator.setRepeatCount(ValueAnimator.INFINITE);
            scaleYAnimator.setRepeatMode(ObjectAnimator.RESTART);//ObjectAnimator.RESTART
            scaleYAnimator.setStartDelay(i * rippleDelay);
            animatorList.add(scaleYAnimator);

            //透明度动画
            final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(rippleView, "Alpha", 1.0f, 0f);
            alphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
            alphaAnimator.setRepeatMode(ObjectAnimator.RESTART);
            alphaAnimator.setStartDelay(i * rippleDelay);
            animatorList.add(alphaAnimator);

        }
```
        
----------


- 第三步：设置动画监听：

 -  这个分析也就没啥的了，给个内部接口就可以了，大家看看代码和使用方法知道了。


----------

```
      //动画的监听
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                //开始动画回调给内部接口
                if (mAnimationProgressListener != null) {
                    mAnimationProgressListener.startAnimation();
                }
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //结束动画回调给内部接口
                if (mAnimationProgressListener != null) {
                    mAnimationProgressListener.EndAnimation();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
```

 
 
----------
### 二、原理图概括


----------


![这里写图片描述](http://img.blog.csdn.net/20170911195032339?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGg4NzAxODkyNDg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


----------

- 给大家看看空心圆的动画效果，也就没有明显的透明度效果了，发现是不是里面的圆在伸缩出来的？


----------

![这里写图片描述](http://img.blog.csdn.net/20170911202325992?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQveGg4NzAxODkyNDg=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


----------
国际案例：gitHub：https://github.com/xuhongv/SearchViewDemo
