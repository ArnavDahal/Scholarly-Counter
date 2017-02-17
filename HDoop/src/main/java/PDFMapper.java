
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// PDF Mapper of the Hadoop MapReduce
public class PDFMapper extends Mapper<Object, Text, Text, Text> {
    @Override
    public void map(Object key, Text value, Context output) throws IOException,
            InterruptedException {

        // Will split the string into two pieces.
        // word[0] will be the token from namefinder
        // Word[1] will be the pdf its refrenced from
        String[] words = value.toString().split("]]");
        output.write(new Text(words[0]), new Text(words[1]));
    }
}
