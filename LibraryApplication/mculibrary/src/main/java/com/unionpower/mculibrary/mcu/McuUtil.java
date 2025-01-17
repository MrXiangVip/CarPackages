package com.unionpower.mculibrary.mcu;

public class McuUtil {
    /**
     * convert int to 2-bytes
     * @param a
     * @return
     */
    public static byte[] convertIntToByte2(int a) {
        byte[] b = new byte[2];
        for (int i = b.length - 1; i >= 0; i--) {
            b[i] = (byte) (a & 0xFF);
            a = a >> 8;
        }
        return b;
    }

    /**
     * convert int to 4-bytes
     * @param a
     * @return
     */
    private static byte[] convertIntToByte4(int a) {
        byte[] b = new byte[4];
        for (int i = b.length - 1; i >= 0; i--) {
            b[i] = (byte) (a & 0xFF);
            a = a >> 8;
        }
        return b;
    }

    /**
     *
     * @param business 1 byte
     * @param cmd 1 byte
     * @param value 2 bytes
     * @return
     */
    public static byte[] getRadioOutBytes(int business, int cmd,int band, int value){
        byte[] values = convertIntToByte2(value);
        byte[] radioBytes = new byte[]{(byte)business,(byte)cmd,(byte)band,values[0],values[1]};
        return radioBytes;
    }

    public static byte[] getOutputBytes(int business,int cmd,int value){
        byte[] audioBytes = new byte[]{(byte) business,(byte) cmd,(byte)value};
        return audioBytes;
    }

    public static int convertByteToInt(byte byteValue) {
        return byteValue & 0xff;
    }

    public static String parseByteToHexString(byte byteValue) {
        return Integer.toHexString(convertByteToInt(byteValue));
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位⼀组，表⽰⼀个字节,把这样表⽰的16进制字符串，还原成⼀个字节
            b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return b;
    }

    public static String AsciiToString(byte[] result2) {
        StringBuilder sbu = new StringBuilder();
        for (byte b : result2) {
            if (0 == b) {
                break;
            }
            sbu.append((char) Integer.parseInt(String.valueOf(b)));
        }
        return sbu.toString();
    }

    public static byte[] getOutputBytesVolume(int business,int cmd,int type, int value){
        byte[] audioBytes = new byte[]{(byte) business,(byte) cmd,(byte)type, (byte)value};
        return audioBytes;
    }
    public static byte[] getOutputBytesReverse(int business, int cmd, int type){
        byte[] audioBytes = new byte[]{(byte) business,(byte) cmd,(byte)type};
        return audioBytes;
    }

    public static String intsToHexString(int... numbers){
        StringBuilder stringBuilder = new StringBuilder();
        for (int number : numbers) {
            if (number < 16) {
                stringBuilder.append("0").append(Integer.toHexString(number));
            } else {
                stringBuilder.append(Integer.toHexString(number));
            }
        }
        return stringBuilder.toString();
    }

    public static String strIntsToHexString(String str,int... numbers){
        StringBuilder stringBuilder = new StringBuilder(str);
        for (int number : numbers) {
            if (number < 16) {
                stringBuilder.append("0").append(Integer.toHexString(number));
            } else {
                stringBuilder.append(Integer.toHexString(number));
            }
        }
        return stringBuilder.toString();
    }


    //将byte的第k位置1
    public static byte bitTo1(byte b, int k){
        return b = (byte)(b | (0x1 << k));
    }

    //将byte的第k位置0
    public static byte bitTo0(byte b,int k) {
        return b = (byte) (b & (~(0x1 << k)));
    }

    public static int byteArrayToIntLittleEndian(byte[] b, int offset) {
        if (b == null || b.length - offset < 4) {
            throw new IllegalArgumentException("Byte array is too short or offset is invalid.");
        }

        int value = 0;
        // 假设是小端序
        for (int i = 0; i < 4; i++) {
            int shift = i * 8;
            value |= (b[offset + i] & 0xff) << shift; // 先将byte转换为无符号数，然后左移相应位数
        }
        return value;
    }
}

