package com.lfalero.hellojava.servicemesh.dao;

import javax.servlet.http.HttpServletRequest;

public interface RequestService {
    String getClientIp(HttpServletRequest request);
}