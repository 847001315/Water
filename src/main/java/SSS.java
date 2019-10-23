public class SSS {
    //所以这里面必须得有的参数是：
    //一个 ab数组 一个 a数组 一个b数组
    //葫芦 同花 顺子 对子
    //特殊牌型 同花顺 炸弹的个数。
    //存储牌组
    //遇到的问题有很多，其中最明显的是 六七张同花和 六七张顺子的问题。
    private int fuzetype;
    int ab[][];
    private int a[];//行
    private int b[];//列
    private int hulu;
    private int tonghua;
    private int shunzi;
    private int duizi;
    private int danpai;
    private int shunxu;
    int gui=0;
    double result[][];//返回的是这个数组。结果数组+后中上的类型计算。从15-23
                //是：《同花顺，炸弹，葫芦，同花，顺子，三条，两对，对子，空》 15-23，累加算法，除非 同花或者顺子等于2 否则前墩没有顺子和同花的说法。
                //是：《  15 ， 16 ，17 ， 18 ， 19， 20 ， 21 ， 22 ，23》
                //再把26作为得分的位置吧；
    private int result_arr;
    private int number_result;
    private int select_hou;
    private int select_mid;
    private int score_now;
    private int score_best;
    private boolean mid_wudu;
    private boolean mid_zhadan;
    private boolean mid_hulu;
    private boolean mid_tonghua;
    private boolean mid_shunzi;
    private boolean mid_shuangdui;
    private boolean mid_duizi;
    private boolean mid_three;
    private boolean mid_zapai=false;
    int score_arr=0;
    public int max_j=0;
    private int score_tonghuashun=100; //同花顺的得分； 100
    private int score_zhadan=80;//炸弹的得分  80
    private int score_hulu=20;//葫芦的得分  20
    private int score_tonghua=15;//同花的得分  15
    private int score_shunzi=10;//顺子的得分  10
    private int score_santiao=8;//三条的得分 8
    private int score_liangdui=6;//两对的得分 6
    private int score_duizi=3;//对子的得分 3
    private int xiadun_bili=1;//下顿的比例 1
    private double zhongdun_bili=1.5;//中墩的比例 1.5
    private  int shangdun_bili=2;//上墩的比例 2
    //构造函数，负责初始化。
    private int zhongdunleixinggeshu=0;//中墩中有的类型个数
    private  boolean daoshui=false;
    private  int xiadunguanjian=0;
    private boolean teshu=false;
    public SSS(String g, int sx){
        int t = 0;
        ab=new int[4][15];
        a=new int[4];
        b=new int[15];
        hulu = 0;
        tonghua = 0;
        shunzi=0;
        duizi = 0;
        danpai = 0;
        shunxu=sx;
        result=new double[200][30];
        result_arr=0;
        select_hou=1;//后墩是否有分支
        select_mid=1;//中墩是否有分支
        score_best=0;
        score_now=0;
        mid_hulu = false;
        mid_tonghua = false;
        mid_shunzi = false;
        mid_shuangdui = false;
        mid_duizi = false;
        mid_three = false;
        for(int m =0;m<g.length();m=m+3){

            //  "$2 &2&3*4 $8&9&10*5$J&Q&K*A*8";
            t = g.charAt(m+1)-48;
            if(t==1)
            {
                if(g.charAt(m+2)=='0'){

                    t=10;
                }
            }else{
                t=change_data(t);
            }

            switch (g.charAt(m)){
                case '$':
                    //黑桃
                    ab[0][t]++;
                    a[0]++;
                    b[t]++;
                    break;
                case '&':
                    //红桃
                    ab[1][t]++;
                    a[1]++;
                    b[t]++;
                    break;
                case '*':
                    //梅花
                    ab[2][t]++;
                    a[2]++;
                    b[t]++;
                    break;
                case '#':
                    //方块
                    ab[3][t]++;
                    a[3]++;
                    b[t]++;
                    break;
            }
            if(t==10){
                m++;
            }
        }

    }
    public void start(int type){
        fuzetype=type;
        ;//负责这个类的顺序控制函数
        //special_pai();//判断了特殊牌，以后可以设定如果特殊牌就返回true，然后就直接特殊牌爆牌；

        judge_type(type);//这里面应该写多个循环
        //printsss();
    }
    /**  代码的核心思想就是，现确定后墩类型，再去进行多种，中墩与后墩我写的算法暂时不一样，我想直接就考虑五种情况 ，分别是 **
     **  葫芦同花顺子两对或者对子。因为这样就不用考虑同花和顺子交叉的问题了，唯一的问题就考虑一下是否存在同类型倒水的问题 **/
    private int change_data(int t ){
         if(t==26){
            t=11;
        }else if(t==65-48){
            t=14;
        }else if(t==81-48){
            t=12;
        }else if(t==75-48){
            t=13;
        }
        return t;
    }


    private void special_pai(){
        //判断特殊牌型 例如至尊青龙这类的，具体怎么弄还是 之后再说
        //

        teshu =false;
        int tong3=0,tong5=0,tong8=0;
        int end1=0;
        int end2=0;
        int end3=0;
        for(int i = 0 ; i<4;i++){
            int quanda=0;
            int m=0;//用于一条龙的
            int k=0;//用于十二皇族和 四怪冲三
            int k2=0;//用于三分天下
            int kdui=0;
            for(int j = 2;j<15;j++){

                //用于全打或者全小。

                if(b[j]>=1){
                    m++;
                }
                if(b[j]>=2){
                    kdui++;
                }
                if(b[j]==0 && j<8){
                    quanda++;
                }
                if(b[j]>=3 ){
                    k++;
                }

                if(b[j]==4){
                    k2++;


            }
            }if(m==13 || k ==4 || k2==3   || kdui==6){
                teshu = true;
            }

            if(a[i]==3){
                if(tong3==0){
                    tong3=1;
                }
            }else if(a[i]==5){
                tong5++;
            }else if(a[i]==8){
                tong5++;
                if(tong3==0){
                    tong3=1;
                }
            }
            if(tong3==1 && tong5 ==2){
                teshu=true;
            }
            if(teshu==true){
                break;
            }
            if(a[i]==13){
                teshu=true;
                break;
            }
        }
        //三顺子和同花顺放在外面判断吧。
        int btidai[];
        btidai=b.clone();
        if(teshu==false){
            //三顺子一定只会有一个断点。先找最大点。
            boolean sanshunzi=true;
            int m = 0;
                    int max=0;
            for(int i = 0 ;i <2;i++){
                m=0;
                for(int j =14;j>=2;j--){
                    if(btidai[j]>=1){
                        max=j;
                        break;
                    }
                }
                for(int j=max;j>=2;j--){
                    btidai[j]--;
                    if(btidai[j]<0){
                        sanshunzi=false;
                    }
                    m++;
                    if(m==5){
                        break;
                    }
                }
            if(sanshunzi==false){
                break;
            }
            }

          teshu=sanshunzi;
        }
    }

    /*核心结构*/
    private void judge_type(int type){
        int abclone[][]=new int[4][15];
        int aclone[];
        int bclone[];
        ;//判断这个牌的所有类型
        //就先以葫芦为例子吧
        //得到后墩以及后墩的好几种可能性 并且形成一个参数 select_hou = 1；随着葫芦的对子个数 形成不一样的select。
        //for（）对于得到的后墩，生成多个中盾，以及前墩，看看怎么组合分数最高。 取出分数最高的，得出答案。
        //大概就是 一个 1->3->9的过程，所以还要有一个select_mid;
        //用两三个数组，记录下来最高分，这里*******///*** 可以考虑拓展成次高分 和 次次高分
        for(int i=0 ;i <ab.length;i++){
            abclone[i]=ab[i].clone();
        }
         abclone=ab.clone();
         aclone=a.clone();
         bclone=b.clone();

        for(int p=1;p<=select_hou;p++){
            for(int i=0 ;i <abclone.length;i++){
                ab[i]=abclone[i].clone();
            }
            daoshui=false;
            a=aclone.clone();
            b=bclone.clone();
            result_arr=0;
            //*写一个后墩函数。
            get_houdun(type,p);//得到一个数组，然后数组的分已经写了，以及mid-hulu等变量判断完成
            int bclone2[];
            bclone2=b.clone();
            int aclone2[];
            aclone2=a.clone();
            int abclone2[][]=new int[4][15];
            for(int i=0 ;i <abclone2.length;i++){
                abclone2[i]=ab[i].clone();
            }
            if(zhongdunleixinggeshu==0){
                number_result++;
            }
            for(int y=0;y<zhongdunleixinggeshu;y++){//这是执行同一个后墩

                for (int t=0;t<select_mid;t++){
                    for(int i=0 ;i <abclone2.length;i++){

                        ab[i]=abclone2[i].clone();
                    }
                    b=bclone2.clone();
                    a=aclone2.clone();
                    //这个循环就是把 比如说 中墩是双对  的所以组合都弄出来。
                    get_zhongdun();//将中墩的卡片一一放进result数组上,再加个判断，如果number_result>0 ，则复制前面的数组。
                    compare_score();//将前墩的卡片放入，并且给其判断，再算得分。10.4还未实现。这么说来，基本可以说必须要用二维数组了。
                    number_result++;
                }

                //在这里把那些判断变量改为正的。
                if (mid_wudu==true){
                    mid_wudu=false;
                }else if (mid_zhadan==true){
                    mid_zhadan=false;
                }else if(mid_hulu == true){
                    mid_hulu=false;
                }else if(mid_tonghua==true){
                    mid_tonghua=false;
                }else if (mid_shunzi==true){
                    mid_shunzi=false;
                }else if(mid_three==true){
                    mid_three=false;
                }else if(mid_shuangdui==true){
                    mid_shuangdui=false;
                }else if(mid_duizi== true){
                    mid_duizi=false;
                }
            }

        }

        //经历了这些循环之后，能够得出最高分的排列是什么了。就可以就将其输出出去；
    }
    private void get_houdun(int type,int p){
        //现确定要判断的类型，然后再去寻找分支。
        //初步认定是，先传入一个数字，然后再去判断类型。比如，我们就先不管数字，先判断葫芦这种情况，即出现了 葫芦+多对的情况
        if(type==0){
            //判定五毒
            judge_wudu(p);
        }else if(type==1){
            //判断炸弹
            judge_zhadan(p);
        }else if(type==2){
            judge_hulu(p);//判断出存在三条和对子，以及把select hou给赋值了，再同时还选出了自己想要的葫芦。我只想在这里得到葫芦，没有葫芦我就不做了
        }else if(type==3){
           judge_tonghua(p);
        }else if(type==4){
            judge_shunzi(p);
            //顺子
        }else if(type==5){
            //三条
            judge_santiao();
        }else if(type==6){
            judge_shuangdui(p);
            //两对
        }else if(type==7){
           judge_duizi();
            //单对
        }
        xiadunguanjian=type+15;
        //这里写判断，要

        //get_score(result[number_result]);//这里可以写一个得分的累计的过程。
                    //估计这里还要写一个判断后面八张牌中，中墩可能会出现的情况，最不想用就是true语句，估计要用了。
        panduanleixing(); //负责判断中墩的可能类型变量。
    }

    private void panduanleixing(){
        for(int j = 0 ;j<15;j++){
            if(b[j]==4){
                mid_zhadan=true;
                gui++;
            }
        }

        //判断葫芦
        for(int j =0;j<15;j++){
            if(b[j]==3 ){
                for(int m = 0 ; m<15;m++){
                    if(b[m]>=2 && m!=j){
                        mid_hulu=true;
                        gui++;
                    }
                }
                mid_three=true; //三条
            gui++;
            }
        }
        //同花
        for(int i=0;i<4;i++){
            if(a[i]>=5){
                mid_tonghua=true;
                gui++;
            }
        }

        //顺子
        int m=0;
        for(int j=0;j<15;j++){
            if (b[j] > 0) {
                m++;
                if(m==5){
                    mid_shunzi=true;
                    gui++;
                }

            }else {
                m=0;
            }
        }




        //两对或者以上 中墩为对子；
        int u=0;
        for(int j=0;j<15;j++){
            if(b[j]==2){
                u++;
            }
        }if(u>=2){
            mid_shuangdui=true;
            gui++;
        }
        //中墩为对子；
        if(u<=2 && u>0){
            mid_duizi=true;
            gui++;
        }

        if(gui==0){
            mid_zapai=true;
        }
        zhongdunleixinggeshu=0;
        if(mid_zhadan==true){
            zhongdunleixinggeshu++;
        }
        if(mid_hulu==true){
            zhongdunleixinggeshu++;
        }
        if(mid_tonghua==true){
            zhongdunleixinggeshu++;
        }
        if(mid_shunzi==true){
            zhongdunleixinggeshu++;
        }
        if(mid_three==true){
            zhongdunleixinggeshu++;
        }
        if(mid_shuangdui==true){
            zhongdunleixinggeshu++;
        }
        if(mid_duizi==true){
            zhongdunleixinggeshu++;
        }
        if(mid_zapai==true){
            zhongdunleixinggeshu++;
        }


        /*先做关于同花和葫芦的判断。其他的先不管了，10.4凌晨留*/
    }

    //中顿识别并且将其填入数组中
    private void get_zhongdun(){
        //寻找 是否葫芦 同花 顺子 两对，对子
        //现确定要判断的类型，再去寻找分支；
        //得到一个稳定的中墩，且可以行使的。
        //这里可以写一个得分的累计的过程，注意这时候加上了中墩，比例要调整。可以认为写了一个score函数，在不同墩的比例是不一样的，参数有挺多。
        //先判断，如果有的话，就说明就select mid ++；

        //这个函数的任务就是把我所要的类型找出来，并且给予对应的数组赋值。

        //给多个数组复制相同的后墩
        //对于不同的后墩，写错了

        if(result[number_result][1]==0){
            //没被赋值过.

        if(number_result>0){
            for(int q=0;q<5;q++){
                result[number_result][q]=result[number_result-1][q];
            }
                result[number_result][xiadunguanjian]=1;
        }
        xiadunguanjian=24;
        }
        int muqian=0;
        for(int i =15;i<24;i++){
            if(result[number_result][i]==1){
                muqian=i;
                break;
            }
        }
        result_arr=5;//让下一个数组从5开始。
        //在这个if语句里面记得给数组十几赋值。
        if(mid_zhadan){
            for(int j =14;j>0;j--){
                if(b[j]==4){
                    for(int i=0;i<4;i++){
                        result[number_result][result_arr]=(i+1)*100+j;
                        result_arr++;
                        ab[i][j]=0;
                        a[i]--;
                        b[j]--;
                    }
                    break;
                }

            }
            int k =0;
            for(int j = 0;j<15;j++){
                //第一种，找到了单排；
                //第二种，全是大于一张牌的。
                if(b[j]==1){
                    k++;
                    for(int i=0;i<4;i++){
                        if(ab[i][j]==1){
                            result[number_result][result_arr]=(i+1)*100+j;
                            result_arr++;
                            ab[i][j]=0;
                            a[i]--;
                            b[j]--;
                            break;
                        }

                    }
                }
                if(k==1){
                    break;
                }
            }
            if(k!=1){
                for(int j = 2;j<15;j++){
                    if(b[j]==2){
                        k++;
                        for(int i=0;i<4;i++){
                            if(ab[i][j]==1){
                                result[number_result][result_arr]=(i+1)*100+j;
                                result_arr++;
                                ab[i][j]=0;
                                a[i]--;
                                b[j]--;
                                break;
                            }

                        }
                    }
                    if(k==1){
                        break;
                    }
                }

            }
            result[number_result][16]++;
        }
        else if(mid_hulu){//17
            //关于葫芦的判定。所以先注意一下有没有倒水。应该是不会倒水
            //既然存在葫芦，就说明，从后往前找，找得到3个，并且，选择最小的对子，当成附庸,所以不用考虑是否select-mid
            for(int j =14;j>0;j--){
                if(b[j]==3){
                    for(int i=0;i<4;i++){
                        if(ab[i][j]==1){
                            result[number_result][result_arr]=(i+1)*100+j;
                            result_arr++;
                            ab[i][j]=0;
                            a[i]--;
                            b[j]--;
                        }

                    }
                    break;
                }
            }

            for(int j=0;j<15;j++){
                if(b[j]>=2){
                    int m=0;
                    for(int i=0;i<4;i++){
                        if(ab[i][j]==1){
                            result[number_result][result_arr]=(i+1)*100+j;
                            result_arr++;
                            ab[i][j]=0;
                            a[i]--;
                            b[j]--;
                            m++;
                        }

                        if(m==2){
                            break;
                        }
                    }
                    break;
                }
            }

            result[number_result][17]++;

        }else if(mid_tonghua){//18

            int i=0,quchu=0;
            //当同花个数n多于5个 则说明  mid-select = n
            for( i =0 ; i<4;i++){
                if(a[i]>=5){

                    if(a[i]>=6 ){
                        for(int j =14;j>0;j--){
                            if(ab[i][j]==1){
                                if(b[j]>1){
                                    quchu=j;//要特别优待，取出这一个。
                                    break;
                                }
                            }
                        }
                    }
                    break;
                }

            }

            int sks=0;
            for(int j = 0 ;j<15;j++){
                if(quchu!=j){
                    if(ab[i][j]==1){
                        sks++;
                        ab[i][j]=0;
                        b[j]--;
                        result[number_result][result_arr]=(i+1)*100+j;
                        result_arr++;
                    }
                }
                if(sks==5){
                    break;
                }

            }
            a[i]=a[i]-5;
            result[number_result][18]++;

        }else if(mid_shunzi){//19
            //当顺子个数n多于5个 则说明  mid-select=n-5

            /*先写到这里吧 现在是 10/5号 0.36分 我去睡觉了 明天直接来写321行*/
            int m=0,flag=0,number=0,last=0;
            for(int j=0 ; j<15 ;j++){
                if(b[j]>=1){
                    m++;
                    if (m>=5){
                        number=m;
                        last=j;
                    }
                }else {
                    m=0;
                }
                if(m==5){
                    flag=j;
                    //说明从第flag个开始，然后 如果
                }
            }
            //这个顺子有number个，从flag个开始。
            select_mid=number-4;//这个东西，无论遍历即便都不会变的
                                //判断如果是六个的话，最后最头，如果七个的话，最后两个，或者最头两个。

            /*下面这段注释代码的原意是想，直接就果断的判断出来 哪种好，但后来发现 情况特别多，决定把任务交给得分体系去完成。*/
//            if(number==6){
//                if(b[last]==2){
//                    //执行，把last前一个开始，往前五个取走。
//                }else if(b[flag]==2){
//
//                }
//            }else if(number==7){
//                if(b[last]==2){
//                    //执行，把last前一个开始，往前五个取走。
//                }else if(b[last-1]==2){
//                    //执行last-1前一个开始，往前五个取走。
//                }else if(b[flag]==2){
//                    //执行 flag前两个开始，往后五个取走。
//                }else if(b[flag+1]==2){
//                    //执行flag前一个开始，往后五个取走。
//                }
//
//            }else if(number==5){
//                //执行从last哪里往前五个取走。
//            }


            //这下面就是分离的函数，从第flag-1+mid-select个开始
            m=0;
            for(int g=flag-1+select_mid;g>1;g--){

                for(int i=0;i<4;i++){
                    if(ab[i][g]==1){
                        result[number_result][result_arr]=(i+1)*100+g;
                        result_arr++;
                        ab[i][g]=0;
                        a[i]--;
                        b[g]--;
                        break;
                    }
                }


                m++;
                if(m==5)
                    break;

            }
            result[number_result][19]++;

        }else if(mid_three){ //20
            //当三条，说明没有葫芦，因为如果有中墩葫芦，说明下蹲肯定是葫芦，则 双葫芦有水数加成。所以不太需要考虑这个问题。
            //所以这里就是简单的取出三条，并且将最低两个单赋给它。

            //取出三条：
            for(int j = 0 ;j<15;j++){
                if(b[j]==3){
                    for(int i=0;i<4;i++){
                        if(ab[i][j]==1){
                            result[number_result][result_arr]=(i+1)*100+j;
                            result_arr++;
                            ab[i][j]=0;
                            a[i]--;
                            b[j]--;
                        }

                    }
                    break;
                }
            }

            //再取出最低两位的。
            int m=0;
            for (int j =0 ;j <15;j++){
                if(b[j]==1){
                    for(int i=0;i<4;i++){
                        if(ab[i][j]==1){
                            result[number_result][result_arr]=(i+1)*100+j;
                            result_arr++;
                            ab[i][j]=0;
                            a[i]--;
                            b[j]--;
                        }
                    }m++;
                }
                if(m==2){
                    break;
                }
            }
            if(m!=2){
                for (int j =0 ;j <15;j++){
                    if(b[j]>=1){
                        for(int i=0;i<4;i++){
                            if(ab[i][j]==1){
                                result[number_result][result_arr]=(i+1)*100+j;
                                result_arr++;
                                ab[i][j]=0;
                                a[i]--;
                                b[j]--;
                            }
                        }
                        break;
                    }



                }
            }

            //加上类型值
            result[number_result][20]++;
        }else if(mid_shuangdui){//21

            //固定取最小的两个对子为双对。我觉得这样比较好就是了，这里还可以再改。
            int g=0;
            int lg=0;
            for(int j = 2;j<15;j++){
                if(b[j]==2){
                    lg++;
                }
            }
            for(int j=0;j<15;j++){
                if(b[j]==2){
                    g++;
                    //同时把它取出来，放进数组里面；
                    for(int i =0;i<4;i++){
                        if(ab[i][j]==1){
                            result[number_result][result_arr]=(i+1)*100+j;
                            result_arr++;
                            ab[i][j]=0;
                            a[i]--;
                        }
                    }
                    b[j]-=2;
                }
                if(g==2){
                    break;
                }
            }

            //把最小的单赋值给最中间的那个
            int numkc=0;
            for(int j =0;j<15 ;j++){
                if(b[j]==1 ){
                    numkc++;
                        for(int i=0;i<4;i++){
                            if(ab[i][j]==1){
                                result[number_result][result_arr]=(i+1)*100+j;
                                result_arr++;
                                ab[i][j]=0;
                                a[i]--;
                            }
                        }
                        b[j]--;
                        break;
                }
            }
            if(numkc==0){
                for(int j =0;j<15;j++){
                    if(b[j]>=1 ){
                        numkc++;
                        for(int i=0;i<4;i++){
                            if(ab[i][j]==1){
                                result[number_result][result_arr]=(i+1)*100+j;
                                result_arr++;
                                ab[i][j]=0;
                                a[i]--;
                            }
                        }
                        b[j]--;
                        break;
                    }
                }
            }

            result[number_result][21]++;
            //如果存在多对的情况，就一定用 大冲前墩，中冲双小。这种情况为多数。
        }else if(mid_duizi){
            //看看是否存在两个对子，如果有的话，那就是 差的在上面，所以这也基本上是一种情况。
            //从后往前一定取出最大的那个。
            for(int j =14;j>0;j--){
                if(b[j]==2){
                    for(int i=0;i<4;i++){
                        if(ab[i][j]==1){
                            result[number_result][result_arr]=(i+1)*100+j;
                            result_arr++;
                            ab[i][j]=0;
                            a[i]--;
                            b[j]--;
                        }

                    }

                    break;
                }
            }

            //再取出从后往前最小的三个，跳过对子；
            int m=0;//计数君，记录取得三个
            for(int j =0;j<15 ;j++){
                if(b[j]==1){
                    for(int i=0;i<4;i++){
                        if(ab[i][j]==1){
                            result[number_result][result_arr]=(i+1)*100+j;
                            result_arr++;
                            ab[i][j]=0;
                            a[i]--;
                        }
                    }
                    b[j]--;
                   m++;
                }
                if(m==3){
                    break;
                }
            }

            //补上类型
            result[number_result][22]++;
        }else if(mid_zapai){
            //取开头第一个，然后全部从最后一个取.
            for(int j =14;j>1;j--){
                if(b[j]==1){
                    for(int i=0;i<4;i++){
                        if(ab[i][j]==1){
                            result[number_result][result_arr]=(i+1)*100+j;
                            result_arr++;
                            ab[i][j]=0;
                            a[i]--;
                        }
                    }
                    b[j]--;
                    break;
                }
            }
            int opo=0;
            for(int j=2;j<15;j++){
                if(b[j]==1){

                    for(int i=0;i<4;i++){
                        if(ab[i][j]==1){
                            result[number_result][result_arr]=(i+1)*100+j;
                            result_arr++;
                            ab[i][j]=0;
                            a[i]--;
                        }
                    }
                    b[j]--;
                    opo++;
                    if(opo==4){
                        break;
                    }
                }

            }
            result[number_result][23]++;

        }
        int houlai=0;
        for(int i =muqian;i<24;i++){
            if(result[number_result][muqian]==2){
                houlai=1;
                break;
            }else {
                if(result[number_result][i]==1 && i!=muqian){
                    houlai=1;
                }
            }
        }
        if(houlai==1){
            daoshui=false;
        }else {
            daoshui=true;
        }
        if(result[number_result][19]==2){
            double k4=result[number_result][4]%100;
            double k5=result[number_result][5]%100;
            if(k4<k5){
                daoshui=true;
            }
        }
        if(result[number_result][18]==2){
            //双同花
            double k4=(int )result[number_result][4]%100;
            double k9 = result[number_result][9]%100;

            for(int lml=4;lml>=0;lml--){
                k9=(int )result[number_result][lml+5]%100;
                k4=(int )result[number_result][lml]%100;
                if(k9>k4){
                    //说明倒水。然后我要逆转乾坤
                    for(int kg=0;kg<5;kg++){
                        double dtdai;
                        dtdai=result[number_result][kg];
                        result[number_result][kg]=result[number_result][kg+5];
                        result[number_result][kg+5]=dtdai;
                    }
                break;
                }else if(k4>k9){
                    break;
                }

            }
        }

    }
    //前墩识别
    private void compare_score(){//前墩识别

        /*
        * 写一段代码作用是  把前墩补充上去 ，然后识别前墩类型。
        * */
        int g=0;//判断是否是单排
        int zhadankk=0;
        if(result[number_result][16]>=1){
            zhadankk=1;
        }
        int muqian=0;
        for(int i=24;i>15;i--){
            if(result[number_result][i]>=1){
                muqian=i;
                break;
            }
        }
        int j,i;
        //我决定先识别前墩类型，再去处理前墩。
        //前墩类型：单牌，对子，三条，同花（当后中都为同花时），顺子（当后中都为顺子时）
        if(result[number_result][18]==2){
            //当后中都是同花。这里有一个疑问，
            for(i=0;i<4;i++){
                if(a[i]==3){
                    g++;
                    result[number_result][18]++;
                }
            }
        }
        if(result[number_result][19]==2){
            //当中后都是顺子。
            int m=0;
            for(j=0;j<15;j++){
                if(b[j]>0){
                    m++;
                }else
                    m=0;
                if(m==3){
                    g++;
                    result[number_result][19]++;
                    break;
                }

            }
        }

        //三条
        for(j=0;j<15;j++){
            if(b[j]==3){
                g++;
                result[number_result][20]++;
                if(zhadankk==1){
                    for(int q=0;q<15;q++){
                        if(b[q]==1){
                            for(int z=0;z<4;z++){
                                if(ab[z][q]==1){
                                    result[number_result][4]=(z+1)*100+q;
                                    ab[z][q]=0;
                                    a[z]--;
                                    b[q]--;
                                    break;
                                }

                            }
                            break;
                        }
                    }
                }
                break;
            }
        }

        //对子
        for(j=14;j>=2;j--){
            if(b[j]==2){
                g++;
                result[number_result][22]++;
                if(zhadankk==1){
                    for(int q=2;q<15;q++){
                        if(b[q]>=1 && q!=j){
                            for(int z=0;z<4;z++){
                                if(ab[z][q]==1){
                                    result[number_result][4]=(z+1)*100+q;
                                    ab[z][q]=0;
                                    a[z]--;
                                    b[q]--;
                                    break;
                                }

                            }
                            break;
                        }
                    }
                }
                break;
            }
        }
        int sks=0;
        //是将多余的放入数组之中。
        for(j =14;j>1 ; j--){
            if(b[j]>0){
                for(i=0;i<4;i++){
                    if(ab[i][j]==1 ){
                    if(sks==3){
                        result[number_result][4]=(i+1)*100+j;
                    }else {
                        result[number_result][result_arr]=(i+1)*100+j;
                        result_arr++;
                    }
                        ab[i][j]=0;
                        a[i]--;
                        b[j]--;
                        sks++;
                    }
                }



            }
        }
        if(g==0){
            result[number_result][23]++;
        }
        if(daoshui==false){
            int houlai=0;
            for( i =muqian;i<24;i++){
                if(result[number_result][muqian]==3 || result[number_result][muqian]==2){
                    houlai=1;
                    break;
                }else {
                    if(result[number_result][i]==1 && i!=muqian){
                        houlai=1;
                    }
                }
            }
            if(houlai==1){
                daoshui=false;
            }else {
                daoshui=true;
            }
        }
        if(zhadankk==1 && result[number_result][4]==0){
            for(j=0;j<15;j++){
                if(b[j]==1){
                    for(i=0;i<4;i++){
                        if(ab[i][j]==1){
                            result[number_result][4]=(i+1)*100+j;
                            ab[i][j]=0;
                            a[i]--;
                            b[j]--;
                            break;
                        }
                    }
                    break;
                }
            }
        }
        get_score(result[number_result]);

    }
    private void get_score(double daiti[]){ //传入进来的是一个数组，0-12是那十三张牌的放的位置，15-23是牌型的累计，26是分数的累计
        //是：《同花顺，炸弹，葫芦，同花，顺子，三条，两对，对子，空》 15-23，累加算法，除非 同花或者顺子等于2 否则前墩没有顺子和同花的说法。
        //是：《  15 ， 16 ，17 ， 18 ， 19， 20 ， 21 ， 22 ，23》
        double gscore=0;

        boolean dunshu[];
        dunshu=new boolean[3];
        for(int i =0;i<3;i++){
            dunshu[i]=false;
        }
        if(daiti[15]>=1){
            //同花顺
           for(int i =0;i<daiti[15];i++){
               gscore+=100;
               score_panduanqianhou(dunshu);
           }
        } if(daiti[16]>=1){
            //炸弹和 同花顺一样。
            for(int i =0;i<daiti[16];i++){
                gscore+=80;
                score_panduanqianhou(dunshu);
            }
        }if(daiti[17]>=1){
            //葫芦
            for(int i = 0 ; i<daiti[17];i++){
                gscore=score_hulu*score_panduanqianhou(dunshu)+gscore;
                    gscore+=(daiti[score_arr]%100)*0.1;

            }
        } if(daiti[18]>=1){
            //同花
            for(int i = 0 ; i<daiti[18];i++){
                gscore=score_tonghua*score_panduanqianhou(dunshu)+gscore;
                gscore+=(daiti[score_arr+4]%100)*0.2+(daiti[score_arr+3]%100)*0.008+(daiti[score_arr+2]%100)*0.004;
            }
        } if (daiti[19]>=1){
            //顺子
            for(int i = 0 ; i<daiti[19];i++) {
                gscore = score_shunzi * score_panduanqianhou(dunshu) + gscore;
                gscore += (daiti[score_arr+4]%100) * 0.3;
                if(i==2){
                    gscore=10000;
                }
            }
        } if (daiti[20]>=1){
            //三条
            for(int i = 0 ; i<daiti[20];i++) {
                gscore = score_santiao * score_panduanqianhou(dunshu) + gscore;
                gscore += (daiti[score_arr]%100) * 0.13;
            }
        } if (daiti[21]>=1){
            //双对
            for(int i = 0 ; i<daiti[21];i++) {
                gscore = score_liangdui * score_panduanqianhou(dunshu) + gscore;
                gscore += (daiti[score_arr]%100) * 0.13+(daiti[score_arr+2]%100)*0.008;

            }
        } if (daiti[22]>=1){
            //对子.
            for(int i = 0 ; i<daiti[22];i++) {
                gscore = score_duizi * score_panduanqianhou(dunshu) + gscore;
                gscore+= (daiti[score_arr+1]%100)*0.13+(daiti[score_arr+2]%100)*0.008;
            }
        }
        if(daiti[23]==1){
            gscore += score_panduanqianhou(dunshu)*1;
            gscore+=0.1*(daiti[5]%100);
        }
        if(dunshu[2]==false){
            //散排，而且基本在上墩。
            gscore += score_panduanqianhou(dunshu)*1;
            gscore+=0.1*(daiti[score_arr+2]%100)+0.005*(daiti[score_arr+1]%100);
        }

        if(daoshui==true){
            gscore=0;
        }
        if(teshu==true){
            gscore=10000;
        }
        daiti[26]=gscore;

        /*
        首先 一定要有单排对于这个局面的影响。设置一下吧 2 - 5 0.05分 6-10 0.2分 10-13 0.3分 14 0.6分
        比如葫芦。则是 222 33 则分数是 20+0.1
        同花  A J 10 5 2 分数是
        同花 A J 9 8 7 这样吧 就只算前三个就好了。最大是5倍，然后第二大百分之10 第三大就 百分之 1
        放大第一个的权重，


        * */
    }
    public double ziwojuezhu(){
        double max=0;
        for(int i = 0 ; i<result.length;i++){
            if(result[i][26]>max){
                max=result[i][26];
                max_j=i;
            }
        }
        return max;
    }
    private double score_panduanqianhou(boolean a[]){
        if(a[0]==true && a[1]==true){
            a[2]=true;
            score_arr=10;
            return shangdun_bili;
        }else if(a[0] == true){
            score_arr=5;
            a[1]=true;
            return zhongdun_bili;
        }else if(a[0]==false){
            score_arr=0;
            a[0]=true;
            return xiadun_bili;
        }
        return 0;
    }
    private void findzuida(){

    }



    private void return_data(){
        ;//返回接口数据
    }
    private  void judge_wudu(int p){
        //先找到同花，然后看看有没有同花顺。
        int m=0;
        int heng=0,end=0;
        boolean cunzai=false;
        int gcg=0;
        for(int i =0;i<4;i++){
            if(a[i]>=5){
                for(int j =14;j>=2;j--){
                    if(ab[i][j]==1){
                        m++;
                    }else {
                        m=0;
                    }
                    if(m==5){
                        gcg++;
                        if(gcg==2){
                            if(end>j+4){
                                break;
                            }
                        }
                        heng=i;
                        end=j+4;
                        cunzai=true;
                        break;
                    }
                }
            }
            m=0;
        }
        //当的确存在
        if(cunzai){
            int g=0;
            for(int j=end;j>=2;j--){
                ab[heng][j]--;
                b[j]--;
                a[heng]--;
                result[number_result][result_arr]=(heng+1)*100+j;
                result_arr++;
                g++;
                if(g==5){
                    break;
                }
            }

        }
        result[number_result][15]++;

    }
    private void judge_zhadan(int p ){
        for(int j=14;j>1;j--){
            if(b[j]==4){
                for(int i =0;i<4;i++){
                    ab[i][j]--;
                    b[j]--;
                    a[i]--;
                    result[number_result][result_arr]=(i+1)*100+j;
                    result_arr++;
                }
                break;
            }
        }
        //这里的话，是因为有炸弹，所以我打算把最后一个数字放在最后拿。所以
        result_arr++;
        result[number_result][16]++;
    }
    private boolean judge_hulu(int p){

        int j=0;
        int m=0;

        //后墩在确定类型之后，是否有多种组合

            for(j = 14 ; j>0;j--){
                if(b[j]==3){
                    for(int k=0;k<15;k++){
                        if(b[k]>=2){
                            m++;
                        }
                    }
//                    if(m>1){
//                        m--;
//                    }
                    break;
                }

            }
        select_hou=m-1;//如果有一个对子，那么就是没有分支。有两个对子，就是有两条路。如这题，则 有三个对子。就是应该有 三条路。

            m=0;
        //取出三条
        for(int g=0;g<4;g++){
            if(ab[g][j]==1){
                ab[g][j]--;
                result[number_result][result_arr]=(g+1)*100+j;
                result_arr++;
                a[g]--;
            }
        }
        b[j]=0;
        //取出b[j];
        m=0;
        //取出对子
        for(int k=0;k<15;k++){
            int t=0;
            if(b[k]>=2){
                m++;
                if(m==p){//p从1开始的话。则说明 p等于多少，就走哪条路。这时候p等于2
                    for(int g=0;g<4;g++){
                        if(ab[g][k]==1){
                            ab[g][k]--;
                            result[number_result][result_arr]=(g+1)*100+k;
                            result_arr++;
                            a[g]--;
                            b[k]--;
                            t++;
                        }
                        if(t==2){
                            break;
                        }
                    }


                }
            }
        }
        //到这里为止，第一次的后墩已经放进去了
        //写入类型
        result[number_result][17]++;
        return true;
    }//判断葫芦在其中的个数
    private void judge_tonghua(int p){
        //基本完成 判断同花基本完成;
        /*
        *  先确定一下 select_hou的数字 是那种无论运行多少次都不会变的。
        * 寻找看看有没有多余的组合。同时改变select_hou
        *
        * */
        int m=0,end=0,heng=0;
        //先判断有没有双同花的情况；
        for(int i =0 ;i<4;i++){
            if(a[i]>=5){

                for(int j = 14; j>0;j--){
                    if(ab[i][j]==1){
                        if(j>=end){
                            m++;
                            if(m==2 &&j==end){
                                for(;j>=2;j--){
                                    if(ab[heng][j]>ab[i][j]){
                                        break;
                                    }else if(ab[i][j]>ab[heng][j]){
                                        heng=i;
                                        break;
                                    }
                                }
                            }else{
                                end=j;
                                heng=i;
                            }

                        }
                        break;
                    }
                }
            }
        }


            int i =heng;
            int g=0;

            int hulue7=-1;
            int hulue8=-1;
            int hulue9=-1;
            int lhc=0;
            if(a[i]>=5 ){
                if( a[i]!=5 &&a[i]>=7 && a[i]<=9){

                    for(int j =14;j>=2;j--){
                        if(ab[i][j]==1){
                            if(b[j]>=2){
                                //则试图忽略掉它
                                if(hulue7==-1){
                                    hulue7=j;
                                }else if (hulue8==-1){
                                    hulue8=j;
                                }else if(hulue9==-1){
                                    hulue9=j;
                                }
                                lhc++;
                            }
                        }
                        if(lhc==a[i]-5){
                            break;
                        }
                    }
                }
                //假设ai=8
                //直接就把所有的行都吃了
                //首先如果等于的话，那么定义一个m
                int  suanshu=0;
                int sum=1;
                suanshu=a[i]-lhc;
                if(suanshu<5){
                    for(int mlm=0;;){
                        if(hulue9!=-1){
                            hulue9=-1;
                        }else if(hulue8!=-1){
                            hulue8=-1;
                        }else if(hulue7!=-1){
                            hulue7=-1;
                        }
                        mlm++;
                        if(mlm+suanshu==5){
                            break;
                        }
                    }
                }
                for(int gc =suanshu;gc>0;gc--){
                    sum=sum*gc;
                }
                if(a[i]!=5)
                select_hou=sum/120;
                if(select_hou>=6){
                    select_hou=6;
                }
                int kmk=0;
                int lol=0;
                for(int j =0 ;j < 15;j++){
                    if(ab[i][j]>0){
                        g++;
                        lol++;
                        if(select_hou==1){
                            g=0;
                        }
                        if(a[i]!=5){
                            if(g!=p && j!=hulue7 && j!=hulue8 && j!=hulue9 ){
                                ab[i][j]--;
                                b[j]--;
                                result[number_result][result_arr]=(i+1)*100+j;
                                result_arr++;
                                kmk++;
                            }
                                if(kmk==5)
                                    break;

                        }else {
                            ab[i][j]--;
                            b[j]--;
                            result[number_result][result_arr]=(i+1)*100+j;
                            result_arr++;
                            if(lol==5)
                                break;
                        }

                    }
                }
                a[i]=a[i]-5;
            }

            result[number_result][18]++;


    }
    private void judge_shunzi(int p){

        //还要考虑出现双顺子倒水的问题。
        //先判断顺子是否有双顺子
        //卧槽 ，双顺子好难。先放到后面吧
        //如果说先不判断双顺子的话，那么就是单顺子末尾。
        //如果说确定只有单顺子的话，那么说明，顺子是最大墩，那么只要考虑 会不会有三条和对子的情况。就是说 影响这个顺子的发布的 只有这两个。
        // 比如说 你有 3-9   4多余 8多余  还有一个对12  A J  也就是说 这个组合可以
        //          是   3-7 88QQ4 AJ9
        //          或者 5-9 44QQ3 AJ8
        //由于多种因素，所以这两种组合是不可以直接否定掉的。我必须得遍历它。
        //算了 就全部遍历吧。
        int k=0,begin=0,number=0;
        for(int j=0;j<15;j++){
            if(b[j]>0){
                k++;
            }else {
                k=0;
            }
            if(k==5){
                begin=j-4;
            }
            if(k>=5){
                number=k;//单顺子的情况下，顺子的长度。
            }
        }

        select_hou=number-5+1;//7-5+1=3;

        //接下来就取出来，从begin开始；
        int l=0;
        for(int j =begin+p-1;j<15;j++){
            //开始取出来；
            for(int i =0;i<4;i++){
                if(ab[i][j]==1){
                    ab[i][j]--;
                    b[j]--;
                    a[i]--;
                    result[number_result][result_arr]=(i+1)*100+j;
                    result_arr++;
                    l++;
                    break;
                }

            }
            if(l==5){
                break;
            }
        }

        //然后给数组尾部赋值；
        result[number_result][19]++;

    }
    private void judge_duizi(){
        //就一个对 那肯定选最大的。基本不会有这个啦。
        int begin= 0;
        for(int j=14;j>0;j--){
            if(b[j]>=2){
               begin=j;
               break;
            }
        }
        int m=0;
        for(int i =0 ;i<4;i++){
            if(ab[i][begin]==1){
                ab[i][begin]--;
                b[begin]--;
                a[i]--;
                result[number_result][result_arr]=(i+1)*100+begin;
                result_arr++;
                m++;
            }
            if(m==2){
                break;
            }
        }
        result[number_result][22]++;

        m=0;
        for(int j=0;j<15;j++){
            if(b[j]==1){
                for(int i = 0 ;i<4;i++){
                    if(ab[i][j]==1) {
                        ab[i][j]--;
                        b[j]--;
                        a[i]--;
                        result[number_result][result_arr] = (i + 1) * 100 + j;
                        result_arr++;
                        m++;
                        break;
                    }
                }
                if(m==3){
                    break;
                }
            }

        }
        if(m<3){
            System.out.println("单对是失败的");
        }
    }
    private void judge_santiao(){
        //有三条了 只要考虑 中墩是否是三条。
        //选最大的三条；
        int begin=0;
        for(int j = 14 ;j > 0 ;j--){
            if(b[j]==3){
                begin=j;
                break;
            }
        }
        //取出三条，单的话 取最小的两个。
        for(int i =0;i<4;i++){
            if(ab[i][begin]==1){
                ab[i][begin]--;
                b[begin]--;
                a[i]--;
                result[number_result][result_arr]=(i+1)*100+begin;
                result_arr++;
            }
        }

        //取最小两个。
        int m=0;
        for(int j=0;j<15;j++){
            if(b[j]==1){
                m++;

                for(int i = 0 ;i<4;i++){
                    if(ab[i][j]==1) {
                        ab[i][j]--;
                        b[j]--;
                        a[i]--;
                        result[number_result][result_arr] = (i + 1) * 100 + j;
                        result_arr++;
                        break;
                    }
                }
            }
            if(m==2){
                break;
            }
        }

        result[number_result][20]++;
    }

    private void judge_shuangdui(int p){
        //如果有四五对的话，那就是 第二带第三。
        //如果只有三对的话，那就是第一带第三。
        int number=0;
        int xiao[];
        xiao=new int[7];
        for(int j=0;j<15;j++){
            if(b[j]>=2){
                number++;
                xiao[number]=j;
            }

        }
        if(number==3){
            //取出第一和第三。
            if(xiao[1]==xiao[2]-1){
                judge_shuangdui_quchu(xiao[2],xiao[1]);
            }else if(xiao[2]==xiao[3]-1){
                judge_shuangdui_quchu(xiao[3],xiao[2]);
            }else{
                judge_shuangdui_quchu(xiao[3],xiao[1]);
            }

        }else if(number==4){
            if(xiao[3]==xiao[4]-1){
                judge_shuangdui_quchu(xiao[4],xiao[3]);
            }else if (xiao[2]==xiao[3]-1){
                judge_shuangdui_quchu(xiao[3],xiao[2]);
            }else if(xiao[1]==xiao[2]-1){
                judge_shuangdui_quchu(xiao[2],xiao[1]);
            }else{
                //取出 第三第四
                judge_shuangdui_quchu(xiao[4],xiao[1]);
            }

        }else if (number==5){
            if(xiao[4]==xiao[5]-1){
                judge_shuangdui_quchu(xiao[5],xiao[4]);
            }else if(xiao[3]==xiao[4]-1){
                judge_shuangdui_quchu(xiao[4],xiao[3]);
            }else if (xiao[2]==xiao[3]-1){
                judge_shuangdui_quchu(xiao[3],xiao[2]);
            }else if(xiao[1]==xiao[2]-1){
                judge_shuangdui_quchu(xiao[2],xiao[1]);
            }else {
                //取出第二和最后
                judge_shuangdui_quchu(xiao[4],xiao[1]);
            }

        }else if(number==2 || number==6){
            judge_shuangdui_quchu(xiao[2],xiao[1]);
        }

        //以及取走单牌
        int m=0;
        for(int j=0;j<15;j++){
            if(b[j]==1){
                for(int i = 0 ;i<4;i++){
                    if(ab[i][j]==1) {
                        ab[i][j]--;
                        b[j]--;
                        a[i]--;
                        result[number_result][result_arr] = (i + 1) * 100 + j;
                        result_arr++;
                        m++;
                        break;
                    }
                }
                if(m==1){
                    break;
                }
            }

        }

    }
    private void judge_shuangdui_quchu(int number1,int number2){
            int m=0;
            //开始取出来；
            for(int i =0;i<4;i++){
                if(ab[i][number1]==1){
                    m++;
                    if(m==3){
                        break;
                    }
                    ab[i][number1]--;
                    b[number1]--;
                    a[i]--;
                    result[number_result][result_arr]=(i+1)*100+number1;
                    result_arr++;
                }

            }
            m=0;
            for(int i =0;i<4;i++){
            if(ab[i][number2]==1){
                m++;
                if(m==3){
                    break;
                }
                ab[i][number2]--;
                b[number2]--;
                a[i]--;
                result[number_result][result_arr]=(i+1)*100+number2;
                result_arr++;
            }

            }
            result[number_result][21]++;

    }
    private void printsss(){
        String ggg="";
        if(fuzetype==0){
            ggg="同花顺";
        }else if(fuzetype==1){
            ggg="炸弹";
        }else if(fuzetype==2){
            ggg="葫芦";
        }else if(fuzetype==3){
            ggg="同花";
        }else if(fuzetype==4){
            ggg="顺子";
        }else if(fuzetype==5){
            ggg="三条";
        }else if(fuzetype==6){
            ggg="双对";
        }else if(fuzetype==7){
            ggg="对子";
        }
        System.out.println(ggg);
        for(int i= 0; i<number_result;i++){
            System.out.print(i+":  ");
            for(int j =0 ; j<result[i].length;j++)
            System.out.print(result[i][j]+" ");
            System.out.println();
        }

        System.out.println("----------------------");

    }
    public void printwai(){

        System.out.println("决出的最后优胜者是：");
        int i=max_j;
            for(int j =0 ; j<result[max_j].length;j++)
                System.out.print(result[max_j][j]+" ");
            System.out.println();

    }

    private void judge_put(){
        ;//判断如何摆放的问题，以及选择哪一个的问题
    }

    private void get_data(){
        ;//从接口外面获得数据
    }

    public void returnkmk(int g[]){

        //初步设定
        //五毒 0  炸弹 1
        //葫芦 2  同花 3  顺子 4  三条 5  多对6 单对 7
        //判断葫芦

        int heng=0,end=0;
        boolean cunzai=false;
        for(int i =0;i<4;i++){
            int m=0;
            if(a[i]>=5){
                for(int j =14;j>=2;j--){
                    if(ab[i][j]==1){
                        m++;
                    }else {
                        m=0;
                    }
                    if(m==5){
                        g[0]=1;
                        break;
                    }
                }
            }
        }

        for(int j =0;j<15;j++){
            if(b[j]==3 ){
                for(int m = 0 ; m<15;m++){
                    if(b[m]>=2 && m!=j){
                        g[2]=1;//葫芦
                    }
                }
                g[5]=1; //三条
            }else if(b[j]==4){
                g[1]=1;//炸弹
            }
        }
        //同花
        for(int i=0;i<4;i++){
            if(a[i]>=5){
                g[3]=1;
            }
        }

        //顺子
        int m=0;
        for(int j=0;j<15;j++){
            if (b[j] > 0) {
                m++;
                if(m==5){
                    g[4]=1;
                }

            }else {
                m=0;
            }
        }




        //两对或者以上 中墩为对子；
        int u=0;
        for(int j=0;j<15;j++){
            if(b[j]==2){
                u++;
            }
        }if(u>=2){
            g[6]=1;
        }
        if(u<=2 && u>0){
            g[7]=1;
        }
    }
}
