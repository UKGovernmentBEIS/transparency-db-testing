TEST AUTOMATION FRAMEWORK

1. Technologies Used:

    Programing Language : JAVA
    
    Build Tool           : MAVEN
    
    JAVA SDK             : SDK 11.0.0
    
    Tools                : Serenity BDD with Cucumber Framework, RestAssured (API Testing), JUnit(Test Runner)
                           Browserstack (Cross Browser Testing)
                           
    Design Pattern       : POM (Page Object Model - using PAGE FACTORY)

2. Execution Procedure:

    Manual : Right click the respective runner class and run
    
    Automated : Navigate to project folder in Command Prompt and type the below commands given in quotes
    
         "mvn clean verify"        -> To test all the features
         
         "mvn clean verify -P API" -> To test all the API features
         
         "mvn clean verify -P UI"  -> To test all the UI features
      
         "mvn clean verify -P SEARCH" -> To test all the SEARCH portal UI features

         "mvn clean verify -P SEARCHAPI" -> To test all the SEARCH portal API features
      
         "mvn clean verify -P ADMINAPI" -> To test all the ADMIN portal API features
         
         "mvn clean verify -P -Dit.test=Runner_File"  -> To test individual feature
         
         
