package com.example.qrdoc.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AttemptTracker {

    private static final Map<String, AttemptInfo> attempts = new HashMap<>();

    public static boolean isBlocked(String id) {
        AttemptInfo info = attempts.get(id);
        if (info == null) return false;

        if (info.getAttempts() >= 3) {
            if (info.getBlockedUntil() == null) {
                info.setBlockedUntil(LocalDateTime.now().plusMinutes(5));
            }
            return LocalDateTime.now().isBefore(info.getBlockedUntil());
        }
        return false;
    }

    public static void recordAttempt(String id, boolean success) {
        AttemptInfo info = attempts.getOrDefault(id, new AttemptInfo());
        if (success) {
            attempts.remove(id);
        } else {
            info.setAttempts(info.getAttempts() + 1);
            attempts.put(id, info);
        }
    }

    private static class AttemptInfo {
        private int attempts = 0;
        private LocalDateTime blockedUntil;

        public int getAttempts() { return attempts; }
        public void setAttempts(int attempts) { this.attempts = attempts; }

        public LocalDateTime getBlockedUntil() { return blockedUntil; }
        public void setBlockedUntil(LocalDateTime blockedUntil) { this.blockedUntil = blockedUntil; }
    }
}
