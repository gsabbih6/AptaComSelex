import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.common.processor.BeanWriterProcessor;
import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Docking {
    private final String pathToRNA;
    private final String pathToDock = "dockR_results";
    private ExecutorService pool;
    public Docking(String pathToRNA) {
        this.pathToRNA = pathToRNA;
        pool= Executors.newFixedThreadPool(10);
    }

    public void dock(ProcessBuilder processBuilder, String recPath, String ligPath, String outName) {

        processBuilder.command("bash", "-c", "hdock " + recPath + " " + ligPath + " -out " + outName);
        try {

            Process process = processBuilder.start();
            System.out.println(processBuilder.command());
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }
//            int exitVal = process.waitFor();
//            if (exitVal == 0) {
//                System.out.println("Success!");
//                System.out.println(output);
//                System.exit(0);
//            } else {
//                //abnormal...
//            }

        } catch (IOException e) {
            e.printStackTrace();
        }
//        catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }
//    public void createPdb(ProcessBuilder processBuilder){
//
//        processBuilder.command("bash", "-c", "hdock " + recPath + " " + ligPath + " -out " + outName);
//        try {
//
//            Process process = processBuilder.start();
//            System.out.println(processBuilder.command());
//            StringBuilder output = new StringBuilder();
//            BufferedReader reader = new BufferedReader(
//                    new InputStreamReader(process.getInputStream()));
//            String line;
//            while ((line = reader.readLine()) != null) {
//                output.append(line + "\n");
//            }
//            int exitVal = process.waitFor();
//            if (exitVal == 0) {
//                System.out.println("Success!");
//                System.out.println(output);
//                System.exit(0);
//            } else {
//                //abnormal...
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }

    public void startProcess() throws IOException {
        File dir = new File(pathToRNA);
        File[] rnadir = dir.listFiles();

        Stream<Path> paths = Files.walk(Paths.get(pathToRNA));
        paths.filter(Files::isRegularFile).forEach((data) -> {
            String[] splits = data.toString().split("\\.");
            if (splits[splits.length - 1].equalsIgnoreCase("pdb")) {
                String[] sp = splits[splits.length - 2].split("\\\\");
//                System.out.println(sp[sp.length-1]);
                pool.submit(new DockingConcurrent(new ProcessBuilder(),
                        "./proteindata/Isda.pdb",
                        "./"+data.toString().replace("\\", "/"),
                        sp[sp.length-1]+".out"));

//                dock(new ProcessBuilder(),
//                        "./proteindata/Isda.pdb",
//                        "./"+data.toString().replace("\\", "/"),
//                        sp[sp.length-1]+".out");
            }
        });
//        pool.shutdown();
//        if (rnadir != null) {
//            for (File child : rnadir) {
//                ZipFile zipFile = new ZipFile(child.getAbsolutePath());
//
//                Enumeration<? extends ZipEntry> entries = zipFile.entries();
//                while (entries.hasMoreElements()) {
//                    ZipEntry entry = entries.nextElement();
//                    InputStream stream = zipFile.getInputStream(entry);
//                    String[] splits = entry.getName().split("\\.");
////                    System.out.println(splits);
//                    if (splits[splits.length-1].equalsIgnoreCase("pdb")) {
//                        System.out.println(entry.getName());
//                        dock(new ProcessBuilder(),"./proteindata/Isda.pdb","./test/"+entry.getName(),"entry.getName()"+".out");
//                    }
//                }
//            }
//
//        }
    }
}
