# Linux常用命令

- 修改用户密码

  su passwd [username]

- ubuntu防火墙操作

  开启防火墙---- sudo ufw enable

  关闭防火墙---- sudo ufw disable

  查看防火墙状态----sudo ufw status

  **ufw default allow/deny:**外来访问默认允许/拒绝

  **ufw allow/deny 20：**允许/拒绝 访问20端口,20后可跟/tcp或/udp，表示tcp或udp封包

  **ufw allow/deny servicename:**ufw从/etc/services中找到对应service的端口，进行过滤

  **ufw allow proto tcp from 10.0.1.0/10 to 本机ip port 25:**允许自10.0.1.0/10的tcp封包访问本机的25端口

  **ufw delete allow/deny 20:**删除以前定义的"允许/拒绝访问20端口"的规则

- 查看端口号信息

  ss -ltn
  
- 查找文件

  find /path -iname filename

- 查找文件中的指定字符串数量

  grep -o test.txt 'test' | wc -l