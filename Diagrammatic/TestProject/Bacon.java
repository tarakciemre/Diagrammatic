{DProject}
PRO: TestProject


INT: Interactable2

ELE 500 500 600 600 color

END

INT: Interactable3

END

INT: Interactable

EXT Interactable2,Interactable3

END

CLA: Bacon3

END

ABS: AbstractTest

PRO jakeMadeIt,boolean
PRO element0,Number
PRO element1,Number
PRO element2,Number
PRO element3,Number
PRO element4,Number

MET getEaten boolean false

END

ABS: AbstractTest3

END

CLA: Bacon2

EXT AbstractTest

END

ABS: AbstractTest2

EXT AbstractTest3

END

CLA: Bacon

EXT Bacon2

IMP: Interactable,Interactable2

PRO taste,Taste
PRO withPancake,boolean
PRO jakeMadeIt,boolean

MET getEaten boolean false
MET setEaten void false taste,Taste!withPancake,boolean

CON false false false

END

END

COMPLEXLINES
500,500/600,600
400,600/500,600