# Water
# README
[![Build Status](https://travis-ci.org/847001315/Water.svg?branch=master)](https://travis-ci.org/847001315/Water)
## 这是一个包含十三水最优解的算法和网络发送请求的项目

### 技术栈(Technologies)
    Java 8
    IDE IJ 2019 
    
### 项目类说明
    Main 负责调配全局，包括开启网络申请
    SSS  负责得到一个指定后墩的最优解
    API  负责网络申请的方式
    others 负责接收网络请求返回体或者发送题
### 启动(Launch or Setup)
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
