package com.lhever.sc.devops.logviewer.dto;

import java.util.*;

public class LoginCache {

    private Map<String, LoginCountDto> map = new HashMap<>();


    public synchronized int getLoginCount(String key) {
        LoginCountDto loginCountDto = map.get(key);
        long now = System.currentTimeMillis();
        if (loginCountDto == null) {
            loginCountDto = new LoginCountDto(0, now);
            map.put(key, loginCountDto);
            return 0;
        }

        if (isExpire(now, loginCountDto)) {
            loginCountDto = new LoginCountDto(0, now);
            map.put(key, loginCountDto);
            return 0;
        } else {
            return loginCountDto.getCount();
        }
    }

    public synchronized int incLoginCount(String key, int loginCount) {
        loginCount++;
        LoginCountDto loginCountDto = map.get(key);
        loginCountDto.setCount(loginCount);
        return loginCount;
    }

    public synchronized void clear(String key) {
        map.remove(key);
    }


    public boolean isExpire(LoginCountDto loginCountDto) {
        return isExpire(System.currentTimeMillis(), loginCountDto);
    }

    public boolean isExpire(long now, LoginCountDto loginCountDto) {
        boolean expire = (now - loginCountDto.getCreateTime()) > 15 * 60 * 1000;
        return expire;
    }

    public synchronized void removeExpire() {
        long now = System.currentTimeMillis();
        List<String> keys = new ArrayList<>();

        Set<Map.Entry<String, LoginCountDto>> entries = map.entrySet();
        for (Map.Entry<String, LoginCountDto> entry : entries) {
            String key = entry.getKey();
            LoginCountDto value = entry.getValue();
            if (value == null) {
                continue;
            }
            boolean expire = isExpire(now, value);
            if (expire) {
                keys.add(key);
            }
        }
        keys.stream().forEach(key  -> map.remove(key));
    }















}
