FROM python:3.7-slim
LABEL maintainer="Rodney Osodo"
WORKDIR /app
COPY requirements.txt .
RUN pip3 install -r requirements.txt
COPY app.py .
