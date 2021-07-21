# StringTable

- ### 结构

  > StringTable底层结构是一个hashtable，因此不允许存放相同的字符串。可以使用-XX:StringTableSize来调整StringTable的大小。随着越来越多的String进入StringTable，hash冲突会越来越严重，导致String.intern()方法（注意类似于String s = "abc";这样的的代码，其实也是网StringTable中加入了一个字符串）性能大幅度下降，因此设置一个较大的size很有必要。

- ### 常见的String操作

  > String.intern()方法会查看StringTable中是否存在对应的字符串，存在的话返回StringTable中的String，不存在的话，jdk1.6会把String的值复制一份放入StringTable中，jdk1.7/1.8会将String的引用地址值放入StringTable

- ### G1收集器String去重

  > gc的时候会对堆中（非StringTable）的String对象执行去重操作，这也得益于String的不可变设计
  >
  > -XX:+UseStringDuplication开启