from flask import Flask, request, jsonify
import requests, json, os
from dotenv import load_dotenv

load_dotenv("secrets.env")

api_key = os.environ.get("api_key")
api_username = os.environ.get("api_username")
app = Flask(__name__)

def make_request(data, url):
    data['data']['api_key'] = api_key
    data['data']['api_username'] = api_username
    headers = {
    'Content-Type': 'application/json'
    }
    request = requests.post(url, data=json.dumps(data), headers=headers)
    response_body = request.json()
    return response_body

@app.route('/')
def hello():
    return "Base url"


@app.route('/requestdelivery', methods=['POST'])
def request_delivery():
    delivery_data = request.get_json()
    url = "https://apitest.sendyit.com/v1/##request"
    response = make_request(delivery_data, url)
    return response


@app.route('/canceldelivery', methods=['POST'])
def cancel_delivery():
    delivery_data = request.get_json()
    url = 'https://apitest.sendyit.com/v1/#cancel'
    response = make_request(delivery_data, url)
    return response

@app.route('/trackdelivery', methods=['POST'])
def track_delivery():
    delivery_data = request.get_json()
    url = "https://apitest.sendyit.com/v1/#track"
    response = make_request(delivery_data, url)
    return response


if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000)


# curl  --header "Content-Type: application/json" --request POST  --data '{"command": "cancel","data": {"api_key": "aOYE0BD3rz03QKPXUx4R","api_username": "qualis","order_no": "AN82TT944-33W"},"request_token_id": "request_token_id"}'  http://localhost:5000/canceldelivery

