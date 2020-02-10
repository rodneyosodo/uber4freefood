import requests

values = """
  {
    "command": "track",
    "data": {
      "api_key": "mysendykey",
      "api_username": "mysendyusername",
      "order_no": "AA34BE331"
    },
    "request_token_id": "request_token_id"
  }
"""

headers = {
  'Content-Type': 'application/json'
}

url = "https://apitest.sendyit.com/v1/#track"

request = requests.post(url, data=values, headers=headers)

response_body = request.json
print(response_body)