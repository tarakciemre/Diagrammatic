# CS102 ~ Personal Log page ~

## Emre Tarakcı


On this page I will keep a weekly record of what I have done for the CS102 group project. This page will be submitted together with the rest of the repository, in partial fulfillment of the CS102 course requirements.

### ~  14-21/04/2020 ~
This week we created the GitHub project and I set up the initial project settings and pushed the initial commit.

### ~ 21-28/04/2020 ~
-I wrote DConstructorProperty and DProperty classes and edited the rest of the classes accordingly. I also wrote the interface "Extractable", and then edited the other classes to make sure they implement extractable.
-We had major problems about the merging process in github and it caused our project to corrupt, so I worked on fixing it by reverting the project to an earlier version. We divided the work among ourselves this week
and put reasonable deadlines for everyone to complete their part of the code. After that, I altered some of my teammates' codes to make sure they were compatible with my code.

### ~ 29/04/2020 ~
****

-I wrote the code for translating texts to interfaces.To accomplish this, we have created a template that allows us to save the necessary data in the project to later recall them while opening a project
-By editing the template that we created for normal classes, I wrote extract method for abstract classes.

****

###  ~ 1/05/2020 ~
****

-I participated in the planning process of DProject class. This class saves projects into text by calling "...ToString" methods of neccessary elements, methods and properties.
-I've participated in the design process of the classes, I've worked on and designed the complexLine and how we can set the class hierarchies in our program.
-I found and implemented a JavaFX graphical user interface class into project called Resize. The link to this class is here: https://yumberc.github.io/Resize/Resize.html

****

### ~ 8/05/2020 ~
****

I helped design of Resize class.
I helped design of Resize class listeners.
I helped design of upcoming stages of drawing arrows.

### ~ 8/05/2020 ~
****

I re-designed Resize class (changed Element inner-class constructor/properties and changed createElement method.)
I changed Resize class listeners. (Designed them to fit our purpose of UML structure.)
I helped design of upcoming stages of drawing arrows.

### ~ 12/05/2020 ~

-Arrow Class is added.
-Branch issues solved.
-*ComplexLine design process:*
	-Complexline division process is designed
	-A method for saving complexlines into the project is designed
	-Pythagoras theoram is used for calculating the closest point in the line to the mouse

### ~ 17/05/2020 ~
****
-Complexline class is created
-*Updated Resize class ( Later called DApp)*
	-updateSize() 
	-drawCenteredLine()
	-showClosest()
	-getClosestPoint() methods are added,
-I've worked on the mathematical background of showing the closest line and the implementation of it to the code



### ~ 18/05/2020 ~
****

-Element class is seperated from DApp class
-Participated in the Design and implementation of the Menu bar 
-Helped creation of update methods for Element and Circle objects
-Added ScrollPane to the GUI

****

### ~ 19/05/2020 ~
****

-I've designed the drawFilledTriangle() method
-I've worked on updating extract methods 
-I've created DProject and wrote extract method for projects
-Wrote stringToText and textToString methods for projects
-Updated DInterface and DGeneralClass: changed superClass structures
-ArrowHead, DashedComplexLine classes are added
-Participated in adding menu options for adding properties and methods to classes
-Eliminated syntax errors in GUI 
-Helped the function for Removing a point from a line
-ProjectManager is integrated with the Graphical User Interface
-Fixed Bugs in ComplexLine
-Helped design and changes in projectToText() method
-Resize code is turned into DApp and is integrated with other classes

****

### ~ 20/05/2020 ~
****
-ArrowHead is edited to calculate its direction automatically
-toString() methods are created for Element and Complexline, which is important for saving function
-Menu options are moved to a new class called DMenuWizard
-Color options are created for the objects, and the objects are now created with random colors
-I've created the option to change background color

****

### ~ 23/05/2020 ~
****
**Present:** Osman, Giray, Özgür, Elif, Kaan, Emre..   _**Absent:** -

-DMethod class is updated
-The class creation function now checks if the name is suitable
-saveProject() and loadProject() methods are written
-ProjectManager is updated suitable to saveProject() and loadProject() methods

****

### ~ 26/05/2020 ~
****

-Property names are now compared to previous ones to prevent multiple properties with the same name 
-editClassOptions() and editInterfaceOptions() methods are added to DMenuWizard
-containsSuperInterface() method is added
-Added the creation of an updated class
-Added descriptions for the checkboxes in creatin method

****

### ~ 27/05/2020 ~
****

-Fixed DApp Bugs:
	-The classes are now brought to front when a .diag project file is called
	-Added seperatorLines to outline the differentiation between name, properties and the methods of the object
	-Added updateSeperatorLines() method which edits the seperator lines according to the changing size of particular object
	
-Added and implemented a method which enlarges an object when new property/method is added
-ConstructorMaker now does not add constructors to the abstract classes ad interfaces
-updateSeparators() method added

****

### ~ 28/05/2020 ~
****
-I've worked on fixing project warnings, removing unused class and interfaces, removing unused imports, fixing properties that are not being visible on elements .
-I've worked on updating the class hierarchies process, fixing the multiple lines occuring at once
-I've updated updateHierarchy method 
-ConstuctorMaker bug fixes completely fixed and now works properly 
-I've helped fixing of minor property and add method bugs 
-I've updated DConstructor class to be compatible with the Graphical user interface

****