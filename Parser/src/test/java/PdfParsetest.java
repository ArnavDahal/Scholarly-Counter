import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.sentdetect.*;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.*;
import java.util.Arrays;

// This program will take a location of PDFs, parse them using Apache TIKA, and then use a name finder with a downloaded
// model to find certian tokens
public class PdfParsetest{

    public static PrintStream out;


    // Uses Apache TIKA to extract data from a PDF
    public static String parsePDFS(String filePath, String fileName) throws IOException, TikaException {
        System.out.println("Parsing PDF: " + fileName);

        // This contains all the content from the pdf
        BodyContentHandler pdfContent = new BodyContentHandler();
        // Has the metadata
        Metadata mData = new Metadata();
        // File input, filePath is passed in
        FileInputStream inStream = new FileInputStream(new File(filePath));
        // Call to Apache TIKA to parse it
        ParseContext parserC = new ParseContext();
        PDFParser pdfParser = new PDFParser();
        try {
            pdfParser.parse(inStream, pdfContent, mData, parserC);
        } catch (Exception ex) {
            return null;
        }
        //********/
        // Sends it off to Apache NLP
        // findText(pdfContent.toString(), fileName);
        // Changed to
        return pdfContent.toString();
        //*******//

    }

    // Reads input from the user on where the pdfs are located
    public static String readInput(String in) throws IOException {
       // BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;

          //  System.out.println("Enter the directory where the PDFs are located: ");
        //input = reader.readLine();

        return in;


    }

    // Gets the path for all PDFs in a folder
    public static String getFilePaths(String path) throws IOException {
        try {
            File pdfFolder = new File(path);
            // Holds the list of file paths
            File[] listOfPDFs = pdfFolder.listFiles();

            // Iterates through the folder
            for (int i = 0; i < listOfPDFs.length; i++) {
                if (listOfPDFs[i].isFile()) {
                    // Passes the path of a pdf to the Apache TIKA pdf parser method


                    //parsePDFS(listOfPDFs[i].getPath(), listOfPDFs[i].getName());
                    //*******//
                    // Changed to
                    return (listOfPDFs[i].getPath() + " " + listOfPDFs[i].getName());
                    //******//
                }

            }
        } catch (Exception ex) {
            // If we get a bad path
            System.out.println("ERROR: Bad Path");
            System.exit(0);
        }
        return null;
    }



    // Uses the Apache NLP library to first break the pdf content into strings, then tokens, and then uses name finder
    static public String findText(String inputPDF, String fileName) {

        // Inits the input streams
        InputStream tokenModelBin = null;
        InputStream nameModelBin = null;
        InputStream sentModelBin = null;

        try {
            // Inits the Sentence detector with a pre-downloaded model
            sentModelBin = new FileInputStream("en-sent.bin");
            SentenceModel sModel = new SentenceModel(sentModelBin);
            SentenceDetectorME sDetME = new SentenceDetectorME(sModel);

            // Inits the token finder with a pre-downloaded model
            tokenModelBin = new FileInputStream("en-token.bin");
            TokenizerModel tokModel = new TokenizerModel(tokenModelBin);
            Tokenizer tok = new TokenizerME(tokModel);

            // Inits the namefinder with a pre-downloaded model
            nameModelBin = new FileInputStream("en-ner-organization.bin");
            TokenNameFinderModel TokNameFinderModel = new TokenNameFinderModel(nameModelBin);
            NameFinderME nameFinder = new NameFinderME(TokNameFinderModel);

            // Breaks up the content into sentences
            String sentences[] = sDetME.sentDetect(inputPDF);
            String text;
            // Iterates through all the sentences
            for (String sentence : sentences) {
                String tokens[] = tok.tokenize(sentence); // Finds the tokens
                Span nSpan[] = nameFinder.find(tokens); // Finds names
                String names = Arrays.toString(Span.spansToStrings(nSpan, tokens)); // Changes the names into string
                if (names != "[]" && names.length() < 30) { // Model finder isnt perfect, so cut off at 30 char length names
                    names = names.substring(1, names.length()); // Removes the first [ from the string
                    text = (names + "] " + fileName); // Adds a ] to the end of the string

                    //out.print(text + "\n"); // saves all of it to a text file
                    //******// Changed to
                    return text;
                    //*******//
                }

            }
            // Catches exceltions and closes inputStreams
        } catch (Exception ex) {
        } finally {
            try {
                if (tokenModelBin != null) tokenModelBin.close();
            } catch (IOException e) {
            }
            ;
            try {
                if (nameModelBin != null) nameModelBin.close();
            } catch (IOException e) {
            }
        }
        return null;
    }

}
