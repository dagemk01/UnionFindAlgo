import java.lang.Math; 
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    // Instance Variables
    double[] data;
    int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){

        if (validate(n, trials)){
            data = new double [trials];
            this.trials = trials;
            for (int i = 0; i < trials; i ++){
                //Current instace of the percolation object
                Percolation inst = new Percolation(n);
                int count = 0;
                while (inst.percolates() == false){
                    int row = StdRandom.uniform(1, n+1);
                    int col = StdRandom.uniform(1, n+1);

                    inst.open(row, col);
                    if(count % 10 == 0){
                    //System.out.println(""+count+": Processing Please Wait openning ("+row+", "+col+")");
                    }
                    count ++;
                }
                System.out.println("finmished: "+ i);
                double total = n*n;
                data[i] = inst.numberOfOpenSites()/total;
                System.out.println(inst.numberOfOpenSites()/(total));

            }
        }
    }

    // sample mean of percolation threshold
    public double mean(){
        double mean = StdStats.mean(this.data);
        return mean;

    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        double stdev = StdStats.stddev(this.data);
        return stdev;

    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        double x = this.mean();
        double t = Math.sqrt(trials);
        double st = Math.sqrt(this.stddev());
        double coLe = x - (1.96*st/t);
        return coLe;

    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        double x = this.mean();
        double t = Math.sqrt(trials);
        double st = Math.sqrt(this.stddev());
        double coHi = x + (1.96*st/t);
        return coHi;

    }

    private boolean validate(int n, int trial){
        if(n > 0 && trial > 0){
            return true;
        }
        throw new IllegalArgumentException("Cannot be innitialized to " + n + " or " + trial);
        
    }
   // test client (see below)
   public static void main(String[] args){
       int n = Integer.parseInt(args[0]);
       int trials = Integer.parseInt(args[1]);
       System.out.println(n + ", " + trials);
       PercolationStats test = new PercolationStats(n, trials);
       

       System.out.println("Mean:                    = " + test.mean());
       System.out.println("sttdev:                  = " + test.stddev());
       System.out.println("95% confidence interval  = [" + test.confidenceLo() + ", " + test.confidenceHi() + "]");
      
   }

}