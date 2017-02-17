import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;


// Main class of the Hadoop MapReduce
public class PDFHadooper extends Configured implements Tool{

    // Main
    public static void main(String[] args) throws Exception {
        int res = ToolRunner.run(new Configuration(), new PDFHadooper(), args);
        System.exit(res);       
    }
// Runs the Hadoop Job
    @Override
    public int run(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Please put in [input] [output] as parameters");
            System.exit(-1);
        }

        // Sets the output types
        Job job = Job.getInstance(new Configuration());
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        // Sets the Mapper and Reducer classes
        job.setMapperClass(PDFMapper.class);
        job.setReducerClass(PDFReducer.class);

        // Sets the inputs and output types (all text)
        job.setInputFormatClass(TextInputFormat.class);
        job.setOutputFormatClass(TextOutputFormat.class);

        // Gets the paths from parameters passed in through the command line
        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setJarByClass(PDFHadooper.class);

        job.submit();
        return 0;
    }
}
