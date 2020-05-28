# CS102 ~ Group Meetings Log page ~

Below is a record of our project group meetings. This page will be submitted together with the rest of the repository, in partial fulfillment of the CS102 course requirements.

****
### Meeting ~ (14/04/2020, 4 hours)
****
**Present:** Osman, Giray, Özgür, Elif, Kaan, Emre..   _**Absent:** -

**Discussion:** 
-We decided the project's design and structure. We decided to write classes: DObject, DMethod, DConstructor, DConstructorPropety,
DGeneralClass, DInterfac, DClass, DProperty, DAbstractClass
-We shared the classes betwen he group and done the work in several hours then diverged our work to demonstrate a basic program. 

**ToDo:** Hierarchies and relations between classes and interfaces => DRelation class etc.

****
### Meeting ~ (23/04/2020, 3 hours)
****
**Present:** Osman, Giray, Özgür, Elif, Kaan, Emre..   _**Absent:** -

-Extractable interface done, GitHub branches caused a major problem for us, we spent our time to fix it and happily we handled it. 

**ToDo:** GUI classes etc.

### Meeting ~ (25/04/2020, 3 hours)
****
**Present:** Osman, Giray, Özgür, Elif, Kaan, Emre..   _**Absent:** -

-Logs created and filled for every user
-Begun work on detailed design report
****

### Meeting ~ (29/04/2020, 3 hours)
****
**Present:** Osman, Giray, Özgür, Elif, Kaan, Emre..   _**Absent:** -

-classToString methods completed
-extract method edited
-text/object translation process completed which enables to save project as a text file 
****

### Meeting ~ (1/05/2020, 5 hours)
****
**Present:** Osman, Giray, Özgür, Elif, Kaan, Emre..   _**Absent:** -

-Transferred the project from DrJava to Eclipse and installed the JavaFX library into our project
-DClass translate methods updated
-FileManager class planned
-GUI classes and interfaces planned and started to work on GUI.
-Implemented a JavaFX graphical user interface class into project (Resize class) 
****

### Meeting ~ (8/05/2020, 3 hours)
****
**Present:** Osman, Giray, Özgür, Elif, Kaan, Emre..   _**Absent:** -

-Worked on how to draw arrows between elements. 
-Designed upcoming stages of drawing arrows and shared by group members.
-Updated element subclass.

-**ToDo:** drawCenteredLine(Element first, Element second) => method (for test purposes)
	  -intLine( Element first, Element second) method
	  -drawLine(Element first, intCoordinates() ) method => returns 0 - up, 1 - left, 2 - down, 3 - right  
	  -drawTriangle(double[] dot, Line line) method
	  -intCoordinates(Element item, Line line) method => returns interception x and y 
****

### Meeting ~ (12/05/2020, 4 hours)
****
**Present:** Osman, Giray, Özgür, Elif, Kaan, Emre..   _**Absent:** -

-Arrow Class is added.
-Branch issues solved.
-*ComplexLine design process:*
	-Complexline division process is designed
	-A method for saving complexlines into the project is designed
	-Pythagoras theoram is used for calculating the closest point in the line to the mouse
****

### Meeting ~ (17/05/2020, 3 hours)
****
**Present:** Osman, Giray, Özgür, Elif, Kaan, Emre..   _**Absent:** -
-Complexline class is created
-*Resize class is updated ( Later called DApp)*
	-updateSize() 
	-drawCenteredLine()
	-showClosest()
	-getClosestPoint() methods are added

****

### Meeting ~ (18/05/2020, 3 hours)
****
**Present:** Osman, Giray, Özgür, Elif, Kaan, Emre..   _**Absent:** -

-Element class is seperated from DApp class
-Menu bar is added
-Update methods are created for Element and Circle objects
-ScrollPane is added to the GUI
-DisplayFieldOptions() method is added
****

### Meeting ~ (19/05/2020, 8 hours)
****
**Present:** Osman, Giray, Özgür, Elif, Kaan, Emre..   _**Absent:** -

-Extract methods are updated
-Created DProject and wrote extract method for projects
-Wrote stringToText and textToString methods for projects
-DInterface and DGeneralClass update: changed superClass structures
-ArrowHead, DashedComplexLine classes are added
-Menu options for adding properties and methods to classes are added
-setInheritance method is made
-Syntax errors in GUI are eliminated
-Function for Removing a point from a line is added
-ProjectManager is integrated with the Graphical User Interface
-Icon is added to the project
-Bugs in ComplexLine are fixed
-projectToText() method is edited
-Accesible interface is added
-Resize code is turned into DApp and is integrated with other classes

****

### Meeting ~ (20/05/2020, 12 hours)
****
**Present:** Osman, Giray, Özgür, Elif, Kaan, Emre..   _**Absent:** -
-displayConstructorMaker() method is added
-removeClass() option is added
-ArrowHead is edited to calculate its direction automatically
-drawHierarchy() method is updated
-toString() methods are created for Element and Complexline, which is important for saving function
-Menu options are moved to a new class called DMenuWizard
-Element is distinguished between ClassElement and InterfaceElement
-Color options are created for the objects, and the objects are now created with random colors
-Option to change background color was added

****

### Meeting ~ (23/05/2020, 4 hours)
****
**Present:** Osman, Giray, Özgür, Elif, Kaan, Emre..   _**Absent:** -

-DMethod class is updated
-The class creation function now checks if the name is suitable
-saveProject() and loadProject() methods are written
-ProjectManager is updated suitable to saveProject() and loadProject() methods

****

### Meeting ~ (26/05/2020, 7 hours)
****
**Present:** Osman, Giray, Özgür, Elif, Kaan, Emre..   _**Absent:** -
-Property names are now compared to previous ones to prevent multiple properties with the same name 
-editClassOptions() and editInterfaceOptions() methods are added to DMenuWizard
-containsSuperInterface() method is added
****

### Meeting ~ (27/05/2020, 6 hours)
****
**Present:** Osman, Giray, Özgür, Elif, Kaan, Emre..   _**Absent:** -
-DApp bugs fixed
-ConstructorMaker now does not add constructors to the abstract classes ad interfaces
-updateSeparators() method added
****

### Meeting ~ (28/05/2020, 7 hours)
****
**Present:** Osman, Giray, Özgür, Elif, Kaan, Emre..   _**Absent:** -
-After remove method hierarchies updated to draw relations correctly
-updateHierarchy method updated
-ConstuctorMaker bug fixes completely fixed and now works properly 
-Edit class bugs fixed in huge amount
-add property and add method bugs fixed
-DConstructor updated
-Added close warning window
-Properties not being visible on elements fixed
-Removed unused imports
-Fixed project warnings
-Removed unused class and interfaces
****