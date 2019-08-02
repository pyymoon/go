package com.start.homework;

public class TransformNumber {

    public static int PIECE_SIZE = 4;
    public static String[] UPCASE = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    public static String[] UNIT = {"", "拾", "佰", "仟", "圆整", "万", "亿"};

    public String transformLongToReceipt(Long number) {
        int digit = 0;
        int[] fourDigit = new int[PIECE_SIZE];
        StringBuffer result = new StringBuffer();
        while (number != 0) {
            int flag = 0;
            for (int i = 0; i < PIECE_SIZE; i++) {
                fourDigit[i] = number.intValue() % 10;
                number /= 10;
                flag += fourDigit[i];
            }
            if (flag != 0) {
                result.insert(0, UNIT[digit + 4]);
                getStringWithFourDigit(result, fourDigit);
            }
            digit++;
        }
        if (result.charAt(0) == '零') {
            result.delete(0, 1);
        }
        return result.toString();
    }

    /**
     * 匹配范式比如"0100"、"1001"、"0101"、"1010"等情况
     *
     * @param str
     * @param number
     */
    private void getStringWithFourDigit(StringBuffer str, int[] number) {
        for (int i = 0; i < PIECE_SIZE; i++) {
            if (number[i] == 0) {
                if (i == 3 || (i > 0 && number[i + 1] != 0 && (number[i - 1] != 0 | number[0] != 0))) {
                    str.insert(0, "零");
                }
            } else {
                getUnitByDigit(i, str);
                getSingleByInt(number[i], i, str);
            }
        }
    }

    /**
     * 插入单位
     *
     * @param digit
     * @param string
     */
    private void getUnitByDigit(int digit, StringBuffer string) {
        digit = digit % PIECE_SIZE;
        string.insert(0, UNIT[digit]);
    }

    /**
     * 插入数字对应的大写汉字，个位数为0时不插入任何值
     *
     * @param number
     * @param digit
     * @param string
     */
    private void getSingleByInt(int number, int digit, StringBuffer string) {
        if (number == 0 & digit % PIECE_SIZE == 0) {
            return;
        }
        string.insert(0, UPCASE[number]);
    }

    public static void main(String[] args) {
        long in_1 = 101001010;
        long in_2 = 1024345;
        TransformNumber tfn = new TransformNumber();
        String result = tfn.transformLongToReceipt(in_1);
        System.out.println(result);
    }
}