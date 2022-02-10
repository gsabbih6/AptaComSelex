import java.io.File;

public class RNA {
    String sequence,structure;
    double minimumFreeEnery;

    public RNA(String sequence, String structure, double minimumFreeEnery) {
        this.sequence = sequence;
        this.structure = structure;
        this.minimumFreeEnery = minimumFreeEnery;
    }
    public void exportFasta(File fastaFile){

    }

    public void exportWatsonCrickParams(File paramFile){

    }
}
