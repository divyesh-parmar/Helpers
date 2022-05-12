package com.example.instantchatforwp;

/**
 * Created by Div on 5/4/19.
 */

public class CountryModel {
    String m_code;
    String m_name;
    int m_flag;

    public CountryModel(String m_code, String m_name, int m_flag) {
        this.m_code = m_code;
        this.m_name = m_name;
        this.m_flag = m_flag;
    }

    public String getM_code() {
        return m_code;
    }

    public void setM_code(String m_code) {
        this.m_code = m_code;
    }

    public String getM_name() {
        return m_name;
    }

    public void setM_name(String m_name) {
        this.m_name = m_name;
    }

    public int getM_flag() {
        return m_flag;
    }

    public void setM_flag(int m_flag) {
        this.m_flag = m_flag;
    }
}
