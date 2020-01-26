import os
from dotenv import load_dotenv
import africastalking


dotenv_path = os.path.join(os.path.curdir, '.env')
load_dotenv(dotenv_path)

username = "sandbox"
apikey = os.getenv("api_key")

#Initialize the SDK
africastalking.initialize(username, apikey)
sms = africastalking.SMS


def send_message(message, recipient):
    try:
        m = []
        m.append(recipient)
        recipient = m
        print(recipient)
        #Once this is done, that's it! We'll handle the rest
        print(recipient)
        response = sms.send(message, recipient)
        return response['SMSMessageData']['Recipients'][0]['messageId']
    except Exception as e:
        print("Houston, we have a problem ", e)
    

if __name__ == "__main__":
    recipients = ['+254720136609']
    message = 'I\'m a lumberjack and its ok, I sleep all night and I work all day'
    print(send_message(message, recipients))