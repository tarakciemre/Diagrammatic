# CS102 ~ Personal Log page ~

## Murat Kaan Özbaş


On this page I will keep a weekly record of what I have done for the CS102 group project. This page will be submitted together with the rest of the repository, in partial fulfillment of the CS102 course requirements.

### ~ 14/04/20 ~
This week I learned some more javaFX, went into documentation about Application Scene Stage etc and wrote some of my labs in FX. 

### ~ 21/04/20 ~
This week I helped with the detailed design for the logic part of our project with the rest of the team. We started writing our first pieces of code.

I wrote the DObject class. And then edited some of the other classes by adding many missing methods such as DGeneralClass methods for DProperty and DMethod fields and added toString()s to all classes. More is on the commit itself.

### ~ 29/04/2020 ~
****

I updated DAbstractClass.
I updated DConstructor class.

****

###  ~ 1/05/2020 ~
****

I helped planning of the FileManager class.
I helped planning of the GUI classes and interfaces.

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
-*Resize class is updated ( Later called DApp)*
	-updateSize() 
	-drawCenteredLine()
	-showClosest()
	-getClosestPoint() methods are added



### ~ 18/05/2020 ~
****

-Element class is seperated from DApp class
-Menu bar is added
-Update methods are created for Element and Circle objects
-ScrollPane is added to the GUI
-DisplayFieldOptions() method is added
****

### ~ 19/05/2020 ~
****

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

### ~ 20/05/2020 ~
****
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
****

### ~ 27/05/2020 ~
****

-DApp bugs fixed
-ConstructorMaker now does not add constructors to the abstract classes ad interfaces
-updateSeparators() method added
****

### ~ 28/05/2020 ~
****

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