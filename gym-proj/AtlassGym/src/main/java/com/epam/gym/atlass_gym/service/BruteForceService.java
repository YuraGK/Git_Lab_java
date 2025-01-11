package com.epam.gym.atlass_gym.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class BruteForceService {

    private static final int MAX_FAILED_ATTEMPTS = 3;
    private static final long LOCK_TIME_DURATION = TimeUnit.MINUTES.toMillis(15);
    private final ConcurrentHashMap<String, FailedLoginAttempt> failedLoginAttempts = new ConcurrentHashMap<>();

    public boolean isBlocked(String username) {
        FailedLoginAttempt attempt = failedLoginAttempts.get(username);
        if (attempt == null) {
            return false;
        }
        if (System.currentTimeMillis() - attempt.getLastFailedAttemptTime() > LOCK_TIME_DURATION) {
            failedLoginAttempts.remove(username);
            return false;
        }
        return attempt.getFailedAttempts() >= MAX_FAILED_ATTEMPTS;
    }

    public void recordFailedLogin(String username) {
        FailedLoginAttempt attempt = failedLoginAttempts.getOrDefault(username, new FailedLoginAttempt(username));
        attempt.incrementFailedAttempts();
        failedLoginAttempts.put(username, attempt);
    }

    public void resetFailedLoginAttempts(String username) {
        failedLoginAttempts.remove(username);
    }

    private static class FailedLoginAttempt {
        private final String username;
        private int failedAttempts;
        private long lastFailedAttemptTime;

        public FailedLoginAttempt(String username) {
            this.username = username;
            this.failedAttempts = 0;
            this.lastFailedAttemptTime = System.currentTimeMillis();
        }

        public String getUsername() {
            return username;
        }

        public int getFailedAttempts() {
            return failedAttempts;
        }

        public long getLastFailedAttemptTime() {
            return lastFailedAttemptTime;
        }

        public void incrementFailedAttempts() {
            failedAttempts++;
            lastFailedAttemptTime = System.currentTimeMillis();
        }
    }
}
