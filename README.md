This is Web and "Telegram" messenger bot application for people a like hitchhiker's of earth.

**run application:**

    git clone https://github.com/redirut/HGEApp.git
    cd hgeapp
    mvn clean package
    java -jar target/hgeapp-0.0.1-SNAPSHOT.jar

**or**

    git clone https://github.com/redirut/HGEApp.git
    cd hgeapp
    mvn spring-boot:run
---    
Telegram bot info:

**name:**
`HGEApp_Bot`

**token:** 
`see Api map:bot`

---
**Api map:city:**

url:
**POST**    `/add`         

info:
Add new city.

example:

    curl -v -H "Content-Type: application/json" -X POST -d '{"name":"Minsk", "notes":["Minsk is very good city","Minsk is capital of Belarus"]}' http://localhost:666/add

---
url:
**POST**    `/add/{cityName}/add`         

info:
Add new note to exist city.

example:

    curl -v -H "Content-Type: application/json" -X POST -d 'Minsk a large town' http://localhost:666/add/Minsk/add

---
url:
**POST**    `/update`         

info:
Update city by id, the id of the object being updated must be set to json object.

example:

    curl -v -H "Content-Type: application/json" -X POST -d '{"id":"1", "name":"Minsk", "notes":["Minsk very big city"]}' http://localhost:666/update

---
url:
**GET**    `/get/{cityName}`        

info:
Get city by name.

example:

    curl -v http://localhost:666/get/Minsk

---
url:
**GET**    `/all`         

info:
Get all cities.

example:

    curl -v http://localhost:666/all

---
url:
**POST**    `/remove`         

info:
Remove city.

example:

    curl -v -H "Content-Type: application/json" -X POST -d '{"id":"1", "name":"Minsk"}' http://localhost:666/remove

---
url:
**POST**    `/remove/{cityName}`         

info:
Remove city by name.

example:

    curl -v http://localhost:666/remove/Minsk

---    
**Api map:bot:**

url:
**POST**    `/init/{name}`         

info:
init new bot.

example:

    curl -v -H "Content-Type: application/json" -X POST -d 'your bot_token here' http://localhost:666/init/HGEApp_Bot

