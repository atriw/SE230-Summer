# -*- coding:utf-8 -*-
import pika
import json

class Sender:
    def __init__(self,exchange, queueName, routingKey):
        self.exchange = exchange
        self.queueName = queueName
        self.routingKey = routingKey
        
        self.connect = pika.BlockingConnection(pika.ConnectionParameters("localhost"))
        # 声明一个管道
        self.channel = self.connect.channel()
        # 声明queue名称为test
        self.channel.queue_declare(queue=self.queueName,durable = True)  # 队列持久化


    def send(self, data):
        #RabbitMQ的消息永远不会被直接发送到队列中，它总是需要经过一次交换
        self.channel.basic_publish(exchange=self.exchange,
                      routing_key=self.routingKey,
                      body=json.dumps(data),
                      properties=pika.BasicProperties(delivery_mode=2,))
        
    def __del__(self):
        self.connect.close()
