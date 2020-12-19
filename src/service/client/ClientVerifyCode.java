package service.client;

import java.util.Random;

/**
 * @author lixinyao
 * @create 2020-12-2020/12/19-19:11
 **/
//生成四位随机数
    //范围在0000-9999之间
public class ClientVerifyCode {
    public String getFourRandom(){
        Random random=new Random();
        String fourRandom=random.nextInt(1000)+"";
        int randLength=fourRandom.length();
        if(randLength<4){
            for(int i=1;i<=4-randLength;i++){
                fourRandom="0"+fourRandom;
            }
        }
        return fourRandom;
    }
}
