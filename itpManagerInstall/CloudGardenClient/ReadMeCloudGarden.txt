CloudGarden.jar

1. 先ず、itpManager2をインストールしてください。
http://www.itplants.com/official_products/software.html

2. dataとDBをコピーします。Windowsではインストーラーが行います。
mkdir ~/itpManager
cp -r itpManager/data ~/itpManager/
cp -r itpManager/DB ~/itpManager/

3. itpManagerを起動して、メニュから、
メニュ >  設定 > ログイン情報設定　を選択します。
control.cloud-garden.comのIDとPWを入力します。

4. CloudGardenClientの起動方法
www.itplants.com/Download/CloudGardenClient130908.zip
をダウンロードして解凍した後、CloudGardenClientホルダーの中身をitpManagerのホルダーにコピーします。
itpManagerのホルダーで起動します。
java -jar cloudgarden.jar

5. デバッグ
~/itpManager/debug.txt があればデバッグサーバーにつながります。
devcontrol.cloud-garden.com:8081
現在、devcontrol.cloud-garden.com:8081では動作が確認できません。接続できないようです。

// こちらは旧型アイティプランターに対応しているものです。
control.cloud-garden.com:8080
PLT
Amount: 1
Name: ITPLANTER-0
Number: 1  / Version: 1.1  / Serial: 1234567890 / Camera: 1

S_LAMP 1 S_PUMP 0:PTime 0
Temp 200
Water 400
illum 4000

// こちらが 新型アイティプランターに対応しているもののはずです。
devcontrol.cloud-garden.com:8081
PLT
Amount: 1
Name: ITPLANTER-0
Number: 1  / Version: 2.10  / Serial: 1234567890 / Camera: 1

> Version: 2.10は、Version: 2.9かもしれません。２の部分で判別しています。

S_LAMP 1 S_PUMP 0:PTime 0
Temp 20.0
Water 60.0
illum 40000

> Version: 2.Xでは、センサー値の単位が変わっています。

現在、Raspberry Piでも動作しています。
起動時に、センサー情報が、なかなか表示されません。表示されると問題なく表示され続けます。
キャッシュに溜まっているのではないでしょうか？




