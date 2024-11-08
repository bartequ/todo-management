# Run locally
To run app local you need mongodb up and running
 - `docker run -d -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=admin mongo`

Depending on used image you may also need
 - `docker exec -it [containerId] mongosh -u admin -p admin --authenticationDatabase admin`
 - `use management_todo_db`
 - `db.createUser({
      user: "admin",
      pwd: "admin",
      roles: [{ role: "readWrite", db: "management_todo_db" }]
    });`
