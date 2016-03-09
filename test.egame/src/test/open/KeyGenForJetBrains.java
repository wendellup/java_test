package test.open;

import java.io.*;
import java.math.BigInteger;
import java.util.*;
import java.util.zip.CRC32;

/**
 * KeyGenForJetBrains.
 *
 */
public class KeyGenForJetBrains {

    private KeyGenForJetBrains() {}

    static PrintStream out = System.out;


    public static void main(String[] args) {

        String name = args.length > 0 ? args[0] : "Normal";

        KeyGen keyGen = new KeyGen();
        keyGen.productType = ProductType.getProductType(args.length > 1 ? args[1] : "IDEA");
        keyGen.version = Byte.parseByte(args.length > 2 ? args[2] : "14");
        keyGen.licenseType = LicenseType.valueOf(args.length > 3 ? args[3] : "COMMERCIAL"); // now not used

        out.println("Product Name   : " + keyGen.productType + ' ' + keyGen.version);
        out.println("User Name      : " + name);
        out.println("License Key    : " + keyGen.makeKey(name, 0, new Random().nextInt(1000)));
    }


    public enum ProductType {
        IDEA(1);

        private int id;

        private ProductType(int id) { this.id = id; }

        public int id() {
            return this.id;
        }

        public static ProductType getProductType(String str) {
            for (ProductType product : values()) {
                if (product.toString().equalsIgnoreCase(str)) {
                    return product;
                }
            }
            return null;
        }
    }


    @SuppressWarnings("UnusedDeclaration")
    public enum LicenseType {
        COMMERCIAL(0),
        NON_COMMERCIAL(1),
        SITE(2),
        OPENSOURCE(3),
        PERSONAL(4),
        YEARACADEMIC(5);

        private int id;

        private LicenseType(int id) { this.id = id; }

        public int id() {
            return this.id;
        }
    }


    static class KeyGen {

        public static short getCRC(String s, int i, byte[] bytes) {
            CRC32 crc32 = new CRC32();
            if (s != null) {
                for (int j = 0; j < s.length(); j++) {
                    char c = s.charAt(j);
                    crc32.update(c);
                }
            }
            crc32.update(i);
            crc32.update(i >> 8);
            crc32.update(i >> 16);
            crc32.update(i >> 24);
            for (int k = 0; k < bytes.length - 2; k++) {
                byte byte0 = bytes[k];
                crc32.update(byte0);
            }
            return (short) (int) crc32.getValue();
        }

        public static String encodeGroups(BigInteger biginteger) {
            BigInteger beginner1 = BigInteger.valueOf(60466176L);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; biginteger.compareTo(BigInteger.ZERO) != 0; i++) {
                int j = biginteger.mod(beginner1).intValue();
                String s1 = encodeGroup(j);
                if (i > 0) {
                    sb.append("-");
                }
                sb.append(s1);
                biginteger = biginteger.divide(beginner1);
            }
            return sb.toString();
        }


        public static String encodeGroup(int i) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 5; j++) {
                int k = i % 36;
                char c;
                if (k < 10) {
                    c = (char) (48 + k);
                } else {
                    c = (char) (65 + k - 10);
                }
                sb.append(c);
                i /= 36;
            }
            return sb.toString();
        }


        ProductType productType = ProductType.IDEA;
        byte version = 14;

        LicenseType licenseType = LicenseType.COMMERCIAL;


        public String makeKey(String name, int days, int id) {
            id %= 10000;
            byte[] bKey = new byte[12];
            bKey[0] = (byte) productType.id();
            bKey[1] = version;

            Date d = new Date();
            long ld = d.getTime() >> 16;
            bKey[2] = (byte) (int) (ld & 0xFF);
            bKey[3] = (byte) (int) (ld >> 8 & 0xFF);
            bKey[4] = (byte) (int) (ld >> 16 & 0xFF);
            bKey[5] = (byte) (int) (ld >> 24 & 0xFF);

            days &= 65535;
            bKey[6] = (byte) (days & 0xFF);
            bKey[7] = (byte) (days >> 8 & 0xFF);

            bKey[8] = 105;
            bKey[9] = -59;
            bKey[10] = 0;
            bKey[11] = 0;

            int w = getCRC(name, id % 10000, bKey);
            bKey[10] = (byte) (w & 0xFF);
            bKey[11] = (byte) (w >> 8 & 0xFF);

            BigInteger pow = new BigInteger("89126272330128007543578052027888001981", 10);
            // noinspection SpellCheckingInspection
            BigInteger mod = new BigInteger("86f71688cdd2612ca117d1f54bdae029", 16);
            BigInteger k0 = new BigInteger(bKey);
            BigInteger k1 = k0.modPow(pow, mod);
            String s0 = Integer.toString(id);
            String sz = "0";
            while (s0.length() != 5) {
                s0 = sz.concat(s0);
            }
            s0 = s0.concat("-");

            String s1 = encodeGroups(k1);
            s0 = s0.concat(s1);
            return s0;
        }
    }
}
