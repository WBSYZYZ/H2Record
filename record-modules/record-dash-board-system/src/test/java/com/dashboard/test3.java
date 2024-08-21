package com.dashboard;

import com.alibaba.fastjson.JSONObject;
import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class test3 {
    private static final Kryo kryo = new Kryo();

    private static byte[] result;
    test3(){
        kryo.register(Unit.class);
        kryo.register(BaseBo.class);
        kryo.register(ArrayList.class);
        kryo.register(Byte.class);
        kryo.register(Serializable.class);
    }

    public static void main(String[] args) throws Exception {
        test3 test3=new test3();
        List<Unit> unitList=new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            Unit unit = new Unit();
            unit.setName("斤"+i);
            unit.setId("99933240653ff9e50165551b44db09a3"+i);
            unit.setEntityId("99933240"+i);
            unit.setSelfEntityId("99933240"+i);
            unit.setSortCode(0);
            unit.setCreateTime(20180820101255131L);
            unit.setOpUserId("999337238b5b3ce2018b656ec470000a"+i);
            unit.setOpUserName("青蓼");
            unit.setIsValid((short) 1);
            unit.setLastVer(1);
            unitList.add(unit);
        }
        int kryoObjectSize = getKryoObjectSize(unitList);
        int hessianObjectSize = getHessianObjectSize(unitList);
        System.out.println("kryoObjectSize:" + bytesToKBFormatted(kryoObjectSize) + " hessianObjectSize:" + bytesToKBFormatted(hessianObjectSize));
    }

    public static int getKryoObjectSize(List<Unit> unitList) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Output output = new Output(bos);
        kryo.writeClassAndObject(output, unitList);
        output.flush(); // 确保所有数据都被写出
        bos.close();
        result=bos.toByteArray();
        ByteArrayInputStream bis = new ByteArrayInputStream(result);
        Input input = new Input(bis);
        Object o = kryo.readClassAndObject(input);
        bis.close();
        List<Unit> t = (List<Unit>) o;
        System.out.println(JSONObject.toJSONString(t));
        return output.toBytes().length;
    }
    public static int getHessianObjectSize(Object obj) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        Hessian2Output ho = new Hessian2Output(bos);
        ho.writeObject(obj);
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        Hessian2Input hi = new Hessian2Input(bis);
        List<Unit> t = (List<Unit>) hi.readObject();
        System.out.println(JSONObject.toJSONString(t));
        return bos.toByteArray().length;
    }

    public static String bytesToKBFormatted(long bytes) {
        return String.format("%.2f KB", bytes / 1024.0);
    }
}
