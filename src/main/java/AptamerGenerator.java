/*
 * Refer to https://link.springer.com/protocol/10.1007%2F978-1-62703-755-6_1#Sec2*/
public class AptamerGenerator implements Generator {

    private int kmerL = 0;
    public static String PT1 = "PT1";
    public static String PT2 = "PT2";
    public static String PT3 = "PT3";
    public static String PT4 = "PT4";

    char[] bp4 = {'A', 'U', 'C', 'G'};
    char[] bpR = {'A', 'G'};
    char[] bpY = {'U', 'C'};

    public AptamerGenerator() {
    }

    public AptamerGenerator(int kmerLength) {
        setNumberOfBasePairs(kmerLength);
    }

    /*
     * Molecular weight of a 40bp ssRNA is about 12.8kDa*/
//    @Override
    private void setNumberOfBasePairs(int kmerLength) {
        this.kmerL = kmerLength;
    }

    @Override
    public String buildSequence(String type) {

        if (type.equalsIgnoreCase(PT1)) {
            return PT1();
        }
        if (type.equalsIgnoreCase(PT2)) {
            return PT2();
        }
        if (type.equalsIgnoreCase(PT3)) {
            return PT3();
        }
        if (type.equalsIgnoreCase(PT4)) {
            return PT4();
        }
        return "";
    }

    private int getRandomNumber(int min, int max) {
        // excludes max
        return (int) ((Math.random() * (max - min)) + min);
    }

    private String PT1() {

//        not the first reference
        //Based on recommendations from Ruff KM, Snyder TM, Liu DR (2010) Enhanced functional potential of nucleic
        // acid aptamer libraries patterned to increase secondary structure. J Am Chem Soc 132:9453–9464

//        Computational Design of RNA Libraries for In Vitro Selection of Aptamers
//                Authors
//        Authors and affiliations
//        Yaroslav G. ChushakJennifer A. MartinJorge L. ChávezNancy Kelley-LoughnaneMorley O. Stone

        /*
         * PT1: RYRY-N4-(RY)3-N3-(RY)3-N4-(RY)3-N3-RYRY where R = (A, G), Y = (U, C), and N = (A, C, G, U)*/

        // part 1
        StringBuilder s = new StringBuilder();

        char R = bpR[getRandomNumber(0, 2)];
        char Y = bpY[getRandomNumber(0, 2)];
        for (int i = 0; i < 2; i++) {
            s.append(R);
            s.append(Y);
        }
        s.append(getRandomPart(4));

        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 3; i++) {
            s.append(R);
            s.append(Y);
        }
        s.append(getRandomPart(3));

        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 3; i++) {
            s.append(R);
            s.append(Y);
        }

        s.append(getRandomPart(4));

        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 3; i++) {
            s.append(R);
            s.append(Y);
        }
        s.append(getRandomPart(3));
        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 2; i++) {
            s.append(R);
            s.append(Y);
        }

        for (int i = 0; i < 10; i++) {
            s.append(getRandomPart(4));
        }
        return s.toString();
    }

    private String PT2() {
        //Based on recommendations from Ruff KM, Snyder TM, Liu DR (2010) Enhanced functional potential of nucleic
        // acid aptamer libraries patterned to increase secondary structure. J Am Chem Soc 132:9453–9464

        /*
         * PT2: RRYY-N3-RRYY-N4-RRYY-N2-RRYY-N4-RRYY-N3-RRYY where R = (A, G), Y = (U, C), and N = (A, C, G, U)*/

        // part 1
        StringBuilder s = new StringBuilder();

        char R = bpR[getRandomNumber(0, 2)];
        char Y = bpY[getRandomNumber(0, 2)];
        for (int i = 0; i < 2; i++) {
            s.append(R);
        }
        for (int i = 0; i < 2; i++) {
            s.append(Y);
        }
        s.append(getRandomPart(3));

        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 2; i++) {
            s.append(R);
        }
        for (int i = 0; i < 2; i++) {
            s.append(Y);
        }

        s.append(getRandomPart(4));

        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 2; i++) {
            s.append(R);
        }
        for (int i = 0; i < 2; i++) {
            s.append(Y);
        }

        s.append(getRandomPart(2));

        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 2; i++) {
            s.append(R);
        }
        for (int i = 0; i < 2; i++) {
            s.append(Y);
        }
        s.append(getRandomPart(4));
        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 2; i++) {
            s.append(R);
        }
        for (int i = 0; i < 2; i++) {
            s.append(Y);
        }
        s.append(getRandomPart(3));
        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 2; i++) {
            s.append(R);
        }
        for (int i = 0; i < 2; i++) {
            s.append(Y);
        }
        for (int i = 0; i < 10; i++) {
            s.append(getRandomPart(4));
        }
        return s.toString();
    }

    private String PT3() {
        //Based on recommendations from Ruff KM, Snyder TM, Liu DR (2010) Enhanced functional potential of nucleic
        // acid aptamer libraries patterned to increase secondary structure. J Am Chem Soc 132:9453–9464

        /*
         * PT2: RRYY-N3-RRYY-N4-RRYY-N2-RRYY-N4-RRYY-N3-RRYY where R = (A, G), Y = (U, C), and N = (A, C, G, U)*/

        // part 1
        StringBuilder s = new StringBuilder();

        char R = bpR[getRandomNumber(0, 2)];
        char Y = bpY[getRandomNumber(0, 2)];
        for (int i = 0; i < 2; i++) {
            s.append(R);
        }
        for (int i = 0; i < 2; i++) {
            s.append(Y);
        }
        s.append(getRandomPart(3));

        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 3; i++) {
            s.append(R);
        }
        for (int i = 0; i < 3; i++) {
            s.append(Y);
        }

        s.append(getRandomPart(4));

        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 3; i++) {
            s.append(R);
        }
        for (int i = 0; i < 3; i++) {
            s.append(Y);
        }

        s.append(getRandomPart(4));

        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 3; i++) {
            s.append(R);
        }
        for (int i = 0; i < 3; i++) {
            s.append(Y);
        }
        s.append(getRandomPart(3));
        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 2; i++) {
            s.append(R);
        }
        for (int i = 0; i < 2; i++) {
            s.append(Y);
        }
        for (int i = 0; i < 10; i++) {
            s.append(getRandomPart(4));
        }
        return s.toString();
    }

    private String PT4() {
        //Based on recommendations fromRuff KM, Snyder TM, Liu DR (2010) Enhanced functional potential of nucleic
        // acid aptamer libraries patterned to increase secondary structure. J Am Chem Soc 132:9453–9464

        /*
         * PT4: RRYY-N3-(RY)3-N4-(RY)3-N4-(RY)3-N3-RRYY where R = (A, G), Y = (U, C), and N = (A, C, G, U)*/

        // part 1
        StringBuilder s = new StringBuilder();

        char R = bpR[getRandomNumber(0, 2)];
        char Y = bpY[getRandomNumber(0, 2)];
        for (int i = 0; i < 2; i++) {
            s.append(R);
        }
        for (int i = 0; i < 2; i++) {
            s.append(Y);
        }
        s.append(getRandomPart(3));

        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 3; i++) {
            s.append(R);
            s.append(Y);
        }

        s.append(getRandomPart(4));

        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 3; i++) {
            s.append(R);
            s.append(Y);
        }

        s.append(getRandomPart(4));

        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 3; i++) {
            s.append(R);
            s.append(Y);
        }
        s.append(getRandomPart(3));
        R = bpR[getRandomNumber(0, 2)];
        Y = bpY[getRandomNumber(0, 2)];

        for (int i = 0; i < 2; i++) {
            s.append(R);
        }
        for (int i = 0; i < 2; i++) {
            s.append(Y);
        }
        for (int i = 0; i < 10; i++) {
            s.append(getRandomPart(4));
        }
        return s.toString();
    }

    private char[] getRandomPart(int number) {

        //part 2 completely random
        char c[] = new char[number];
        for (int i = 0; i < number; i++) {
            c[i] = bp4[getRandomNumber(0, 4)];
        }
        return c;
    }
}
