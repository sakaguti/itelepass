CloudGarden.jar

1. �悸�AitpManager2���C���X�g�[�����Ă��������B
http://www.itplants.com/official_products/software.html

2. data��DB���R�s�[���܂��BWindows�ł̓C���X�g�[���[���s���܂��B
mkdir ~/itpManager
cp -r itpManager/data ~/itpManager/
cp -r itpManager/DB ~/itpManager/

3. itpManager���N�����āA���j������A
���j�� >  �ݒ� > ���O�C�����ݒ�@��I�����܂��B
control.cloud-garden.com��ID��PW����͂��܂��B

4. CloudGardenClient�̋N�����@
www.itplants.com/Download/CloudGardenClient130908.zip
���_�E�����[�h���ĉ𓀂�����ACloudGardenClient�z���_�[�̒��g��itpManager�̃z���_�[�ɃR�s�[���܂��B
itpManager�̃z���_�[�ŋN�����܂��B
java -jar cloudgarden.jar

5. �f�o�b�O
~/itpManager/debug.txt ������΃f�o�b�O�T�[�o�[�ɂȂ���܂��B
devcontrol.cloud-garden.com:8081
���݁Adevcontrol.cloud-garden.com:8081�ł͓��삪�m�F�ł��܂���B�ڑ��ł��Ȃ��悤�ł��B

// ������͋��^�A�C�e�B�v�����^�[�ɑΉ����Ă�����̂ł��B
control.cloud-garden.com:8080
PLT
Amount: 1
Name: ITPLANTER-0
Number: 1  / Version: 1.1  / Serial: 1234567890 / Camera: 1

S_LAMP 1 S_PUMP 0:PTime 0
Temp 200
Water 400
illum 4000

// �����炪 �V�^�A�C�e�B�v�����^�[�ɑΉ����Ă�����̂̂͂��ł��B
devcontrol.cloud-garden.com:8081
PLT
Amount: 1
Name: ITPLANTER-0
Number: 1  / Version: 2.10  / Serial: 1234567890 / Camera: 1

> Version: 2.10�́AVersion: 2.9��������܂���B�Q�̕����Ŕ��ʂ��Ă��܂��B

S_LAMP 1 S_PUMP 0:PTime 0
Temp 20.0
Water 60.0
illum 40000

> Version: 2.X�ł́A�Z���T�[�l�̒P�ʂ��ς���Ă��܂��B

���݁ARaspberry Pi�ł����삵�Ă��܂��B
�N�����ɁA�Z���T�[��񂪁A�Ȃ��Ȃ��\������܂���B�\�������Ɩ��Ȃ��\�����ꑱ���܂��B
�L���b�V���ɗ��܂��Ă���̂ł͂Ȃ��ł��傤���H




