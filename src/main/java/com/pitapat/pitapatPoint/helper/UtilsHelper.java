package com.pitapat.pitapatPoint.helper;

public class UtilsHelper {
    public static String telFormat( String str ) {
        // 숫자만 추출
        str = str.replaceAll("[^0-9]", "");
        StringBuilder tmp = new StringBuilder();

        if (str.length() < 4) {
            return str;
        } else if (str.length() < 7) {
            tmp.append(str.substring(0, 3));
            tmp.append("-");
            tmp.append(str.substring(3));
        } else if (str.length() < 11) {
            tmp.append(str.substring(0, 3));
            tmp.append("-");
            tmp.append(str.substring(3, 6));
            tmp.append("-");
            tmp.append(str.substring(6));
        } else {
            tmp.append(str.substring(0, 3));
            tmp.append("-");
            tmp.append(str.substring(3, 7));
            tmp.append("-");
            tmp.append(str.substring(7));
        }

        return tmp.toString();
    }

    public static String withComma( Integer number ) {
        // 소수점 기준으로 숫자를 나눕니다.
        String[] parts = String.valueOf(number).split("\\.");

        // 정수 부분에 3자리마다 쉼표를 추가합니다.
        String integerPart = parts[0];
        integerPart = integerPart.replaceAll("\\B(?=(\\d{3})+(?!\\d))", ",");

        // 소수점 부분이 있는 경우
        if (parts.length > 1) {
            return integerPart + "." + parts[1];
        } else {
            return integerPart;
        }
    }
}
