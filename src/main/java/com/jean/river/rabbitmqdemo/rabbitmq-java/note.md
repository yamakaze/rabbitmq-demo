https://x-team.com/blog/set-up-rabbitmq-with-docker-compose/

Here's what we've just done:

image: where we tell Docker which image to pull. We're using an Alpine implementation of RabbitMQ with the management plugin. The Alpine distro is the one you'll want to use if you want to save disk space.

container_name: this represents the container created from the image above.

ports: the list of ports that will be mapped from the container to the outside world, for interacting with the queue and the web UI.

volumes: where we map the log and data from the container to our local folder. This allows us to view the files directly in their local folder structure instead of having to connect to the container.

networks: where we specify the network's name that the container will be using. This helps to separate container network configurations.