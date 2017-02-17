Information:
	This program will take in a search term and parse pdfs files relating to that term, returning certian name tokens.
	For this program I used the pre-made models found on Apache NLP's model download page.
	The ones I used were:
		Namefinder: 		en-ner-organization.bin
		Sentence Detector: 	en-sent.bin
		Token detector: 	en-token.bin
	The output of the program will be in a file labeled part-r-00000.
	The structure of that file is that of the Name of the token found, followed by a set of PDFs they were found in.
		Ex.
			Word
			[wordmagazine.pdf, uic_weekly.pdf]
			Word 2
			[wowzers.pdf]

Structure:
	The structure of this program consists of 3 Major parts:
		Part 1: Downloading the PDFs (Using python and bash)
		Part 2: Parsing the PDFs using Apache Tika and NLP in Java (Running the Parser program )
		Part 3: Using Hadoop MapReduce to get a list of tokens found in the files.

How to run:
	Requirements:
		Java 6 
		Simple Build Tools 
		Apache HADOOP
		Apache Tika
		Apache NLP
		Python
	Step 0:
		First chmod the main directory:
			> chmod 777 *
	Step 1: DOWNLOADING THE PDFS
		Running the PDF downloader:
			The pdf downloader is a python script that interacts with ArXiv.org's API
			Use:
				python pdfSearch.py CONTENT RESULTS
				python pdfSearch.py CONTENT
				python pdfSearch.py
			Where CONTENT is the search term you want to use and RESULTS is the max amount of PDFs results to return
			If RESULTS or both RESULTS and CONTENT are left blank, they will default to: CONTENT = Computer Science and RESULTS = 10
			The PDFs are downloaded to /Parser/pdfs
	
	Step 2: PARSING THE PDFS
		Running the PDF parser through SBT
			The program is created in java using SBT
			To start the cleaneast way is to put the following in the console:
				> cd Parser
				> sbt clean
				> sbt compile
				> sbt run
			You should now recieve a dialog box that asks for input on where the pdfs are.
			If you are using the default location set by the python script, you can just enter
				> pdfs
			You will have to wait for all the PDFs to finish parsing.
	
	Step 3: RUNNING HADOOP
		We will now use HADOOP along with a MapReduce program to get our end results.
			Use the bash script runHadoop.sh to automatically do it for you. Go back to the top directory.
				> ./runHadoop.sh
			If you run into errors or would like to do it manually I will list the steps here.
			If not SKIP to Step 4.
		Manual: 
			Make your directory in HDFS
				> hdfs dfs -mkdir <DIRECTORY> 
					ex. hdfs dfs -mkdir /user/hadoop/adahal3
			Place the folder for hadoop to run on into the folder above
				> hdfs dfs -put <LOCAL FILE> <HDFS FOLDER>
					ex. hdfs dfs -put Parser/tokens.txt /user/hadoop/adahal3/input
			Compile and Package the MapReduce SBT project
				> cd HDoop; sbt package;
					* If this gives you an error run
						> cd HDoop; sbt clean; sbt compile; sbt package;
			Run Hadoop on the JAR
				> cd target; hadoop jar hparser-0.1.jar  /user/hadoop/adahal3/input  /user/hadoop/adahal3/output
				
	Step 4: RETRIEVING RESULTS
		* Note: It may take some time for Hadoop to finish
			To check if its done
				>  hdfs dfs -ls /user/hadoop/adahal3/output
			Make sure there is a file in there that is named SUCCESS
		We will now retrieve our results from HDFS using the following command:
			> hdfs dfs -get /user/hadoop/adahal3/output
		This will create a folder called "output"
			Go into it and and open the part-r-00000 file
			
	* If you can't run something, make sure to chmod 777 it.	
	

Limitations:
	- The programs have to be run seperately due to them having to finish their operations.
	- It will ONLY parse PDF files and not PPT files
	- The predefined models aren't perfect so I had to cut off any names found that were longer than 30 characters,
		as sometimes NLP would give me a name that was a bunch of garble and very long.
			

Tests:
	Parser Testing: 
		(9 Unit Tests, 1 Intergration Test) 
			The intergration test, tests all 3 of my major methods; The finding of PDFs in a file location, the parsing using Apache Tika, and the nameFinding using ApacheNLP
		To run tests type in the command line
			> sbt test
		It will run tests on a copy of the source file (My orginal program did not have any returns only calls to other functions)
		The changed copy just has the functions returning the parameters (they are combined into a string if there are multiple) that would have been passed into the next function.
		If you would like to see the changes made, go into /src/test/java and open PdfParsetest.java. The return value changes are indicated by a /****/ Comment and the word: changed as a comment
		In total I commented out 4 method calls and replaced them with return statements to return the paramters being passed into the next function.
		In addition I had to add returns to the end of each function as I changed the function types from void to String. None of the core logic was changed.
		My tests are written in jUnit mainly with Assertations and state changes. 
		I have also created 2 pdfs that are placed in the testPdfs folder.
		
	Hadoop Testing:
		I do not have test cases for Hadoop as I do not see a way of testing the reduce and map functions that wouldnt already be provided by running it on an input.
	PDFDownloader:
		The PDF downloader is pretty straight forward.	
		Just running it, and if something downloads thats a pdf, it works.

