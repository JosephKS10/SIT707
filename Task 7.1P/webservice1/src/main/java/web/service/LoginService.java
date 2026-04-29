package web.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LoginService {


    private static final Map<String, String[]> USER_DB = new HashMap<>();

    static {
        USER_DB.put("ahsan",   new String[]{"ahsan_pass",   "1990-05-15"});
        USER_DB.put("joseph", new String[]{"joseph_pass", "1998-08-20"});
        USER_DB.put("alice",   new String[]{"alice123",     "2000-01-01"});
    }

    public static boolean login(String username, String password, String dob) {
        
        if (!isPresent(username) || !isPresent(password) || !isPresent(dob)) {
            return false;
        }
       
        if (!isValidDobFormat(dob)) {
            return false;
        }
       
        String[] record = USER_DB.get(username);
        if (record == null) {
            return false;
        }
       
        return record[0].equals(password) && record[1].equals(dob);
    }

    static boolean isPresent(String s) {
        return s != null && !s.trim().isEmpty();
    }

    static boolean isValidDobFormat(String dob) {
        if (dob == null || !dob.matches("\\d{4}-\\d{2}-\\d{2}")) {
            return false;
        }
        try {
            LocalDate.parse(dob);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static Map<String, String[]> getRegisteredUsers() {
        return Collections.unmodifiableMap(USER_DB);
    }
}