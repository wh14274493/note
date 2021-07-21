# Leetcode基础算法汇总

- ###### 状态机的用法 https://leetcode-cn.com/problems/string-to-integer-atoi/solution/zi-fu-chuan-zhuan-huan-zheng-shu-atoi-by-leetcode-/

- ###### KMP算法 https://leetcode-cn.com/problems/implement-strstr/solution/kmp-suan-fa-xiang-jie-by-labuladong/

- ###### 投票法求多数元素 https://leetcode-cn.com/problems/majority-element/

- ###### 归并求逆序对 https://leetcode-cn.com/problems/shu-zu-zhong-de-ni-xu-dui-lcof/

- ###### Fisher-Yates洗牌算法打乱数组 https://leetcode-cn.com/problems/shuffle-an-array/

- ###### 滑动窗口 https://leetcode-cn.com/problems/minimum-size-subarray-sum/

- ###### 计算第一个大于等于n且为2的n次幂的数

  ```java
  int n = cap - 1; //为了防止n原本就是2的n次幂，不减一的话求出来的值是2n
  n |= n >>> 1;
  n |= n >>> 2;
  n |= n >>> 4;
  n |= n >>> 8;
  n |= n >>> 16;
  //上面的步骤相当于是把n从最左边的1开是向右全部改为1
  return n + 1;
  ```

  

