# conexion a la red
docker network create --subnet=192.18.0.0/16 votacionnetwork
# creacion de la base de datos
docker run --name votacioncontainer --network votacionnetwork --ip 192.18.0.10 -e MYSQL_ROOT_PASSWORD=09112 -e MYSQL_DATABASE=votaciondb -e MYSQL_USER=alextcw -e MYSQL_PASSWORD=09112 -p 3306:3306 -d mysql