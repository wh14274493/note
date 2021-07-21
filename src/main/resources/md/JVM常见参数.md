# JVM常见参数

- #### -Xmx ---- 堆空间最大大小

- #### -Xms（-XX:MaxHeapSize） ---- 堆空间初始化大小

- #### -Xmn ---- 同时指定新生代初始和最大大小，相当于同时指定-XX:NewSize 和 -XX:MaxNewSize

- #### -XX:SurvivorRatio ---- eden与survivor区的比例

- #### -XX:InitialSurvivorRatio 和 -XX:+UseAdaptiveSizePolicy ---- 同时指定可以动态的调整比例

- #### -XX:MaxTenuringThreshold ---- 晋升老年代的阈值

- #### -XX:+PrintGCDetails -verbose:gc ---- 打印GC详情信息

- #### -XX:+ScanvengeBeforeFullGC ---- FullGC之前进行一次miniorGC

- #### -XX:+PrintStringTableStatisics ---- 打印StringTabled信息

- #### -XX:+PrintCommandLineFlags ---- 查看命令行参数