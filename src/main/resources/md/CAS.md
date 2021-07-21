# CAS

1. CAS需要依赖volatile关键字实现，因为需要通过volatile来保证可见性；
2. 底层是通过unsafe类的方法来实现cas操作的，而unsafe在hotspot中的实现和volatile类似，也是通过lock指令来实现的；
3. 常见的CAS类没有解决ABA问题，如AtomicInteger等，但是AtomicStampedReference通过版本号的机制来解决ABA问题；
4. 为什么LongAddr累加器会比AtomicInteger要快？
   - LongAddr通过内部实现一个Cell数组来提高并发度；
   - Cell数组的扩容属于懒创建的方式，仅当有需要时才创建；
   - Cell数组的有效Cell个数大小不能超过CPU的核心数，且数组每次扩容都是将原有的大小翻倍；
   - 每个Cell对象占用24个字节的大小（object header + object body），因为cpu缓存行的大小一般为64 bytes，所有通过缓存行填充（Cell类上被Contented注解标注会在Cell对象前后填充128 byte，保证每个Cell单独占用一个缓存行）的方式加速对Cell的修改操作

