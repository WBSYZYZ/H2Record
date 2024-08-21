package com;

import com.recordmq.domain.CheckroomMessageDTO;
import com.recordmq.util.HessianUtil;

import java.io.IOException;
import java.util.Arrays;

public class test3 {
    public static void main(String[] args) throws IOException {

        CheckroomMessageDTO checkroomMessageDTO=new CheckroomMessageDTO();
        checkroomMessageDTO.setCode("123");
        checkroomMessageDTO.setCheckinTime(1231231);
        checkroomMessageDTO.setDetailList(null);
        checkroomMessageDTO.setOpType(1);
        checkroomMessageDTO.setOpUserId("123");
        checkroomMessageDTO.setEntityId("3123213");
        checkroomMessageDTO.setHistoryId("123123123");
        byte[] serialize = HessianUtil.serialize(checkroomMessageDTO);
        String aa=Arrays.toString(serialize);
        System.out.println( HessianUtil.deserialize("TXQAJ2NvbS5yZWNvcmRtcS5kb21haW4uQ2hlY2tyb29tTWVzc2FnZURUT1MACGVudGl0eUlkUwAHMzEyMzIxM1MABGNvZGVTAAMxMjNTAAloaXN0b3J5SWRTAAkxMjMxMjMxMjNTAAZvcFR5cGVJAAAAAVMACG9wVXNlcklkUwADMTIzUwALY2hlY2tpblRpbWVMAAAAAAASyX9TAApkZXRhaWxMaXN0Tno=".getBytes()));

    }
}
