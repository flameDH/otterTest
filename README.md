# 電獺前測說明文件
###### tags: `interview`

假設已裝好java環境
java se 1.8

## 安裝sbt 
    brew install sbt
    
or

    echo "deb https://dl.bintray.com/sbt/debian /" | sudo tee -a /etc/apt/sources.list.d/sbt.list
    sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 642AC823
    sudo apt-get update
    sudo apt-get install sbt

    
## 說明

app為api以及util,helper相關程式碼放置

題目規定的api程式碼放置處為

**app/controllers/user/**
共三支檔案
**UserController.java**
**UserModel.java**
**UserHandler.java**


conf下為config及routes

## config

![](https://i.imgur.com/9CulCyA.png)

此檔案為 /conf/application.conf
在362行處

由於我是用.sql檔交付資料庫structure

要修改的地方為DB

**IP:** DB IP
**SHCEMA:** SHEMA NAME
**USER:** db account
**PASS:** db password

sql檔檔名為:**otterTest.sql**

## 執行

到project目錄下 輸入 sbt

![](https://i.imgur.com/OxSH0mJ.png)

會進入sbt指令介面 在輸入run

![](https://i.imgur.com/FyyfPh6.png)

api URL:

     http://127.0.0.1:9000/api/user

input example:

    {
	    "username":"input"
    }

### 新增api

URL:

http://127.0.0.1:9000/api/car

input:

    {
        "color": "black",
        "brand":"mazuda",
        "city":"KH"
    }

當作對照組使用