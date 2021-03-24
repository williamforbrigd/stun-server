FROM java:8
RUN mkdir /start

COPY run.sh /start
COPY ./out/artifacts/stun_server_jar/stun-server.jar /start
COPY ./out/artifacts/public_nat_jar/stun-server.jar /start
COPY ./out/artifacts/private_nat_jar/stun-server.jar /start
COPY ./out/artifacts/stun_client_jar/stun-server.jar /start

WORKDIR /start/
CMD ["bash", "run.sh"]

