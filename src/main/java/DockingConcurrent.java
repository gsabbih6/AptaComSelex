import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DockingConcurrent implements Runnable {

    private ProcessBuilder processBuilder;
    private String recPath, ligPath, outName;

    public DockingConcurrent(ProcessBuilder processBuilder, String recPath, String ligPath, String outName) {
        this.processBuilder = processBuilder;
        this.outName = outName;
        this.ligPath = ligPath;
        this.recPath = recPath;
    }

    @Override
    public void run() {
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
//                System.out.println(line);
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

    }
}
