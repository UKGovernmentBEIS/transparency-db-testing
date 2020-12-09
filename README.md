#Automation framework - Test demo

1. Technologies Used:

    Programing Launguage : JAVA , Selenium 
    
    Build Tool           : MAVEN, JUNIT

    SDK                  : SDK 11.0.0

    Style                : BDD Framework

    Integration          : Browerstack 

    Framework Followed   : Serenity (Framework), Cucumber(BDD)

    API Utilised         : REST ASSURED

    Design Pattern       : POM (Page Object Model - using PAGE FACTORY)

    Locators Used        : XPATH,ID,NAME

    MAVEN commands       : MVN CLEAN,TEST,INSTALL,BUILD

2. Application under test: Features Implemented:

    A)Feature – Confirm successful login to Subsidy Control Transparency DB
    
3. Execution Procedure:

    Manual : Right click the respective runner class and run
    
    Automated : Go to the project folder where POM file is visible, run the following commands
    
        step1: MVN CLEAN  - To delete the target folder
        
        Step2: MVN TEST   - To run the unit test
        
        step3: MVN BUILD   - To create the production build
        
        step4: MVN INSTALL -To install dependencies
        