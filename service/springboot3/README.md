## 使用说明

 使用说明[参考](../springboot/README.md)

 springboot 和springboot3目录 功能相同，都是使用示例demo

## 版本依赖
   区别于springboot项目(springboot2.x)，springboot3.x 版本依赖有些变化，jdk需要使用jdk17，maven版本需要使用3.8.1以上版本
   maven 3.8.1+
   [openjdk 17](https://d.injdk.cn/d/download/openjdk/17/openjdk-17_windows-x64_bin.zip)

## 重要说明
  - 设计规范
    - core/ 纯java业务逻辑实现，无框架依赖，保持稳定。
    - captcha-spring-boot-starter/  spring框架集成starter,尽量不依赖spring特定版本，也即spring版本无关。
      
    ****以上目录也即java版本无关、spring版本无关，请慎重修改****
    
    - springboot springboot2.x版本
    - springboot3 springboot3.x版本示例
    - springmvc springmvc版本示例
  
  - PR提交规范
    - 鼓励提交之前，提交issue，讨论问题（群里或issue comment)，避免重复提交。
    - 不要直接提交到Master分支，建议提交到dev分支，提交到master分支会拒绝。

   