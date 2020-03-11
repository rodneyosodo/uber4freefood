import requests

values = """
  {
    "command": "cancel",
    "data": {
      "api_key": "aOYE0BD3rz03QKPXUx4R",
      "api_username": "qualis",
      "order_no": "AN82TT944-33W"
    },
    "request_token_id": "request_token_id"
  }
"""

headers = {
  'Content-Type': 'application/json'
}

url = 'https://apitest.sendyit.com/v1/#cancel'

request = requests.post(url, data=values, headers=headers)

response_body = request.text
print(response_body)