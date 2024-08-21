/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.recordmq.permisssion;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.recordmq.config.RMQConfigure;
import com.recordmq.util.WebUtil;
import com.recordmq.exception.ServiceException;
import com.recordmq.model.UserInfo;
import com.recordmq.service.PermissionService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class PermissionAspect {

    @Resource
    private RMQConfigure configure;

    @Resource
    private PermissionService permissionService;

    /**
     * @Permission can be applied to the Controller class to implement Permission verification on all methods in the class
     * can also be applied to methods in a class for fine control
     */
    @Pointcut("@annotation(com.recordmq.permisssion.Permission) || @within(com.recordmq.permisssion.Permission)")
    private void permission() {

    }

    @Around("permission()")
    public Object checkPermission(ProceedingJoinPoint joinPoint) throws Throwable {
        if (configure.isLoginRequired()) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String url = request.getRequestURI();
            UserInfo userInfo = (UserInfo) request.getSession().getAttribute(WebUtil.USER_INFO);
            if (userInfo == null || userInfo.getUser() == null) {
                throw new ServiceException(-1, "user not login");
            }
            boolean checkResult = permissionService.checkUrlAvailable(userInfo, url);
            if (!checkResult) {
                throw new ServiceException(-1, "no permission");
            }
        }
        return joinPoint.proceed();
    }
}
