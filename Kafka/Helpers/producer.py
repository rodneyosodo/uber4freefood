from confluent_kafka import Producer, KafkaError, KafkaException
import time
import sys


def callback(err, msg):
    if err:
        sys.stderr.write("Message failed: ", err)
    else:
        sys.stderr.write("Message delivered to ", msg.topic())


if __name__ == "__main__":
    counter = 0
    while True:
        producer = Producer({'bootstrap.servers': 'localhost:9092'})
        topic = "Qualis"
        try:
            message = "{} Test message {}".format(counter, time.asctime()[11:][:8])
            producer.produce(topic, key="Osodo Rodney", value=message)
            counter = counter + 1
            #producer.produce(topic, b'Test message')#, callback=callback)
        except BufferError:
            pass
            #sys.stderr.write("Local producer queue is full")
        producer.flush()
        # time.sleep(1)

