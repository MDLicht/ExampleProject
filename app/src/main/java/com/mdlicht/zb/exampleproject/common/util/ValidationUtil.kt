package com.mdlicht.zb.exampleproject.common.util

import android.util.Patterns
import java.util.regex.Matcher
import java.util.regex.Pattern


object ValidationUtil {
    /**
     * @brief 이메일 유효성 검사
     * @param email
     * @return 유효 여부
     */
    fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        val m = pattern.matcher(email)

        return m.matches()
    }

    /**
     * @brief 비밀번호 유효성 검사
     * @param pw
     * @return 유효 여부
     */
    fun isValidPw(pw: String): Boolean {
        val pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,16}$")
        val m = pattern.matcher(pw)

        return m.matches()
    }

    /**
     * @brief 이름 유효성 검사
     * @param name
     * @return 유효 여부
     */
    fun isValidName(name: String): Boolean {
        val pattern: Pattern = Pattern.compile("^[가-힣a-zA-Z]{1,20}$")
        val m: Matcher = pattern.matcher(name)

        return m.matches()
    }

    /**
     * @brief 전화번호 유효성 검사
     * @param phoneNum
     * @return 유효 여부
     */
    fun isValidPhoneNum(phoneNum: String): Boolean {
        if (phoneNum.contains("-"))
            return false
        val pattern = Pattern.compile("^01([0|1|6|7|8|9])([0-9]{3,4})([0-9]{4})$")
        val m = pattern.matcher(phoneNum)

        return m.matches()
    }

    /**
     * @brief 숫자 문자열 유효성 검사
     * @param numberString
     * @return 유효 여부
     */
    fun isNumberString(numberString: String): Boolean {
        val pattern = Pattern.compile("^[^0-9]$")
        val m = pattern.matcher(numberString)

        return m.matches()
    }
}