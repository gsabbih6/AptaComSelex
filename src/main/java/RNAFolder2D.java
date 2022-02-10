import rnafold4j.MFEData;
import rnafold4j.RNAFoldAPI;

public class RNAFolder2D {
    public FoldRes predictMFE(String RNASeq) {
        // The sequence to be predicted
        byte[] seq = new String(RNASeq).getBytes();
        // Create a new instance of RNAFold4J
        RNAFoldAPI rfa = new RNAFoldAPI();
        // Predict the MFE and corresponding structure
        MFEData mfe = rfa.getMFE(seq);

        return new FoldRes(new String(mfe.structure),Double.valueOf(mfe.mfe));
    }

    class FoldRes {
        String structure; Double mfe;

        public FoldRes(String structure, Double mfe) {
            this.structure = structure;
            this.mfe = mfe;
        }
    }
}
