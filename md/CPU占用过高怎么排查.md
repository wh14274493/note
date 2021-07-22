# CPU占用过高怎么排查

- top命令查看哪个进程占用过高
- ps H -eo pid,tid,%cpu | grep 进程id  查看哪个线程占用cpu过高
- jstack 进程id 查看java的堆栈信息
- 将ps命令中查询的tid转换为16进制，然后在jstack命令输出的堆栈信息中找到对应的thread，以及对应的源码行数 
- 这种方式同样也可以检查线程死锁的问题