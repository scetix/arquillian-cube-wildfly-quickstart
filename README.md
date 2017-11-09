# Arquillian Cube 
Quickstart project for running Arquillian unit tests on Wildfly and MySQL running in Docker container

### Common issues when running
Usually the culprit is leftover containers or docker networks from previous tests that were not clean up properly.

To stop all containers: 

	docker kill $(docker ps -q)

To clean up containers: 

	docker rm $(docker ps -a -q)

To clean up networks: 

	docker network prune

*WARNING: this will stop and remove all containers and networks*