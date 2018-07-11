# -*- coding:utf-8 -*-
import pika
import json
connect = pika.BlockingConnection(pika.ConnectionParameters("localhost"))
# 声明一个管道
channel = connect.channel()
# 声明queue名称为test
channel.queue_declare(queue="infoQueue",durable = True)  # 队列持久化


#RabbitMQ的消息永远不会被直接发送到队列中，它总是需要经过一次交换
channel.basic_publish(exchange='exchange',
                      routing_key="info",
                      body=json.dumps({'a':2,'f':6}),
                      properties=pika.BasicProperties(delivery_mode=2,))  # 消息持久化
 
print("Sent " + body)
 
connect.close()
