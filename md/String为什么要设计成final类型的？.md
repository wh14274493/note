# String为什么要设计成final类型的？

###### 	因为如果不设计成final类型的话，子类就可以重新父类中的某些方法，那么String中定义的char字符数组就有可能暴露给外面多个线程进行读写，从而导致线程安全问题的发生。