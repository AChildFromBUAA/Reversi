# Reversi

This is a simple game, I made it for my java homework.

## About AI


黑白棋AI的算法设计采用的是对棋盘分值和行动力的计算相结合并取优的贪心方法来进行计算，对可落子点进行之后若干步落子模拟，从中寻找最优解；根据落子模拟的搜索步数不同决定AI难度。

棋盘分值：对黑白棋的64个落子位置进行估分，越重要（如角点和边点）的分值相应较高，容易造成对方占据重要位置或本身位置不重要的点分值较低（如起始的中央4点和二角点）

行动力：对某一确定的棋盘状态，双方可落子的位置数量称为行动力，双方的行动力之差也是考虑当前棋盘状态对我方是否有利的一个关键因素。

（补充：稳定子，即若某位置上的棋子的颜色已经确定不会再进行变更，则记为该方的稳定子；双方稳定子数量差是AI落子的重要因素，但加入该因素后，作者已经无法下赢简单模式的AI，故舍弃该因素的考虑

Easy: think 1 step; Normal: think 3 steps; Hard: think 5 steps.

## How to play it

If you don't know how to play it, you can click [here](https://en.wikipedia.org/wiki/Reversi)

## Instructions
* It's a java project.
* It's under MIT License.
* If you find some exception, please tell me.
