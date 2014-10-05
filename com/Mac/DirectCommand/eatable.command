#!/bin/sh
#	eatable   light 14h, pump 2, duty 60
#
# clock adjust
/Applications/itpManager/com/Mac/sendcom -e G
/Applications/itpManager/com/Mac/sendcom -e ^
# duty
/Applications/itpManager/com/Mac/sendcom -e n1
/Applications/itpManager/com/Mac/sendcom -e Y0,0,60
# light
/Applications/itpManager/com/Mac/sendcom -e f1
/Applications/itpManager/com/Mac/sendcom -e W0,480,840
# pump
/Applications/itpManager/com/Mac/sendcom -e g2
/Applications/itpManager/com/Mac/sendcom -e X0,300,1
/Applications/itpManager/com/Mac/sendcom -e X1,1020,1
#
/Applications/itpManager/com/Mac/sendcom -e '\'RAM


