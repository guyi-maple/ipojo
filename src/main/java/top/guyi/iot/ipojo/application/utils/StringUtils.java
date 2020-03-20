package top.guyi.iot.ipojo.application.utils;

import java.util.Random;

/**
 * @author guyi
 * 字符串工具
 */
public class StringUtils {

    private static final String UNDERLINE = "_";
    private static final String COLON = ":";

    public static boolean isEmpty(String value){
        return value == null || "".equals(value);
    }

    /**
     * 产生随机数字符串
     * @param count 长度
     * @return
     */
    public static String randomIntString(int count){
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < count; i++) {
            sb.append(random.nextInt(9));
        }
        return sb.toString();
    }

    /**
     * 下划线转驼峰命名
     * @param para
     * @return
     */
    public static String underlineToHump(String para){
        StringBuilder result=new StringBuilder();
        String[] a = para.split(UNDERLINE);
        for(String s:a){
            if(result.length()==0){
                result.append(s.toLowerCase());
            }else{
                result.append(s.substring(0, 1).toUpperCase());
                result.append(s.substring(1).toLowerCase());
            }
        }
        return result.toString();
    }

    /**
     * 驼峰命名转下划线
     * @param para
     * @return
     */
    public static String humpToUnderline(String para){
        if(para.contains(UNDERLINE)){
            return para;
        }
        char[] chs = para.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chs.length; i++) {
            if(Character.isUpperCase(chs[i])){
                if(i != 0){
                    sb.append("_");
                }
                chs[i] = Character.toLowerCase(chs[i]);
            }
            sb.append(chs[i]);
        }
        return sb.toString();
    }

    /**
     * 去除冒号
     * @param text
     * @return
     */
    public static String removeColon(String text){
        return text.replaceAll("\\:","");
    }


    /**
     * Mac地址添加冒号
     * @param mac
     * @return
     */
    public static String addColon(String mac){
        if(mac.contains(COLON)){
            return mac;
        }
        int count = mac.length() / 2;
        char[] arr = mac.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= count; i++) {
            sb.append(arr[i * 2 - 2]);
            sb.append(arr[i * 2 - 1]);
            if(count != i){
                sb.append(COLON);
            }
        }
        return sb.toString();
    }

}
