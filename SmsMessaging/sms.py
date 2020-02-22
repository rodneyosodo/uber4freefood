import os
import africastalking

def send_message(message, number):
    username = "uber_4_food"
    apikey = "392661eddeb96afda41a9ccee71fb55814ca199fa2584fc964e5a9ff95286c85"
    africastalking.initialize(username, apikey)
    sms = africastalking.SMS 
    try:
        recipient = []
        recipient.append(number)
        print(recipient)
        response = sms.send(message, recipient)
        return response['SMSMessageData']['Recipients'][0]['messageId']
    except Exception as e:
        print("Houston, we have a problem ", e)
    
if __name__ == "__main__":
    recipients = '+254720136609'
    message = 'I\'m a lumberjack and its ok, I sleep all night and I work all day'
    print(send_message(message, recipients))