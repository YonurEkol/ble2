package com.example.aman.hospitalappointy.activity;

/**
 * Created by Administrator on 2017/11/25.
 */

public interface Oprate {
    public final static String PWD_COMFIRM = "1、PWD_COMFIRM";
    public final static String PERSONINFO_SYNC = "2、PERSONINFO_SYNC";
    public final static String SETTING_FIRST = "<--SETTING_FIRST2";
    public final static String PWD_MODIFY = "PWD_MODIFY";
    public final static String TEMPTURE_DETECT_START = "TEMPTURE_DETECT_START";
    public final static String TEMPTURE_DETECT_STOP = "TEMPTURE_DETECT_STOP";
    public final static String HEART_DETECT_START = "HEART_DETECT_START";
    public final static String HEART_DETECT_STOP = "HEART_DETECT_STOP";
    public final static String BP_DETECT_START = "BP_DETECT_START";
    public final static String BP_DETECT_STOP = "BP_DETECT_STOP";
    public final static String BP_DETECTMODEL_SETTING = "BP_DETECTMODEL_SETTING";
    public final static String BP_DETECTMODEL_SETTING_ADJUSTE = "BP_DETECTMODEL_SETTING_ADJUSTE";
    public final static String BP_DETECTMODEL_SETTING_ADJUSTE_CANCEL = "BP_DETECTMODEL_SETTING_ADJUSTE_CANCEL";
    public final static String BP_DETECTMODEL_READ = "BP_DETECTMODEL_READ";
    public final static String SPORT_CURRENT_READ = "SPORT_CURRENT_READ";
    public final static String CAMERA_START = "CAMERA_START";
    public final static String CAMERA_STOP = "CAMERA_STOP";
    public final static String ALARM_SETTING = "ALARM_SETTING";
    public final static String ALARM_READ = "ALARM_READ";
    public final static String ALARM_NEW_READ = "ALARM_NEW_READ";
    public final static String ALARM_NEW_ADD = "ALARM_NEW_ADD";
    public final static String ALARM_NEW_MODIFY = "ALARM_NEW_MODIFY";
    public final static String ALARM_NEW_DELETE = "ALARM_NEW_DELETE";
    public final static String LONGSEAT_SETTING_OPEN = "LONGSEAT_SETTING_OPEN";
    public final static String LONGSEAT_SETTING_CLOSE = "LONGSEAT_SETTING_CLOSE";
    public final static String LONGSEAT_READ = "LONGSEAT_READ";
    public final static String LANGUAGE_CHINESE = "LANGUAGE_CHINESE";
    public final static String LANGUAGE_ENGLISH = "LANGUAGE_ENGLISH";
    public final static String BATTERY = "BATTERY";
    public final static String NIGHT_TURN_WRIST_OPEN = "NIGHT_TURN_WRIST_OPEN";
    public final static String NIGHT_TURN_WRIST_CLOSE = "NIGHT_TURN_WRIST_CLOSE";
    public final static String NIGHT_TURN_WRIST_READ = "NIGHT_TURN_WRIST_READ";
    public final static String NIGHT_TURN_WRIST_CUSTOM_TIME = "NIGHT_TURN_WRIST_CUSTOM_TIME";
    public final static String NIGHT_TURN_WRIST_CUSTOM_TIME_LEVEL = "NIGHT_TURN_WRIST_CUSTOM_TIME_LEVEL";
    public final static String FINDPHONE = "FINDPHONE";
    public final static String CHECK_WEAR_SETING_OPEN = "CHECK_WEAR_SETING_OPEN";
    public final static String CHECK_WEAR_SETING_CLOSE = "CHECK_WEAR_SETING_CLOSE";
    public final static String FINDDEVICE_SETTING_OPEN = "FINDDEVICE_SETTING_OPEN";
    public final static String FINDDEVICE_SETTING_CLOSE = "FINDDEVICE_SETTING_CLOSE";
    public final static String FINDDEVICE_READ = "FINDDEVICE_READ";
    public final static String DEVICE_COUSTOM_READ = "DEVICE_COUSTOM_READ";
    public final static String DEVICE_COUSTOM_SETTING = "DEVICE_COUSTOM_SETTING";
    public final static String SOCIAL_MSG_SETTING = "SOCIAL_MSG_SETTING";
    public final static String SOCIAL_MSG_SETTING2 = "SOCIAL_MSG_SETTING2";
    public final static String SOCIAL_MSG_READ = "SOCIAL_MSG_READ";
    public final static String SOCIAL_MSG_SEND = "SOCIAL_MSG_SEND";
    public final static String SOCIAL_PHONE_IDLE_OR_OFFHOOK = "SOCIAL_PHONE_IDLE_OR_OFFHOOK";
    public final static String DEVICE_CONTROL_PHONE = "DEVICE_CONTROL_PHONE";
    public final static String HEARTWRING_READ = "HEARTWRING_READ";
    public final static String HEARTWRING_OPEN = "HEARTWRING_OPEN";
    public final static String HEARTWRING_CLOSE = "HEARTWRING_CLOSE";
    public final static String SPO2H_OPEN = "SPO2H_OPEN";
    public final static String SPO2H_CLOSE = "SPO2H_CLOSE";
    public final static String SPO2H_AUTO_DETECT_READ = "SPO2H_AUTO_DETECT_READ";
    public final static String SPO2H_AUTO_DETECT_OPEN = "SPO2H_AUTO_DETECT_OPEN";
    public final static String SPO2H_AUTO_DETECT_CLOSE = "SPO2H_AUTO_DETECT_CLOSE";
    public final static String FATIGUE_OPEN = "FATIGUE_OPEN";
    public final static String FATIGUE_CLOSE = "FATIGUE_CLOSE";
    public final static String WOMEN_SETTING = "WOMEN_SETTING";
    public final static String WOMEN_READ = "WOMEN_READ";
    public final static String COUNT_DOWN_WATCH_CLOSE_UI = "COUNT_DOWN_WATCH_CLOSE_UI";
    public final static String COUNT_DOWN_WATCH_OPEN_UI = "COUNT_DOWN_WATCH_OPEN_UI";
    public final static String COUNT_DOWN_APP = "COUNT_DOWN_APP";
    public final static String COUNT_DOWN_APP_READ = "COUNT_DOWN_APP_READ";
    public final static String GPS_KAABA = "GPS_KAABA";
    public final static String GPS_REPORT_START = "GPS_REPORT_START";
    public final static String READ_CHANTING = "READ_CHANTING";
    public final static String SCREEN_LIGHT_SETTING = "SCREEN_LIGHT_SETTING";
    public final static String SCREEN_LIGHT_READ = "SCREEN_LIGHT_READ";
    public final static String SCREEN_STYLE_READ = "SCREEN_STYLE_READ";
    public final static String SCREEN_STYLE_SETTING = "SCREEN_STYLE_SETTING";
    public final static String AIM_SPROT_CALC = "AIM_SPROT_CALC";
    public final static String INSTITUTION_TRANSLATION = "INSTITUTION_TRANSLATION";
    public final static String READ_TEMPTURE_DATA = "READ_TEMPTURE_DATA";
    public final static String READ_HEALTH_DRINK = "READ_HEALTH_DRINK";
    public final static String READ_HEALTH_SLEEP = "READ_HEALTH_SLEEP";
    public final static String READ_HEALTH_SLEEP_FROM = "READ_HEALTH_SLEEP_FROM";
    public final static String READ_HEALTH_SLEEP_SINGLEDAY = "READ_HEALTH_SLEEP_SINGLEDAY";
    public final static String READ_HEALTH_ORIGINAL = "READ_HEALTH_ORIGINAL";
    public final static String READ_HEALTH_ORIGINAL_FROM = "READ_HEALTH_ORIGINAL_FROM";
    public final static String READ_HEALTH_ORIGINAL_SINGLEDAY = "READ_HEALTH_ORIGINAL_SINGLEDAY";
    public final static String READ_HEALTH = "READ_HEALTH";
    public final static String OAD = "OAD";
    public final static String SHOW_SP = "SHOW_SP";
    public final static String SPORT_MODE_ORIGIN_READ = "SPORT_MODE_ORIGIN_READ";
    public final static String SPORT_MODE_ORIGIN_READSTAUTS = "SPORT_MODE_ORIGIN_READSTAUTS";
    public final static String SPORT_MODE_ORIGIN_START = "SPORT_MODE_ORIGIN_START";
    public final static String SPORT_MODE_START_INDOOR = "SPORT_MODE_START_INDOOR";
    public final static String SPORT_MODE_ORIGIN_END = "SPORT_MODE_ORIGIN_END";
    public final static String SPO2H_ORIGIN_READ = "SPO2H_ORIGIN_READ";
    public final static String HRV_ORIGIN_READ = "HRV_ORIGIN_READ";
    public final static String CLEAR_DEVICE_DATA = "CLEAR_DEVICE_DATA";
    public final static String DISCONNECT = "DISCONNECT";
    public final static String DETECT_PTT = "DETECT_PTT";
    public final static String DETECT_START_ECG = "DETECT_START_ECG";
    public final static String DETECT_STOP_ECG = "DETECT_STOP_ECG";
    public final static String LOW_POWER_READ = "LOW_POWER_READ";
    public final static String LOW_POWER_OPEN = "LOW_POWER_OPEN";
    public final static String LOW_POWER_CLOSE = "LOW_POWER_CLOSE";
    public final static String S22_READ_DATA = "S22_READ_DATA";
    public final static String S22_READ_STATE = "S22_READ_STATE";
    public final static String S22_SETTING_STATE_OPEN = "S22_SETTING_STATE_OPEN";
    public final static String S22_SETTING_STATE_CLOSE = "S22_SETTING_STATE_CLOSE";
    public final static String BP_FUNCTION_READ = "BP_FUNCTION_READ";
    public final static String BP_FUNCTION_SETTING = "BP_FUNCTION_SETTING";
    public final static String WEATHER_READ_STATUEINFO = "WEATHER_READ_STATUEINFO";
    public final static String SET_WATCH_TIME = "SET_WATCH_TIME";
    public final static String WEATHER_SETTING_STATUEINFO = "WEATHER_SETTING_STATUEINFO";
    public final static String WEATHER_SETTING_DATA = "WEATHER_SETTING_DATA";

    public final static String LIANSUO_SOS = "LIANSUO_SOS";
    public final static String LIANSUO_SEND_ORDER = "LIANSUO_SEND_ORDER";
    public final static String LIANSUO_SEND_CONTENT = "LIANSUO_SEND_CONTENT";
    public final static String UI_UPDATE_AGPS = "UI_UPDATE_AGPS";
    public final static String UI_UPDATE_CUSTOM = "UI_UPDATE_CUSTOM";
    public final static String UI_UPDATE_SERVER = "UI_UPDATE_SERVER";
    public final static String SYNC_MUSIC_INFO = "SYNC_MUSIC_INFO";
    public final static String UI_UPDATE_G15IMG = "UI_UPDATE_G15IMG";
    public final static String TEXT_ALARM_ADD = "TEXT_ALARM_ADD";
    public final static String TEXT_ALARM_MODIFY = "TEXT_ALARM_MODIFY";
    public final static String TEXT_ALARM_READ = "TEXT_ALARM_READ";
    public final static String TEXT_ALARM_DELETE = "TEXT_ALARM_DELETE";
    public final static String TEXT_ALARM = "TEXT_ALARM";
    public final static String NONE = "NONE";
    public final static String[] oprateStr = new String[]{
            PWD_COMFIRM, PERSONINFO_SYNC, SETTING_FIRST, PWD_MODIFY,
            GPS_KAABA, GPS_REPORT_START, READ_CHANTING, HEART_DETECT_START, HEART_DETECT_STOP, TEMPTURE_DETECT_START, TEMPTURE_DETECT_STOP, READ_TEMPTURE_DATA, BP_DETECT_START, BP_DETECT_STOP, BP_DETECTMODEL_SETTING, BP_DETECTMODEL_READ,
            BP_DETECTMODEL_SETTING_ADJUSTE_CANCEL, BP_DETECTMODEL_SETTING_ADJUSTE,
            SPORT_CURRENT_READ, CAMERA_START, CAMERA_STOP, ALARM_SETTING, ALARM_READ, ALARM_NEW_READ, ALARM_NEW_ADD, ALARM_NEW_MODIFY, ALARM_NEW_DELETE,
            LONGSEAT_SETTING_OPEN, LONGSEAT_SETTING_CLOSE, LONGSEAT_READ, LANGUAGE_CHINESE, LANGUAGE_ENGLISH,
            BATTERY, NIGHT_TURN_WRIST_OPEN, NIGHT_TURN_WRIST_CLOSE, NIGHT_TURN_WRIST_READ, NIGHT_TURN_WRIST_CUSTOM_TIME, NIGHT_TURN_WRIST_CUSTOM_TIME_LEVEL,
            DEVICE_COUSTOM_READ, DEVICE_COUSTOM_SETTING, FINDPHONE,
            CHECK_WEAR_SETING_OPEN, CHECK_WEAR_SETING_CLOSE,
            FINDDEVICE_SETTING_OPEN, FINDDEVICE_SETTING_CLOSE, FINDDEVICE_READ,
            SOCIAL_MSG_SETTING, SOCIAL_MSG_SETTING2, SOCIAL_MSG_READ, SOCIAL_MSG_SEND, DEVICE_CONTROL_PHONE, SOCIAL_PHONE_IDLE_OR_OFFHOOK, HEARTWRING_READ, HEARTWRING_OPEN, HEARTWRING_CLOSE,
            SPO2H_OPEN, SPO2H_CLOSE, SPO2H_AUTO_DETECT_READ, SPO2H_AUTO_DETECT_OPEN, SPO2H_AUTO_DETECT_CLOSE, FATIGUE_OPEN, FATIGUE_CLOSE, WOMEN_SETTING, WOMEN_READ, COUNT_DOWN_WATCH_CLOSE_UI, COUNT_DOWN_WATCH_OPEN_UI, COUNT_DOWN_APP_READ, SCREEN_LIGHT_SETTING, SCREEN_LIGHT_READ, SCREEN_STYLE_READ, SCREEN_STYLE_SETTING, AIM_SPROT_CALC, INSTITUTION_TRANSLATION,
            READ_HEALTH_SLEEP, READ_HEALTH_SLEEP_FROM, READ_HEALTH_SLEEP_SINGLEDAY, READ_HEALTH_DRINK, READ_HEALTH_ORIGINAL,
            READ_HEALTH_ORIGINAL_FROM, READ_HEALTH_ORIGINAL_SINGLEDAY, READ_HEALTH, SET_WATCH_TIME,
            OAD, SHOW_SP, SPORT_MODE_ORIGIN_READ, SPORT_MODE_ORIGIN_READSTAUTS, SPORT_MODE_START_INDOOR, SPORT_MODE_ORIGIN_START, SPORT_MODE_ORIGIN_END, SPO2H_ORIGIN_READ, HRV_ORIGIN_READ, CLEAR_DEVICE_DATA, DISCONNECT
            , DETECT_START_ECG, DETECT_STOP_ECG, NONE, LOW_POWER_READ, LOW_POWER_OPEN, LOW_POWER_CLOSE, S22_READ_DATA, S22_READ_STATE, S22_SETTING_STATE_OPEN, S22_SETTING_STATE_CLOSE, DETECT_PTT, BP_FUNCTION_READ, BP_FUNCTION_SETTING
            , WEATHER_READ_STATUEINFO, WEATHER_SETTING_STATUEINFO, WEATHER_SETTING_DATA, LIANSUO_SOS, LIANSUO_SEND_ORDER, LIANSUO_SEND_CONTENT, UI_UPDATE_AGPS, UI_UPDATE_CUSTOM, UI_UPDATE_SERVER
            , UI_UPDATE_G15IMG, SYNC_MUSIC_INFO,/*TEXT_ALARM_READ,TEXT_ALARM_ADD,TEXT_ALARM_MODIFY,TEXT_ALARM_DELETE,*/TEXT_ALARM
    };
}