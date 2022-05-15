import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Testing {
    private RNAFolder2D folder;

    @BeforeEach
    public void setUp() throws Exception {
        folder = new RNAFolder2D();
    }

    @Test
    @DisplayName("Simple multiplication should work")
    public void testMultiply() {
//        assertEquals("..((((......))))..........", folder.predictMFE("GACCGUCAAAUUGCGGGAAAGGGGUC").structure,
//                "Worked");
        assertEquals(10.0, folder.predictMFE(
                "AAGAGCCCAAAACAACACAACCACCACACCACCCAGACACACUACACACGCA").mfe,
                "Worked");

    }

    @Test
    public void test() throws IOException {
        File dir = new File("output");
        File[] rnadir = dir.listFiles();
        List<PropensityPOJO> propensityPOJOS = new ArrayList<>();
        if (rnadir != null) {
            for (File child : rnadir) {
                ZipFile zipFile = new ZipFile(child.getAbsolutePath());

                Enumeration<? extends ZipEntry> entries = zipFile.entries();

                while (entries.hasMoreElements()) {
                    ZipEntry entry = entries.nextElement();
                    InputStream stream = zipFile.getInputStream(entry);
                    System.out.println(entry.getName());
                    BeanListProcessor<PropensityPOJO> rowProcessor = new BeanListProcessor<>(PropensityPOJO.class);
                    TsvParserSettings settings = new TsvParserSettings();
                    settings.getFormat().setLineSeparator("\n");
                    settings.setProcessor(rowProcessor);

// creates a TSV parser
                    TsvParser parser = new TsvParser(settings);

// parses all rows in one go.
                    parser.parse(new InputStreamReader(stream, "UTF-8"));
//                    System.out.println(rowProcessor.getBeans());
                    propensityPOJOS.addAll(rowProcessor.getBeans());
                }
            }

// Writing to an in-memory byte array. This will be printed out to the standard output so you can easily see the result.
            ByteArrayOutputStream csvResult = new ByteArrayOutputStream();

            // CsvWriter (and all other file writers) work with an instance of java.io.Writer
            Writer outputWriter = new OutputStreamWriter(csvResult);
            //##CODE_START
            CsvWriterSettings settings = new CsvWriterSettings();
            settings.setRowWriterProcessor(new BeanWriterProcessor<PropensityPOJO>(PropensityPOJO.class));
            // Encloses all records within quotes even when they are not required.
            settings.setQuoteAllFields(true);

            // Sets the file headers (used for selection, these values won't be written automatically)
//            settings.setHeaders("Protein_ID	RNA_ID", "Top_RNA_fragment", "Annotation", "Interaction_Propensity",
//                    "Z_score", "RBP_Propensity", "RNA_Binding_Domains_IDs", "numof.RNA.Binding_Domains_Instances",
//                    "RNA.Binding_Motifs_IDs", "numof.RNA_Binding_Motifs_Instances", "Ranking"
//            );

            // Creates a writer with the above settings;
            CsvWriter writer = new CsvWriter(outputWriter, settings);
            writer.writeHeaders();
            // Writes the headers specified in the settings
//            System.out.println(writer.writeHeadersToString());
            propensityPOJOS.stream().forEach((data) -> {
                data.setTopRNAFragment("'" + data.getTopRNAFragment());
                writer.processRecord(data);
            });

            writer.close();
            System.out.println(csvResult);
            File file = new File("results/result.csv");
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();// make directory
            try (OutputStream outputStream = new FileOutputStream(file)) {
                csvResult.writeTo(outputStream);
            }

        }
    }

    @Test
    public void readFile() throws IOException {
//        File diro = new File("data");
//        File[] proteindir = diro.listFiles();
//        ProcessingPipe p = new ProcessingPipe(proteindir[0], proteindir[0]);
//  String url=p.processRPISeq("SQATSQPINFQVQKDGSSEKSHMDDYMQHPGKVIKQNNKYYFQTVLNNASFWKEYKFYNA\n" +
//                "NNQELATTVVNDNKKADTRTINVAVEPGYKSLTTKVHIVVPQINYNHRYTTHLEFEKAIP\n" +
//                "TLA");
//        File file = new File("output/" + proteindir[0].getName() + "_out.txt");
////        String url = processCatrapid();
//        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();// make directory
//        try {
//            FileUtils.copyURLToFile(
//                    new URL(url),
//                    file,
//                    300000,
//                    300000);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        new Result().processResultsRPISeq();
    }
}
