* For creation docker image in local, you should make maven docker plugin skip false.
    <skip>true</skip>


* Above command will build the project and create a docker image.
    ./mvnw package

** Project based on Spring Boot and RestAPI. Project has a docker file and can be dockerized.
Project build on Heroku and can be reached
    https://cakemanager-api.herokuapp.com/


Requirements:
* By accessing the root of the server (/) it should be possible to list the cakes currently in the system. This must be presented in an acceptable format for a human to read.
* DONE

* It must be possible for a human to add a new cake to the server.
* DONE

* By accessing an alternative endpoint (/cakes) with an appropriate client it must be possible to download a list of
the cakes currently in the system as JSON data.
* DONE

* The /cakes endpoint must also allow new cakes to be created.
* DONE

* The /filter endpoint returns filtered cakes by title.


Bonus points:
* Tests is done
* Continuous Integration via any cloud CI system is done
* Containerisation is done


