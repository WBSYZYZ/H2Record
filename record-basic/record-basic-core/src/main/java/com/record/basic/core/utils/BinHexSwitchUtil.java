package com.record.basic.core.utils;

import java.util.Arrays;

public class BinHexSwitchUtil {
    public static String bytesToHexString(byte[] bytes){
        if (bytes==null || bytes.length==0){
            return null;
        }
        String result="";
        for (byte b:bytes){
            int temp=b & 0xFF;
            String tempStr=Integer.toHexString(temp);
//            System.out.println("tempStr:"+tempStr);
            if (tempStr.length()==1){//如果转换完了是 一位数 需要前面加 0
                result+="0"+tempStr;
            }else {
                result+=tempStr;
            }
        }
        return result;
    }
    public static byte[] hexStringTobytes(String hexString){
        if (hexString==null || hexString.length()==0){
            return null;
        }
        byte[] result=new byte[hexString.length()/2];
        for (int i=0,foot=0;i<hexString.length();i+=2,foot++){
            String temp=hexString.substring(i,i+2);//切割 16进制 二位为一个单位转换
            result[foot]=(byte) Integer.parseInt(temp,16);
        }
        return result;
    }

    public static void main(String[] args) {
        String value="a2\tcdefghijklmnxyz";
        System.out.println("value 2进制:"+Arrays.toString(value.getBytes()));
        String hexStr=bytesToHexString(value.getBytes());
        System.out.println("value 16进制："+hexStr);

        System.out.println("value转回二进制："+Arrays.toString(hexStringTobytes(hexStr)));
        System.out.println("value转回二进制："+new String(hexStringTobytes(hexStr)));
        System.out.println((byte)Integer.parseInt("-129"));

    }
}