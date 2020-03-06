from confluent_kafka import Consumer, KafkaError

c = Consumer({'bootstrap.servers': 'localhost:9092', 'group.id': 'mygroup',
              'default.topic.config': {'auto.offset.reset': 'smallest'}})
c.subscribe(['Qualis'])
running = True
while running:
    msg = c.poll()
    if not msg.error():
        m = msg.value()
        print('Received message: %s' % m)
    elif msg.error().code() != KafkaError._PARTITION_EOF:
        print(msg.error())
        running = False
c.close()
