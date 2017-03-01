package com.seentech.spiderInfo.model;

/**
 * Created by seentech on 2017/2/28.
 */
public class WeatherInfo {

    private Integer address_code;
    private String next24hours_detailed;
    private String today_brief;
    private String next1day_brief;
    private String next2day_brief;
    private String next3day_brief;
    private String next4day_brief;
    private String next5day_brief;
    private String next6day_brief;

    public WeatherInfo() {
    }

    public void setAddress_code(Integer address_code) {
        this.address_code = address_code;
    }

    public void setNext24hours_detailed(String next24hours_detailed) {
        this.next24hours_detailed = next24hours_detailed == null ? null : next24hours_detailed.trim();

    }

    public void setToday_brief(String today_brief) {
        this.today_brief = today_brief == null ? null : today_brief.trim();
    }

    public void setNext1day_brief(String next1day_brief) {
        this.next1day_brief = next1day_brief == null ? null : next1day_brief.trim();
    }

    public void setNext2day_brief(String next2day_brief) {
        this.next2day_brief = next2day_brief == null ? null : next2day_brief.trim();
    }

    public void setNext3day_brief(String next3day_brief) {
        this.next3day_brief = next3day_brief == null ? null : next3day_brief.trim();
    }

    public void setNext4day_brief(String next4day_brief) {
        this.next4day_brief = next4day_brief == null ? null : next4day_brief.trim();
    }

    public void setNext5day_brief(String next5day_brief) {
        this.next5day_brief = next5day_brief == null ? null : next5day_brief.trim();
    }

    public void setNext6day_brief(String next6day_brief) {
        this.next6day_brief = next6day_brief == null ? null : next6day_brief.trim();
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "address_code=" + address_code +
                ", next24hours_detailed='" + next24hours_detailed + '\'' +
                ", today_brief='" + today_brief + '\'' +
                ", next1day_brief='" + next1day_brief + '\'' +
                ", next2day_brief='" + next2day_brief + '\'' +
                ", next3day_brief='" + next3day_brief + '\'' +
                ", next4day_brief='" + next4day_brief + '\'' +
                ", next5day_brief='" + next5day_brief + '\'' +
                ", next6day_brief='" + next6day_brief + '\'' +
                '}';
    }
}
