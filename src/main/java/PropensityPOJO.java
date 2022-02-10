import com.univocity.parsers.annotations.Parsed;
//	RNA_ID

public class PropensityPOJO {
    @Parsed(field = "Protein_ID")
    private String proteinId;
    @Parsed(field = "RNA_ID")
    private String rnaId;

    public String getTopRNAFragment() {
        return topRNAFragment;
    }

    public void setTopRNAFragment(String topRNAFragment) {
        this.topRNAFragment = topRNAFragment;
    }

    @Parsed(field = "Top_RNA_fragment")
    private String topRNAFragment;
    @Parsed(field = "Annotation")
    private String annotation;
    @Parsed(field = "Interaction_Propensity")
    private String interactionPropensity;
    @Parsed(field = "Z_score")
    private String zScore;
    @Parsed(field = "RBP_Propensity")
    private String RBPPropensity;
    @Parsed(field = "RNA_Binding_Domains_IDs")
    private String rnaBindingDomainIds;
    @Parsed(field = "numof.RNA.Binding_Domains_Instances")
    private String numRNABindingDomainsInstances;
    @Parsed(field = "RNA.Binding_Motifs_IDs")
    private String rnaBindingMotifsIds;
    @Parsed(field = "numof.RNA_Binding_Motifs_Instances")
    private String numRNABindingMotifsInstances;
    @Parsed(field = "Ranking")
    private String ranking;

    @Override
    public String toString() {
        return "PropensityPOJO{" +
                "proteinId='" + proteinId + '\'' +
                ", rnaId='" + rnaId + '\'' +
                ", topRNAFragment='" + topRNAFragment + '\'' +
                ", annotation='" + annotation + '\'' +
                ", interactionPropensity='" + interactionPropensity + '\'' +
                ", zScore='" + zScore + '\'' +
                ", RBPPropensity='" + RBPPropensity + '\'' +
                ", rnaBindingDomainIds='" + rnaBindingDomainIds + '\'' +
                ", numRNABindingDomainsInstances='" + numRNABindingDomainsInstances + '\'' +
                ", rnaBindingMotifsIds='" + rnaBindingMotifsIds + '\'' +
                ", numRNABindingMotifsInstances='" + numRNABindingMotifsInstances + '\'' +
                ", ranking='" + ranking + '\'' +
                '}';
    }
}
