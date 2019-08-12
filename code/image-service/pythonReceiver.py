#!/usr/bin/env python
# -*- coding:utf-8 -*-
import pika
import time
import json
import controller
# 声明socket实例
connect = pika.BlockingConnection(pika.ConnectionParameters("localhost"))
# 声明一个管道  虽然在之前的produce代码中声明过一次管道，
# 但是在不知道produce中的管道是否运行之前（如果未运行,consumers中也不声明的话就会报错），
# 在consumers中也声明一次是一种正确的做法
channel = connect.channel()
 
#声明queue
channel.queue_declare(queue="test")
 

#回调函数
def callback(ch, method, properites, body):
    print("-----", ch, method, properites, body)
    print("Received %r" % body)
    answer = json.loads(body)
    print("%s" %answer['sectionId'])
    ch.basic_ack(delivery_tag=method.delivery_tag)  # 手动确认收到消息，添加手动确认时，no_ack必须为False,不然就会报错
    aController = controller.Controller(answer['sectionId'],answer['camera'],answer['interval'],answer['duration'])
    aController.run()
    
 
channel.basic_qos(prefetch_count=1)  # 在消息消费之前加上消息处理配置
 
channel.basic_consume(callback,
                      queue="requestQueue",
                      no_ack=False)
 
print("Waiting for messages")
#这个start只要一启动，就一直运行，它不止收一条，而是永远收下去，没有消息就在这边卡住
channel.start_consuming()
