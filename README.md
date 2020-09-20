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

    curl -v -H "Content-Type: application/json" -X POST -d '{"name":"Minsk", "notes":["Minsk is very good city","Minsk is capital of Belarus"]}' http://localhost:8088/add

---
url:
**POST**    `/noteTo/{cityName}`         

info:
Add new note to exist city. Note enter in body.

example:

    curl -v -H "Content-Type: application/json" -X POST -d 'Minsk a large town' http://localhost:8088/noteTo/Minsk

---
url:
**POST**    `/update`         

info:
Update city by an id, the id of the object being an updated must be set to in json object.

example:

    curl -v -H "Content-Type: application/json" -X POST -d '{"id":"1", "name":"Minsk", "notes":["Minsk very big city"]}' http://localhost:8088/update

---
url:
**GET**    `/get/{cityName}`        

info:
Get city by name.

example:

    curl -v http://localhost:8088/get/Minsk

---
url:
**GET**    `/all`         

info:
Get all cities.

example:

    curl -v http://localhost:8088/all

---
url:
**POST**    `/remove`         

info:
Remove city.

example:

    curl -v -H "Content-Type: application/json" -X POST -d '{"id":"1", "name":"Minsk"}' http://localhost:8088/remove

---
url:
**POST**    `/remove/{cityName}`         

info:
Remove city by name.

example:

    curl -v http://localhost:8088/remove/Minsk

---    
**Api map:bot:**

url:
**POST**    `/initPollingBot/{name}`         

info:
init new polling bot.

example:

    curl -v -H "Content-Type: application/json" -X POST -d 'your bot_token here' http://localhost:8088/initPollingBot/HGEApp_Bot

