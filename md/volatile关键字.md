# volatile关键字

1. ## 保证有序性

2. ## 保证可见性

   - 对volatile修饰的变量进行读的时候，会在字节码解释器中执行OrderAccess::fence();
   - 对volatile修饰的变量进行读的时候，会在最后执行OrderAccess::storeload();保证变量的写操作之前的所有操作都必须完成的同时刷新cache回主存，根据缓存一致性协议MESI其它cpu可以知道这个cache已经无效了，所以其它CPU也必须重新从主存中加载