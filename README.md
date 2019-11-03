# 1. rabbitmq概述
RabbitMQ 消息队列，提供应用间的异步通信和解耦功能，目前主要基于AMQP-0-9-1（Advanced Message Queuing Protocol）高级消息队列协议协议实现。

# 2. RabbitMQ AMQP-0-9-1
AMQP协议使用5672端口，协议包含Classes：
- connection 客户端和服务端之间的socket连接
- channel 客户端和服务端建立的逻辑通道，之后的所有操作都在channel上传递。相当于轻量级链接，节约tcp资源。
- exchange
- queue
- basic
- tx
- confirm


# 3. rabbitmq cluster
Rabbitmq集群使用cookie来加入集群，只有拥有同样cookie的erlang节点才能加入。
```
# load image
docker load -i rabbitmq.tar
docker tag 2888deb59dfc rabbitmq:0.1

# start rabbitmq docker
docker run -d --hostname rabbit1 --name myrabbit1 -p 15672:15672 -p 5672:5672 -e RABBITMQ_ERLANG_COOKIE='rabbitcookie' rabbitmq:0.1
docker run -d --hostname rabbit2 --name myrabbit2 -p 15673:15672 -p 5673:5672 --link myrabbit1:rabbit1 -e RABBITMQ_ERLANG_COOKIE='rabbitcookie' rabbitmq:0.1
docker run -d --hostname rabbit3 --name myrabbit3 -p 15674:15672 -p 5674:5672 --link myrabbit1:rabbit1 --link myrabbit2:rabbit2 -e RABBITMQ_ERLANG_COOKIE='rabbitcookie' rabbitmq:0.1

# construct rabbitmq cluster
docker exec myrabbit1 bash -c 'rabbitmqctl stop_app; rabbitmqctl reset; rabbitmqctl start_app'
docker exec myrabbit2 bash -c 'rabbitmqctl stop_app; rabbitmqctl reset; rabbitmqctl join_cluster  rabbit@rabbit1; rabbitmqctl start_app'
docker exec myrabbit3 bash -c 'rabbitmqctl stop_app; rabbitmqctl reset; rabbitmqctl join_cluster  rabbit@rabbit1; rabbitmqctl start_app'

# show cluster status
rabbitmqctl cluster_status
```

# 4. 测试工程
rabbitmq-test为springboot工程，分别模拟producer和consumer进行消息的发送和消费