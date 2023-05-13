FROM python:3.8-slim

WORKDIR /app

COPY requirements.txt requirements.txt

RUN apt-get update -q \
  && apt-get install --no-install-recommends -qy python3-dev g++ gcc inetutils-ping \
  && pip install --no-cache-dir --progress-bar off gunicorn \
  && pip install --no-cache-dir --progress-bar off -r requirements.txt \
  && apt-get remove -qy python3-dev g++ gcc --purge \
  && rm -rf /var/lib/apt/lists/* 

RUN pip3 install -r requirements.txt

COPY . .

CMD [ "python3", "-m" , "flask", "run", "--host=0.0.0.0"]