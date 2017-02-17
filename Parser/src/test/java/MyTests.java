import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import org.hamcrest.CoreMatchers;

public class MyTests {


    // public static String parsePDFS(String filePath, String fileName)
        // returns pdfContent.toString();



    @Test
    // Comes back as the same if it worked, if it crashed the test fails
    public void readInputTest() {
        PdfParsetest tester = new PdfParsetest();
        String in = "ASDAJKLSDJKAS231";

        try {
            assertEquals(in, tester.readInput(in));
        } catch (Exception ex) {
            fail("Input crashed");
        }
    }


    // This should detect Apple is a company
    @Test
    public void findTextTestApple() {
        PdfParsetest tester = new PdfParsetest();

        String fileName = "fileName";
        String input = "Apple";
        String comb = input + "]] " +fileName;

        assertEquals(comb, tester.findText(input, fileName));




    }


    // This should pull out only Microsoft
    @Test
    public void findTextTestMicrosoft() {
        PdfParsetest tester = new PdfParsetest();

        String fileName = "fileName.pdf";
        String input = "I love my new stuff! Microsoft is an amazing company!";
        String comb = "Microsoft" + "]] " +fileName;

        assertEquals(comb, tester.findText(input, fileName));



    }

    // This should return null because if it doesnt find a match
    @Test
    public void findTextTestNull() {
        PdfParsetest tester = new PdfParsetest();

        String fileName = "fileName.pdf";
        String input = "arfarf Im a dog!";

        assertEquals(null, tester.findText(input, fileName));



    }

    // This should enter null if nothing relevant is passed in
    @Test
    public void findTextTestBlank() {
        PdfParsetest tester = new PdfParsetest();

        String fileName = "";
        String input = "";

        assertEquals(null, tester.findText(input, fileName));

    }

    // Should return with the file paths and predetermined pdf names
    @Test
    public void getFilePathsTest()
    {
        try {
            PdfParsetest tester = new PdfParsetest();
            String filePath = "testPdfs";
            String expected = "testPdfs/Micro.pdf Micro.pdf";
            assertEquals(expected, tester.getFilePaths(filePath));
        }

        catch (Exception ex)
        {
            fail("PDF inputs failed");
        }
    }

    //There are no PDFs in the /src/ dir so it should return null
    @Test
    public void getFilePathsTestNull()
    {
        try {
            PdfParsetest tester = new PdfParsetest();
            String filePath = "src";
            String expected = null;
            assertEquals(expected, tester.getFilePaths(filePath));
        }

        catch (Exception ex)
        {
            fail("PDF inputs failed");
        }
    }


    // Should parse the file, the fileName is irrelevant to the logic
    // It should contain the predetermined text
    @Test
    public void parsePDFSTestOne()
    {
        try {
            PdfParsetest tester = new PdfParsetest();
            String filePath = "testPdfs/Test1.pdf";
            String fileName = "djsajkds";
            String expected = "Test1";
            String x = tester.parsePDFS(filePath,fileName);
            assertThat(x, CoreMatchers.containsString(expected));

        }

        catch (Exception ex)
        {
            fail("PDF parse failed");
        }
    }

    // Should parse the file, the fileName is irrelevant to the logic
    @Test
    public void parsePDFSTestTwo()
    {
        try {
            PdfParsetest tester = new PdfParsetest();
            String filePath = "testPdfs/Test2.pdf";
            String fileName = null;
            String expected = "Test2";
            String x = tester.parsePDFS(filePath,fileName);
            assertThat(x, CoreMatchers.containsString(expected));


        }

        catch (Exception ex)
        {
            fail("PDF parse failed");
        }
    }

    // Tests the 3 main methods
    @Test
    public void intergration()
    {

        PdfParsetest tester = new PdfParsetest();

        try {

            String filePath = "testPdfs";
            String fileName = "Micro.pdf";
            String temp = tester.getFilePaths(filePath);
            temp = temp.substring(0, temp.indexOf(" "));

            String x = tester.parsePDFS(temp,fileName);

            String comb = "Microsoft" + "]] " +fileName;

            assertEquals(comb, tester.findText(x, fileName));
        }

        catch (Exception ex)
        {
            fail("PDF inputs failed");
        }

    }
}