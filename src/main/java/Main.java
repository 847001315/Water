import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    //这里主要是 调用SSS类，每调用一次，返回一个非常OK的类，我想想，每次传入的参数不一样，代表代码运行的顺序不同
    // //初步设定
    //        //五毒 0  炸弹 1
    //        //葫芦 2  同花 3  顺子 4  三条 5  多对6 单对7
    public static void main(String[] args){

        /*Main
            先执行 那个登陆的情况。如果可以的话，再去执行其它的
            得到一个字符串。
            * */
        /*-----------------------------------*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.shisanshui.rtxux.xyz/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        API request = retrofit.create(API.class);
        login k = new login("mytest02","test8");
        //这上面的基本不用改；
        String token="";

        //final String[] token = {" "};
//        call.enqueue(new Callback<login_return>() {
//            @Override
//            public void onResponse(Call<login_return> call, Response<login_return> response) {
//               // token[0] =response.body().getData().token;
//                        System.out.println(response.body().getStatus());
//                        System.out.println(response.body().getData().user_id);
//                        System.out.println(response.body().getData().token);
//            }
//
//            @Override
//            public void onFailure(Call<login_return> call, Throwable throwable) {
//                System.out.println("gg");
//            }
//        });
//        final String[] ghhh = new String[1];
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
        String card="";
        System.out.println("好像失败了");
        int id=0;
        try {
            Call<open_return> call = request.getCall(token);
            Response<open_return> response = call.execute();
            card=response.body().data.card;
            id=response.body().data.id;
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(card);
        System.out.println(id);


        /*-----------------------------------*/

        int m[];
        m = new int[10];
        String a=card;
        //String a ="#A #2 #3 #4 #5 #6 #7 #8 #9 #10 #J #Q #K";
        //"$2$3$4$5$6*5$Q#8&8*A*8&3*3"
        //"$2&2$8&5$10*5$Q#8&8*A*8&3*3"
        //"$2$3$4$A$6&8*2*5*A*8*3&3#8"
        SSS K = new SSS(a,1);
        K.returnkmk(m);//判断后墩可能有哪些组合

       // K.start();
        int i=0;
        SSS zongti[];
        zongti=new SSS[8];
        for(i=0;i<8;i++){
            if(m[i]==1){//目前先遍历到4 因为三条还没有写，然后 也不那么重要，所以 我就先不写三条。
                zongti[i]=new SSS(a,i);
                zongti[i].start(i);
            }
        }
        double max=0 ;
        int max_i=0,max_j=0;
        for(i=0;i<8;i++){
            if(m[i]==1){
                if(max<zongti[i].ziwojuezhu()){
                    max=zongti[i].ziwojuezhu();
                    max_i=i;
                    max_j=zongti[i].max_j;
                }
            }
        }

        zongti[max_i].printwai();
        Puke puke=new Puke(zongti[max_i].result[max_j],id);
        //这边传回不同的类型组合/。
        //然后这边再判断。
        String msg="";

        try {
            Call<Submit_return> call = request.getCall(token,puke);
            Response<Submit_return> response = call.execute();
            msg=response.body().getData().msg;
        }catch (IOException e){
            e.printStackTrace();
        }
        System.out.println(msg);
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        String jsonObject = gson.toJson(puke);
        System.out.println(jsonObject);
        System.out.println(puke.card[0]);


    }


}
