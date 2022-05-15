import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainApplication {
    private static ExecutorService executor;

    public static void main(String[] args) throws IOException, InterruptedException {

        for (String s : args) {
            if (s.equalsIgnoreCase("-g_aptamers_seq")) {
//                generateAptamers(Integer.parseInt(args[1]));
//                initCatRAPIDProcess();

            }
            if (s.equalsIgnoreCase("-s_dock")) {
//                new Docking("rna3d").startProcess();
            }
            if (s.equalsIgnoreCase("do_catrapid")) {
                initCatRAPIDProcess();
            }
            if (s.equalsIgnoreCase("do_rpiseq")) {
                initPRISeqProcess();
            }
        }
//        new Docking("test").dock(new ProcessBuilder(),
//                "./HDOCKlite/1CGI_r_b.pdb", "./HDOCKlite/1CGI_l_b.pdb", "Hdock.out");


//
//
//        new Docking("rna3d").startProcess();
    }

    private static void initPRISeqProcess() throws IOException, InterruptedException {

        executor = Executors.newFixedThreadPool(10);
//        int defaultNum = args == null ? 100 : Integer.parseInt(args[0]);
        // Process files in data
        File dir = new File("data");
        File[] rnadir = dir.listFiles();
        File diro = new File("proteindata");
        File[] proteindir = diro.listFiles();
        if (rnadir != null && proteindir != null) {
            for (File child : rnadir) {
                // Do something with child
//                System.out.println(child.getAbsolutePath());
                Thread.sleep(300);
                processRPISeq(FileUtils.readFileToString(proteindir[0], "UTF-8"), child);
            }
            System.out.println(proteindir[0].getAbsolutePath());
        } else {
            // Handle the case where dir is not really a directory.
            // Checking dir.isDirectory() above would not be sufficient
            // to avoid race conditions with another process that deletes
            // directories.
        }
        executor.shutdown();
//         release resources

        if (executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS)) {
            new Result().processResultsRPISeq();
        }


//        result(executor);
//        new Result().processResults();
    }

    private static void initCatRAPIDProcess() throws IOException, InterruptedException {

        executor = Executors.newFixedThreadPool(10);
//        int defaultNum = args == null ? 100 : Integer.parseInt(args[0]);
        // Process files in data
        File dir = new File("data");
        File[] rnadir = dir.listFiles();
        File diro = new File("proteindata");
        File[] proteindir = diro.listFiles();
        if (rnadir != null && proteindir != null) {
            for (File child : rnadir) {
                // Do something with child
//                System.out.println(child.getAbsolutePath());
                Thread.sleep(300);
                process(proteindir[0], child);
            }
            System.out.println(proteindir[0].getAbsolutePath());
        } else {
            // Handle the case where dir is not really a directory.
            // Checking dir.isDirectory() above would not be sufficient
            // to avoid race conditions with another process that deletes
            // directories.
        }
        executor.shutdown();
//         release resources

        if (executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS)) {
            new Result().processResults();
        }


//        result(executor);
//        new Result().processResults();
    }

    private static void result(ExecutorService executors) throws IOException {
        if (!executors.isShutdown()) {
            result(executors);
        } else {
            new Result().processResults();
        }
        ;

    }

    private static void generateAptamers(int number) throws IOException {

        int numPool = number; // fro each pattern
        String[] p1s = new String[numPool];
        String[] p2s = new String[numPool];
        String[] p3s = new String[numPool];
        String[] p4s = new String[numPool];

        // generate random patterned sequences of 40mers

        List<String> p1sArray = Arrays.asList(p1s);
        List<String> p1sArray1 = new ArrayList<>();
        List<String> p2sArray1 = new ArrayList<>();
        List<String> p3sArray1 = new ArrayList<>();
        List<String> p4sArray1 = new ArrayList<>();
        p1sArray.parallelStream().forEach((seq) -> {
            p1sArray1.add(new AptamerGenerator().buildSequence(AptamerGenerator.PT1));
            p2sArray1.add(new AptamerGenerator().buildSequence(AptamerGenerator.PT2));
            p3sArray1.add(new AptamerGenerator().buildSequence(AptamerGenerator.PT3));
            p4sArray1.add(new AptamerGenerator().buildSequence(AptamerGenerator.PT4));
        });


        p1s = p1sArray1.toArray(new String[p1sArray1.size()]);
        p2s = p2sArray1.toArray(new String[p2sArray1.size()]);
        p3s = p3sArray1.toArray(new String[p3sArray1.size()]);
        p4s = p4sArray1.toArray(new String[p4sArray1.size()]);
        System.out.println(p1s.length);
        System.out.println(p2s.length);
        System.out.println(p3s.length);
        System.out.println(p4s.length);
        RNAFolder2D rfa = new RNAFolder2D();
        HashSet<RNA> rnaPool = new HashSet<>();
        if (!p1sArray1.isEmpty())
            p1sArray1.parallelStream().forEach((seq) -> {
                RNAFolder2D.FoldRes res = rfa.predictMFE(seq);
                rnaPool.add(new RNA(seq, res.structure, res.mfe));
            });
        if (!p2sArray1.isEmpty())
            p2sArray1.parallelStream().forEach((seq) -> {
                RNAFolder2D.FoldRes res = rfa.predictMFE(seq);
                rnaPool.add(new RNA(seq, res.structure, res.mfe));
            });
        if (!p3sArray1.isEmpty())
            p3sArray1.parallelStream().forEach((seq) -> {
                RNAFolder2D.FoldRes res = rfa.predictMFE(seq);
                rnaPool.add(new RNA(seq, res.structure, res.mfe));
            });
        if (!p4sArray1.isEmpty())
            p4sArray1.parallelStream().forEach((seq) -> {
                RNAFolder2D.FoldRes res = rfa.predictMFE(seq);
                rnaPool.add(new RNA(seq, res.structure, res.mfe));
            });


        // Perform screening based on set criteria
        PriorityQueue<RNA> newRnaPool;
        ScreeningRNA screeningRNA = new ScreeningRNA(rnaPool, -23.0, 10, 30);
        screeningRNA.process();
        newRnaPool = screeningRNA.getNewPool();
        Iterator<RNA> ite = newRnaPool.iterator();

        // Save in fasta format >seq:structure
        File fout = new File("GD2AptamerFasta.fasta");
//        File fout1 = new File("GD2AptamerStrFasta.fasta");
        FileOutputStream fos = new FileOutputStream(fout);
//        FileOutputStream fos1 = new FileOutputStream(fout1);
//
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
//        BufferedWriter bw1 = new BufferedWriter(new OutputStreamWriter(fos1));

        int count = 1;
        int num = 0;
        int breakNum = 100;
        while (!newRnaPool.isEmpty()) {


            if (num % breakNum == 0) {
                bw.close();
                fout = new File("data/GD2AptamerFasta_" + num / breakNum + ".fasta");
                if (!fout.getParentFile().exists())
                    fout.getParentFile().mkdirs();
                fos = new FileOutputStream(fout);
                bw = new BufferedWriter(new OutputStreamWriter(fos));
            }
            RNA rna = newRnaPool.poll();
//            bw.write(">Sequence" + count + " | " + rna.minimumFreeEnery + "kcal/mol");
            bw.write(">Sequence" + count);
            bw.newLine();
            bw.write(rna.sequence);
//            bw.newLine();
//            bw.write(rna.structure);
            bw.newLine();
//            bw1.write(">Sequence" + count );
//            bw1.newLine();
//            bw1.write(rna.structure);
//            bw1.newLine();
            count++;
            num++;
        }
        bw.close();
//        bw1.close();


    }

    private static void process(File protienFasta, File rnaFasta) {
        executor.submit(new ProcessingPipe(protienFasta, rnaFasta));
    }

    private static void processRPISeq(String protienFasta, File rnaFasta) {
        executor.submit(new ProcessingRPISeq(protienFasta, rnaFasta));
    }

    private static String covertTo3D(ProcessBuilder processBuilder, RNA rna) {
        String[] cmd = {"bash", "-c", "./rna_denovo.linuxgccrelease -sequence \"ucaggu aagcag\" -secstruct \"(....( )....)\" -nstruct 2 -out:file:silent test.out -minimize_rna"};
        try {

            Process process = Runtime.getRuntime().exec(cmd);

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
                System.exit(0);
            } else {
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void initialDocking(ProcessBuilder processBuilder, String recPath, String ligPath, String outPath) {
        processBuilder.command("bash", "-c", "./HDOCKlite/hdock " + recPath + " " + ligPath + " -out " + outPath);
        try {

            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
                System.exit(0);
            } else {
                //abnormal...
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
