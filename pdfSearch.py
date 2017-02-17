import urllib
import re
import subprocess
import sys

# Gets the command line inputs
try:
	searchterm = sys.argv[1]
except:
	searchterm = 'computer science'

# Sets defaults if no inputs
try:
	results = sys.argv[2]
except:
	results = '10'

print "Searching for files"

# Uses ArXiv's API to get pdf  results
url = 'http://export.arxiv.org/api/query?search_query=all:'+searchterm+'&start=0&max_results=' + results
data = urllib.urlopen(url).read()

# Uses a regex to get the pdf locations
r = re.compile('(?<=href=").*?(?=" rel="related" type="application/pdf)')
m = r.findall(data)

print "Starting download"

# Calls a bash script that spoofs a browser to get around anti-bots/anti-crawlers so we can download the pdfs
for i in m:
  subprocess.call("./downloadPDF.sh " + i +'.pdf', shell=True)

print "Download Finished"

