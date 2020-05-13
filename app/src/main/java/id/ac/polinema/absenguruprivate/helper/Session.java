package id.ac.polinema.absenguruprivate.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {
    private static final String LOGGED_IN_PREF = "login_status";
    private static final String USERNAME_PREF = "username";
    private static final String PASSWORD_PREF = "password";
    private static final String LOGIN_TIME_PREF = "jam_login";
    private static final String LOGOUT_TIME_PREF = "jam_logout";
    private static final String DATE_PREF = "tanggal";

    private SharedPreferences preferences;

    public Session(Context context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public void setLoggedInStatus(boolean value) {
        preferences.edit().putBoolean(LOGGED_IN_PREF, value).apply();
    }

    public boolean getLoggedInstatus() {
        return preferences.getBoolean(LOGGED_IN_PREF, false);
    }

    public void setUsername(String username) {
        preferences.edit().putString(USERNAME_PREF, username).apply();
    }

    public String getUsername() {
        return preferences.getString(USERNAME_PREF, "");
    }

    public void setPassword(String password) {
        preferences.edit().putString(PASSWORD_PREF, password).apply();
    }

    public String getPassword() {
        return preferences.getString(PASSWORD_PREF, "");
    }

    public void setLoginTime(String loginTime) {
        preferences.edit().putString(LOGIN_TIME_PREF, loginTime).apply();
    }

    public String getLoginTime() {
        return preferences.getString(LOGIN_TIME_PREF, "");
    }

    public void setLogoutTime(String logoutTime) {
        preferences.edit().putString(LOGOUT_TIME_PREF, logoutTime).apply();
    }

    public String getLogoutTime() {
        return preferences.getString(LOGOUT_TIME_PREF, "");
    }

    public void setDate(String date) {
        preferences.edit().putString(DATE_PREF, date).apply();
    }

    public String getDate() {
        return preferences.getString(DATE_PREF, "");
    }

    public void logout() {
        preferences.edit().clear().apply();
    }
}
