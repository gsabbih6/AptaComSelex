import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Result {
    public void processResults() throws IOException {
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

    public void processResultsRPISeq() throws IOException {
        File dir = new File("output");
        File[] outdir = dir.listFiles();
        List<RPISeqPOJO> rpiSeqPOJOS = new ArrayList<>();
        if (outdir != null) {
            for (File child : outdir) {
//                ZipFile zipFile = new ZipFile(child.getAbsolutePath());
//
//                Enumeration<? extends ZipEntry> entries = zipFile.entries();

//                while (entries.hasMoreElements()) {
//                    ZipEntry entry = entries.nextElement();
                    InputStream stream = new FileInputStream(child);
//                    System.out.println(entry.getName());
                    BeanListProcessor<RPISeqPOJO> rowProcessor = new BeanListProcessor<>(RPISeqPOJO.class);
                    TsvParserSettings settings = new TsvParserSettings();
                    settings.getFormat().setLineSeparator("\n");
                    settings.setProcessor(rowProcessor);

// creates a TSV parser
                    TsvParser parser = new TsvParser(settings);

// parses all rows in one go.
                    parser.parse(new InputStreamReader(stream, "UTF-8"));
//                    System.out.println(rowProcessor.getBeans());
                    rpiSeqPOJOS.addAll(rowProcessor.getBeans());
//                }
            }

// Writing to an in-memory byte array. This will be printed out to the standard output so you can easily see the result.
            ByteArrayOutputStream csvResult = new ByteArrayOutputStream();

            // CsvWriter (and all other file writers) work with an instance of java.io.Writer
            Writer outputWriter = new OutputStreamWriter(csvResult);
            //##CODE_START
            CsvWriterSettings settings = new CsvWriterSettings();
            settings.setRowWriterProcessor(new BeanWriterProcessor<RPISeqPOJO>(RPISeqPOJO.class));
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
            rpiSeqPOJOS.stream().forEach((data) -> {
//                data.setTopRNAFragment("'" + data.getTopRNAFragment());
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
}
