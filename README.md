# lanzhou-yuanfen

#### 介绍
一个专注拉屎的网站

#### 软件架构
springboot security mybatisplus ps6y ...


#### 安装教程

1.  git clone
2.  import project
3.  run

#### 使用说明

开始使用....

运行环境:
    TODO


构建: 
    将本项目install成jar包, 然后放在/home/temp/
    运行命令:
        docker build -t yuanfen:0.0.1-SNAPSHOT .
        
运行:
    运行命令:
        docker run -p 80:8080 --name yuanfen \
        --link mysql:tomysql \
        -v /etc/localtime:/etc/localtime \
        -v /home/temp/yuanfen/logs:/var/logs \
        -d yuanfen:0.0.1-SNAPSHOT            



1.  版权所有, 请勿用于商业研究
2.  账号请勿传播
3.  支持多种打赏方式

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 码云特技

1. 2019.12.04: 添加炫酷首页, 修改之前的游客登入能直接进入主页问题, 添加数据库支持
2. 2019.12.09: 添加邮箱支持, 初步支持发送邮箱, 后期将会添加邮箱(EntryPoint)登入
3. 2019.12.10: 添加邮箱登入支持, 后期优化页面和登入逻辑
3. 2020.05.15: 添加QQ登入支持, 后期优化页面和登入逻辑