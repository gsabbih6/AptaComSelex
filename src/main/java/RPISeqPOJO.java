import com.univocity.parsers.annotations.Parsed;
//	RNA_ID

public class RPISeqPOJO {
    @Parsed(field = "Header")
    private String sequenceName;
    @Parsed(field = "RF")
    private String randonForest;
    @Parsed(field = "SVM")
    private String supportVector;

    @Override
    public String toString() {
        return "RPISeqPOJO{" +
                "sequenceName='" + sequenceName + '\'' +
                ", randonForest='" + randonForest + '\'' +
                ", supportVector='" + supportVector + '\'' +
                '}';
    }
}
