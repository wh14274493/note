# Linux开发环境安装

- ## JDK1.8安装

  1. 下载安装包并解压

  2. vim /etc/profile  --------修改环境变量

     export JAVA_HOME=/usr/local/jdk1.8
     export JRE_HOME=${JAVA_HOME}/jre
     export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
     export PATH=.:${JAVA_HOME}/bin:$PATH

  3. source /etc/profile ----------使环境变量生效

  4. java -version -------------验证是否成功

- ## MAVEN安装

  1. 下载安装包并解压
  2. vim /etc/profile  --------修改环境变量

