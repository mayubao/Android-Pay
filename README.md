# Android-Pay
[中文](https://github.com/mayubao/Android-Pay/blob/master/README_CN.md)

A pay library for Android, and which support Wechat pay and Ali pay.
 And developer can easily use Wechat pay in two line code.
 And developer can easily use Ali pay in three line code.

## Setup

### gradle
add these code in the file **build.gradle** as follow:

```xml
dependencies {
    //add pay library
    compile 'io.github.mayubao:pay_library:1.0.1'
}
```

### maven

```xml
<dependency>
  <groupId>io.github.mayubao</groupId>
  <artifactId>pay_library</artifactId>
  <version>1.0.1</version>
  <type>pom</type>
</dependency>
```

## Usage

### Wechat Pay

```java
        //1.create request for wechat pay
        WechatPayReq wechatPayReq = new WechatPayReq.Builder()
                .with(this) //activity instance
                .setAppId(appid) //wechat pay AppID
                .setPartnerId(partnerid)//wechat pay partner id
                .setPrepayId(prepayid)//pre pay id
//								.setPackageValue(wechatPayReq.get)//"Sign=WXPay"
                .setNonceStr(noncestr)
                .setTimeStamp(timestamp)//time stamp
                .setSign(sign)//sign
                .create();
        //2. send the request with wechat pay
        PayAPI.getInstance().sendPayRequest(wechatPayReq);


        //set the callback for wechat pay
        //wechatPayReq.setOnWechatPayListener(new OnWechatPayListener);


```

>Notes：WechatPayReq have no method to set the money, because the money info is include in the parameter 'prepayid'.

### Ali Pay 


#### First way(**Not Recommend**, and its partner rsa private key export in the client , it is very dangerous!)
```java

        //step 1 create config for ali pay
        AliPayAPI.Config config = new AliPayAPI.Config.Builder()
                .setRsaPrivate(rsa_private) // rsa private key from partner (pkcs8 format)
                .setRsaPublic(rsa_public)//ali rsa public key
                .setPartner(partner) //set partner
                .setSeller(seller) //set partner seller accout
                .create();

        //step 2 create reqeust for ali
        AliPayReq aliPayReq = new AliPayReq.Builder()
                .with(activity)//Activity instance
                .apply(config)// the above custome config
                .setOutTradeNo(outTradeNo)//set unique trade no
                .setPrice(price)//set price
                .setSubject(orderSubject)//set order subject
                .setBody(orderBody)//set order detail
                .setCallbackUrl(callbackUrl)//set callback for pay reqest
                .create()//
                .setOnAliPayListener(null);//

        //step 3 send the request for ali pay
        PayAPI.getInstance().sendPayRequest(aliPayReq);

        // set the ali pay callback
        //aliPayReq.setOnAliPayListener(new OnAliPayListener);

```

#### Second way(**Highly Recommend**)

```java
        //step 1 create raw ali pay order info
        String rawAliOrderInfo = new AliPayReq2.AliOrderInfo()
                .setPartner(partner) //set partner
                .setSeller(seller)  //set partner seller accout
                .setOutTradeNo(outTradeNo) //set unique trade no
                .setSubject(orderSubject) //set order subject
                .setBody(orderBody) //set order detail
                .setPrice(price) //set price
                .setCallbackUrl(callbackUrl) //set callback for pay reqest
                .createOrderInfo(); //create ali pay order info


        //step 2 get the signed ali pay order info
        String signAliOrderInfo = getSignAliOrderInfoFromServer(rawAliOrderInfo);

        //step 3 step 3 send the request for ali pay
        AliPayReq2 aliPayReq = new AliPayReq2.Builder()
                .with(activity)//Activity instance
                .setSignedAliPayOrderInfo(signAliOrderInfo)
                .setRawAliPayOrderInfo(rawAliOrderInfo)//set the ali pay order info
                .setSignedAliPayOrderInfo(signAliOrderInfo) //set the signed ali pay order info
                .create()//
                .setOnAliPayListener(null);//
        PayAPI.getInstance().sendPayRequest(aliPayReq);


        //set the ali pay callback
        //aliPayReq.setOnAliPayListener(new OnAliPayListener);

```


## Proguard

```xml

#pay_library
-dontwarn io.github.mayubao.pay_library.**
-keep class io.github.mayubao.pay_library.** {*;}

#wechat pay
-dontwarn com.tencent.**
-keep class com.tencent.** {*;}


#alipay
-dontwarn com.alipay.**
-keep class com.alipay.** {*;}

-dontwarn  com.ta.utdid2.**
-keep class com.ta.utdid2.** {*;}

-dontwarn  com.ut.device.**
-keep class com.ut.device.** {*;}

-dontwarn  org.json.alipay.**
-keep class corg.json.alipay.** {*;}
```


## Document

###  wehcat pay official document
https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=8_5

###  ali pay official document
https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.sdGXaH&treeId=204&articleId=105296&docType=1


## Help
If it is helpful to you, could you buy me a cup of coffee?

### Wechat
![](http://img.blog.csdn.net/20170302140650271)
### Ali
![支付宝支付](http://img.blog.csdn.net/20170302140734345)


## Lisence

    Copyright 2017 mayubao

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


