package util;

import java.util.ArrayList;

/**
 * @author lixinyao
 * @create 2020-12-2020/12/14-14:47
 **/
public class JsonTools {
    //拼接JSON格式时需要添加的内容
    public static String quoteSymbol = "\"";
    public static String squareBracketLeftSymbol = "[";
    public static String squareBracketRightSymbol = "]";
    public static String braceLeftSymbol = "{";
    public static String braceRightSymbol = "}";
    public static String comma = ",";
    public static String colon = ":";


    //将JSON格式的数据转换为ArrayList数组
    public static ArrayList<String> jsonToArray(String postData) {
        ArrayList<String> data = new ArrayList<String>();
        String temp;
        for (int i = 0; i < postData.length(); i++) {
            if (postData.charAt(i) == '"') {
                for (int j = i + 1; j < postData.length(); j++) {
                    if (postData.charAt(j) == '"') {
                        temp = postData.substring(i + 1, j);
                        data.add(temp);
                        i = j;
                        j = postData.length();
                    }
                }

            }
        }
        return data;
    }
    //将sql查询的结果ArrayList的数组转换为JSON格式的String，在于拼接数据
    public static String toJson(ArrayList<String> sqlRunResult,int keyLength){//key表示关键字的个数
        String result = new String();
        int keys = keyLength;

        if (sqlRunResult != null) {
            int dataCount = sqlRunResult.size() / (keys*2); // 返回数据条数总计

            int start = 0;//(currentPage - 1)*pageSize*keys.length; //分页时使用的参数
            result = result + squareBracketLeftSymbol;
            for(int i = 0; i < dataCount; i ++) { //行
                if(i != 0) {
                    result = result + comma;
                }
                result = result + braceLeftSymbol;
                for(int j = 0; j < keys*2;) { //列
                    if(j != 0) {
                        result = result + comma;
                    }
                    String temp = new String();
                    temp = quoteSymbol
                            + sqlRunResult.get((i*keys*2) + j + start)
                            + quoteSymbol
                            + colon
                            + quoteSymbol
                            + sqlRunResult.get((i*keys*2) + j + start + 1)
                            + quoteSymbol;
                    result = result + temp;
                    j = j + 2;
                }
                result = result + braceRightSymbol;
            }
            result = result + squareBracketRightSymbol;
        }
        return result;

    }
}
