from Javadoc:

utils.SimpleFile

# Constructor Detail #

## SimpleFile ##
public SimpleFile()

# Method Detail #

## download ##
public static boolean download(java.lang.String url,
> java.lang.String dest)
> throws java.io.IOException
downloads a file from an internet url to a destination file

Parameters:
url - address of the file you want to download
dest - path to the file you want to save it as
Returns:
returns whether successful
Throws:
java.io.IOException - java.io.BufferedInputStream and java.io.FileOutputStream
## read ##
public static java.lang.String read(java.lang.String url)
reads a textfile and returns the content as a String

Parameters:
url - path to the textfile
Returns:
String containing the content of the file
## save ##
public static boolean save(java.lang.String str,
> java.lang.String url)
saves a textfile

Parameters:
str - the String the textfail will contain
url - the Path to the file you want to save it to
Returns:
returns whether successful
# copy #
public static void copy(java.io.File sourceFile,
> java.io.File destFile)
> throws java.io.IOException
Copies a file to a given location

Parameters:
sourceFile - the File you want to copy
destFile - the File you want it to be copied to
Throws:
java.io.IOException - FileInputStream and FileOutputStream