from confluent_kafka import Producer, KafkaError, KafkaException
from time import gmtime, strftime, sleep
import sys


def callback(err, msg):
    if err:
        sys.stderr.write("Message failed: ", err)
    else:
        sys.stderr.write("Message delivered to ", msg.topic())


if __name__ == "__main__":
    while True:
        producer = Producer({'bootstrap.servers': 'localhost:9092'})
        topic = "Qualis"
        try:
            producer.produce(topic, key="Osodo Rodney", value="Test Message")
            #producer.produce(topic, b'Test message')#, callback=callback)
        except BufferError:
            pass
            #sys.stderr.write("Local producer queue is full")
        producer.flush()
        sleep(1)

