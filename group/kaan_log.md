# CS102 ~ Personal Log page ~

## Murat Kaan Özbaş


On this page I will keep a weekly record of what I have done for the CS102 group project. This page will be submitted together with the rest of the repository, in partial fulfillment of the CS102 course requirements.

### ~ 14/04/20 ~
-This week I learned some more javaFX, went into documentation about Application Scene Stage etc and wrote some of my labs in FX. 

### ~ 21/04/20 ~
-This week I helped with the detailed design for the logic part of our project with the rest of the team. We started writing our first pieces of code.

-I wrote the DObject class. And then edited some of the other classes by adding many missing methods such as DGeneralClass methods for DProperty and DMethod fields and added toString()s to all classes. More is on the commit itself.

### ~ 29/04/2020 ~
****

-I updated DAbstractClass.
-I updated DConstructor class.

****

###  ~ 1/05/2020 ~
****

-I helped planning of the FileManager class.
-I helped planning of the GUI classes and interfaces.

****

### ~ 8/05/2020 ~
****

-I helped design of Resize class.
-I helped design of Resize class listeners.
-I helped design of upcoming stages of drawing arrows.


### ~ 18/05/2020 ~
****

-Helped with the redesign of GUI classes
-Added Menu Bar layout
-DisplayFieldOptions() method is added which will be used to add new properties to a represented class.

****

### ~ 19/05/2020 ~
****

-Extract methods are updated, and menu calls to them are commented out... for now
-Element has labels to represent properties and methods
-Menu options for adding properties and methods to classes are fully added
-setInheritance and getInheritance methods: although we disagree on its design these will be used to link the superclass in gui the moment the object is added 
-Syntax errors in GUI are eliminated
-Icon is added to the project
-projectToText() method is edited
-Accesible interface is added, with a typo though which Osman fixd
-Committed the Resize -> DApp change with consultation from the group.
-Added iniELements and drawHierarchy! now with these methods we can create a DProject as we like and the GUI should handle the work itself. Instead manually telling it to which lines it should draw, the view tree(?) is in synch with GUI representation of it. 

****

### ~ 20/05/2020 ~
****
-improved removeClass() 
-ArrowHead is edited to calculate its direction automatically
-drawHierarchy() method is updated
-Element is now 2: ClassElement and InterfaceElement.
-Color options are created for the objects, and the objects are now created with random colors from these selected colors. I translated the colors into base16.

****

### ~ 22/05/2020 ~
****

-improved DMethod
-Added safeguards: class creation function now checks if the name is suitable

****

### ~ 26/05/2020 ~
****

-Added safeguard: Property names are now compared to previous ones to prevent multiple properties with the same name, which java does not allow and also causes major problems
-added containsSuperInterface in the process

****

### ~ 27/05/2020 ~
****

-Also fixed bugs like everyone today. 
-Did ton of tests to find more bugs to eliminate.
-Helped Osman in constructor addition and showing parts

****

### ~ 28/05/2020 ~
****

-bugfixes, mostly on previous days new bugs.
-updated method additon
-updated logic : equals methods that were needed

****