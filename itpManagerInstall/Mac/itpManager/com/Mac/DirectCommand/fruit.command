#!/bin/sh
#	fruit   light 14h, pump 6, duty 90
#
# clock adjust
/Applications/itpManager/com/Mac/sendcom -e G
/Applications/itpManager/com/Mac/sendcom -e ^
# duty
/Applications/itpManager/com/Mac/sendcom -e n1
/Applications/itpManager/com/Mac/sendcom -e Y0,0,90
# light
/Applications/itpManager/com/Mac/sendcom -e f1
/Applications/itpManager/com/Mac/sendcom -e W0,300,840
# pump
/Applications/itpManager/com/Mac/sendcom -e g6
/Applications/itpManager/com/Mac/sendcom -e X0,300,1
/Applications/itpManager/com/Mac/sendcom -e X1,450,1
/Applications/itpManager/com/Mac/sendcom -e X2,600,1
/Applications/itpManager/com/Mac/sendcom -e X3,750,1
/Applications/itpManager/com/Mac/sendcom -e X4,900,1
/Applications/itpManager/com/Mac/sendcom -e X5,1050,1
#
/Applications/itpManager/com/Mac/sendcom -e '\'RAM


