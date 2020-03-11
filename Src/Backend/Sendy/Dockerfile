FROM python:3.7-slim
LABEL maintainer="Rodney Osodo"
WORKDIR /app
COPY requirements.txt .
RUN pip3 install --no-cache-dir -r requirements.txt
COPY app.py .
