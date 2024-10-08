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
package com.recordmq.service.impl;

import com.alibaba.fastjson.JSONObject;
import java.io.FileReader;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.Resource;

import com.recordmq.util.MatcherUtil;
import com.recordmq.config.RMQConfigure;
import com.recordmq.exception.ServiceException;
import com.recordmq.model.UserInfo;
import com.recordmq.service.PermissionService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import static com.recordmq.permisssion.UserRoleEnum.ADMIN;
import static com.recordmq.permisssion.UserRoleEnum.ORDINARY;

@Service
public class PermissionServiceImpl implements PermissionService, InitializingBean {

    @Resource
    private RMQConfigure configure;

    private PermissionFileStore permissionFileStore;

    @Override
    public void afterPropertiesSet() {
        if (configure.isLoginRequired()) {
            permissionFileStore = new PermissionFileStore(configure);
        }
    }

    @Override
    public boolean checkUrlAvailable(UserInfo userInfo, String url) {
        int type = userInfo.getUser().getType();
        // if it is admin, it could access all resources
        if (type == ADMIN.getRoleType()) {
            return true;
        }
        String loginUserRole = ORDINARY.getRoleName();
        Map<String, List<String>> rolePerms = PermissionFileStore.rolePerms;
        List<String> perms = rolePerms.get(loginUserRole);
        for (String perm : perms) {
            if (MatcherUtil.match(perm, url)) {
                return true;
            }
        }
        return false;
    }

    public static class PermissionFileStore extends AbstractFileStore {
        private static final String FILE_NAME = "role-permission.yml";

        private static Map<String/**role**/, List<String>/**accessUrls**/> rolePerms = new ConcurrentHashMap<>();

        public PermissionFileStore(RMQConfigure configure) {
            super(configure, FILE_NAME);
        }

        @Override
        public void load(InputStream inputStream) {
            Yaml yaml = new Yaml();
            JSONObject rolePermsData = null;
            try {
                if (inputStream == null) {
                    rolePermsData = yaml.loadAs(new FileReader(filePath), JSONObject.class);
                } else {
                    rolePermsData = yaml.loadAs(inputStream, JSONObject.class);
                }
            } catch (Exception e) {
                log.error("load user-permission.yml failed", e);
                throw new ServiceException(0, String.format("Failed to load rolePermission property file: %s", filePath));
            }
            rolePerms.clear();
            rolePerms.putAll(rolePermsData.getObject("rolePerms", Map.class));
        }
    }
}
