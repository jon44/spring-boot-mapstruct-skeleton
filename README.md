# Spring Boot with MapStruct skeleton project!

*Spooky Skeletons!*

![](https://media.giphy.com/media/pm0BKtuBFpdM4/giphy.gif)

## What Do

* Import as Maven Project
* Create a PostgreSQL Database named "assessment_two"
* Run Maven Clean on the parent project
* Run Maven Install on the parent project
* Open the SBM-API project, run the Application class as a Java Project
* Open `localhost:8080/api/swagger-ui.html`
* If it works, you're good to go!

### A Note on Spring

For the Dependency Injection system to work, **all classes must be in a package that begins with** `cooksys`

### A Note On MapStruct

The SBM-DTO project is intended to contain your Data Transfer Objects & Mappers. In order for MapStruct to work properly, you must follow a particular build process:

**Important:** *You must repeat this process in this order whenever you make any changes to any Mapper interface*

1. Run Maven Clean on the parent project
2. Run Maven Install on the parent project
3. Start the project

If you see an error similar to `Unable to find type of XyzMapper...` the most likely cause is that you changed a Mapper and did not Clean+Install the parent project