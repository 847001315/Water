# Water
[![Build Status](https://travis-ci.org/847001315/Water.svg?branch=master)](https://travis-ci.org/847001315/Water)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/43f1512c85864978a660718822ab35cc)](https://www.codacy.com/manual/847001315/Water?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=847001315/Water&amp;utm_campaign=Badge_Grade)
![Image text](https://img.shields.io/badge/build-passing-green)	

## 这是一个包含十三水最优解的算法和网络发送请求的项目

### 编译方法
    将项目用gradle导入IDE中，即可运行。
### 运行环境
    jdk 1.8以上，windows系统。
### 使用说明
    沙漠十三水透析文件注意事项
    直接下载exe文件打开即可，要用谷歌下载，用的是Chrome内核
    1.要登陆后才可看到历史战绩和开始界面的牌，要看区别可以先不登录点一次再登陆点一次
    2.恢复后令牌无效则查询不了历史战绩
    3.exe执行比较慢如果出的慢请耐心等待
    4.github下载文件比较慢请耐心等待
### 项目类说明
    Main 负责调配全局，包括开启网络申请
    SSS  负责得到一个指定后墩的最优解
    API  负责网络申请的方式
    其他负责接收网络请求返回体或发送题

### 启动（启动或设置）
    在build.gradle中添加依赖：
    implementation 'com.squareup.retrofit2:retrofit:2.6.2'
    implementation 'com.squareup.retrofit2:converter-gson:2.0.2'
    implementation 'com.google.code.gson:gson:2.8.5'
    implementation 'com.squareup.retrofit2:adapter-rxjava:2.0.2'
    implementation 'com.squareup.okhttp3:logging-interceptor:4.1.0'
    testCompile 'junit:junit:4.12'
    
    然后编译之后，项目如果能够正常运行，说明配置成功
### 功能特性(Features)
    当前仅支持得到一副牌后输出最优解

### 代码示例(Code Examples)
    在添加完依赖库之后，可以建立一个接口（详细可以看代码中的API和Main类）
    接口中写上：
    
    /*登陆*/
    @POST("auth/login")
    Call<login_return> getCall(@Body login k);
    
    然后在你需要的地方（主函数）写上：
    //配置Retrofit 
    Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.shisanshui.rtxux.xyz/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API request = retrofit.create(API.class);
        
    login k = new login("mytest02","test8");//这是请求体。
        
    try {
         Call<login_return> call = request.getCall(k);
         Response<login_return> response = call.execute();

         String result = response.body().getData().token;
         //ghhh[0] =result;
         token=result;
         System.out.println(result);
         System.out.println(response.body().getData().user_id);
     } catch (IOException e) {
            e.printStackTrace();
        }
        
        如果可以成功输出，则说明配置成功。

### 项目状态（Status）
    已经完成，不过可能会有少许改动

### 来源(Sources) 参考资料
    网络请求包：Retrofit2的使用：https://www.jianshu.com/p/a3e162261ab6?tdsourcetag=s_pcqq_aiomsg

### 外部链接(Links) 助教的API
    http://docs.shisanshui.rtxux.xyz/

### 联系(Contact)
    菜鸡不配有姓名
