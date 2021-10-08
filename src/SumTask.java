import java.util.concurrent.RecursiveTask;

//GOES with Driver class
public class SumTask extends RecursiveTask<Integer> {

    private static final int THRESHHOLD = 1000;

    private int begin;
    private int end;
    private int[] array;

    public SumTask(int begin, int end, int[] array){
        this.begin = begin;
        this.end = end;
        this.array = array;
    }

    @Override
    protected Integer compute(){
        if(end - begin < THRESHHOLD){
            int sum = 0;
            for(int i = begin; i <= end; i++){
                sum += array[i];
            }
            return sum;
        }
        else{
            int mid = (begin + end)/2;

            SumTask leftTask = new SumTask(begin, mid, array);
            SumTask rightTask = new SumTask(mid+1, end, array);

            System.out.println("It's about forking time!");
            leftTask.fork();
            rightTask.fork();

            return rightTask.join() + leftTask.join();
        }
    }

}
