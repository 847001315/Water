public class Puke {
    private int id;
    public String[] card={""};
    public Puke(double a[],int id){
        this.id=id;
        card = new String[3];
        card[0]="";
        card[1]="";
        card[2]="";
        for(int i =12;i>=10;i--){
            card[0]+=zhuanxin(a[i]);
            if(i!=10){
                card[0]+=" ";
            }
        }
        for(int i =9;i>=5;i--){
            card[1]+=zhuanxin(a[i]);
            if(i!=5){
                card[1]+=" ";
            }
        }
        for(int i =4;i>=0;i--){
            card[2]+=zhuanxin(a[i]);
            if(i!=0){
                card[2]+=" ";
            }
        }
    }
    private String  zhuanxin(double number){
        //传入一个数字，返回一个字符床
        char a,b='0';
        double k =number%100;
        int hua=(int) number/100;
        if(k<=9){
             b=(char)(k+48);
        }else {
            if(k==11){
                b='J';
            }else if(k==12){
                b='Q';
            }else if(k==13){
                b='K';
            }else if(k==14){
                b='A';
            }
        }
        if(hua==1){
            a='$';
        }else if(hua==2){
            a=38;
        }else if(hua==3){
            a='*';
        }else {
            a='#';
        }
        String fanhui;

        String s = String.valueOf(a);

        if(k==10){
            s=s+"10";
        }else {
            s=s+String.valueOf(b);
        }
        return s;

    }
}
