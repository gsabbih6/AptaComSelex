import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;

public class ScreeningRNA {
    HashSet<RNA> pool;
//    HashSet<RNA> newPool;
    PriorityQueue<RNA> newPool;
    double cutOffMFE;

    public ScreeningRNA(HashSet<RNA> pool, double cutOffMFE, int minBP, int maxBP) {
        this.cutOffMFE = cutOffMFE;
        this.pool = pool;
//        newPool=new HashSet<>();
        newPool=new PriorityQueue<>(new Comparator<RNA>() {
            @Override
            public int compare(RNA o1, RNA o2) {
                return Double.compare(o1.minimumFreeEnery,o2.minimumFreeEnery);
            }
        });
    }

    public void process() {
        //
        System.out.println(pool.size());
        pool.stream().forEach((rna) -> {
//            System.out.println(rna.minimumFreeEnery);
            if(rna.minimumFreeEnery<=cutOffMFE){
                newPool.add(rna);
//                System.out.println(rna.minimumFreeEnery);
            }
        });

        // for now
//        newPool = pool;
    }

    public PriorityQueue<RNA> getNewPool() {
        return newPool;
    }

}
