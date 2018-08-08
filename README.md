 [![Download](https://api.bintray.com/packages/wankey/maven/pay/images/download.svg) ](https://bintray.com/wankey/maven/pay/_latestVersion)

#RxPay
使用RxJava代码风格的支付lib，支持支付宝和微信支付

##使用方式
1. 添加repository<br>
maven { url "https://dl.bintray.com/wankey/maven/" }<br>
2. 添加依赖<br>
implementation "io.github.wankey.mithril:pay:{$LATEST_VERSION}"<br>
//如果需要支持微信支付，添加下面的依赖<br>
implementation "com.tencent.mm.opensdk:wechat-sdk-android-without-mta:{$LATEST_VERSION}"<br>
//如果需要支持支付宝，请将相关lib文件复制到libs目录下<br>
3. 在Application中初始化
4. 使用支付方式对应的构造函数构造PayBean
5. 调用支付APi
```
RxPay.pay(activity,RxPay.PAY_TYPE,payBean)
     .subscribe(PayResult->{})
```
