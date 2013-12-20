AndroidAnzhiGame
================

安智android平台ANE（官方登录版）
## 特别说明
* 此ANE只针对目前版本，若安智官方更新的SDK，可能会不适用

## 编写ANE过程

* 参照我博客的教程[传送门](http://www.shadowkong.com/archives/1090)

## 打包ANE过程
* 参照`BuildANE`文件夹下的命令行

## 打包APK过程
* 参照`BuildAPK`文件夹下的命令行


## 银联兼容处理方式
* 使用apktool 反编译修改`res/raw`文件夹下的0字节文件，往里面随便加内容。
* 使用apktool 回编译
* 签名优化后 便是`aneTest`文件夹下的`anzhi.apk`

> `Apktool 命令要例：`
> 
> 反编译：`apktool d v.apk v_dir`
> 
> 修改文件:`v_dir/res/raw/debuginfo`随便增加内容
> 
> 回编译:`apktool b v_dir v_new.apk`
> 
> 签名举例:`jarsigner -verbose -sigalg MD5withRSA -digestalg SHA1 -keystore rect.keystore`  `-storepass shadowkong -signedjar v_new_signed.apk v_new.apk rect.keystore`
> 
> 优化举例:`zipalign -f -v 4 v_new_signed.apk anzhi.apk`



## DEMO和参数配置
* 参照`aneTest`文件夹下的源码 和 APK文件

## 作者

* [platformANEs](https://github.com/platformanes)由 [zrong](http://zengrong.net) 和 [rect](http://www.shadowkong.com/) 共同发起并完成。