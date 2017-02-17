import java.io.IOException;
import java.util.Iterator;
import org.apache.hadoop.io.IntWritable;
import java.util.HashSet;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

// The reducer for the Hadoop MapReduce
public class PDFReducer extends Reducer<Text, Text, Text, String> {

    @Override
    public void reduce(Text key, Iterable<Text> values, Context output)
            throws IOException, InterruptedException {
        // Hash set so duplicates cant be added
     HashSet<String> wordSet = new HashSet<String>();
        // This loop adds all the pdf refrences to a HashSet
        for(Text value: values){    
        wordSet.add(value.toString());
        }
        // Output for the Reducer
        output.write(key, "\n" + wordSet);
    }
}