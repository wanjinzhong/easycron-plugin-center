#!/bin/bash
cd /var/webapps/docker_easycron-plugin-center/
echo "Try to find easycron-plugin-center container."
container_name=easycron-plugin-center
container_id=`docker ps -af name="${container_name}" -q`
if [ -n "$container_id" ]
then
	echo "The easycron-plugin-center container found: $container_id."
	echo "Stoping easycron-plugin-center container($container_id)"
	image_id=`sudo docker inspect --format='{{.Image}}' ${container_id}`
	docker stop $container_id
	echo "Try to delete the easycron-plugin-center container($container_id)"
	docker rm $container_id
	echo "Try to delete the easycron-plugin-center image(${image_id})"
	docker rmi $image_id
else
	echo "The easycron-plugin-center container not found, skip."
fi
echo 'Build new image'
docker build -q -t easycron-plugin-center .
echo 'Run image'
docker run -d -p 8088:8080 --name easycron-plugin-center -v /var/webapps/logs/easycron-plugin-center/:/var/logs/easycron-plugin-center easycron-plugin-center:latest