Netty介绍
    JBOSS开源的java项目。现在github上维护。
    异步、基于事件驱动


IO模型
  BIO:one Connection need use by one Thread,if one connection block,this thread also block.
      目录java.io下，
  NIO:同步非阻塞。核心是选择器轮训处理selector(netty目前使用)
  AIO:异步非阻塞。jdk1.7引入，目前尚未普及
