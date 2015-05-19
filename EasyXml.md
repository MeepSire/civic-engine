from Javadoc:

utils.EasyXml

# Field Detail #

## part ##
public Part[.md](.md) part
The parts the xml file has

## parts ##
protected int parts
number of parts the xml file has

## url ##
public java.lang.String url
url to the file the xml is saved in

## name ##
public java.lang.String name
name of the xml (not the file)

# Constructor Detail #

## EasyXml ##
public EasyXml(java.lang.String name)
Creates an xml to which parts can be added

Parameters:
name - name of the xml (not the file)

# Method Detail #

## find ##
public java.lang.String[.md](.md) find(java.lang.String[.md](.md) node)
finds all values of a given path in your EasyXml

Parameters:
node - array of nodes(Strings) that act like a path to the item you want to find (eg: new String[.md](.md){"person","age"} as node finds all items under ..person..age)
Returns:
returns an array of all found items(as Strings) with the given node path
## find ##
public static java.lang.String[.md](.md) find(java.io.File file,
> java.lang.String[.md](.md) node)
finds all values of a given path of an xml file

Parameters:
file - url of the xml file
node - array of nodes(Strings) that act like a path to the item you want to find (eg: new String[.md](.md){"person","age"} as node finds all items under [...]person[...]age)
Returns:
returns an array of all found items(as Strings) with the given node path
## save ##
public boolean save(java.lang.String url)
Saves your xml

Parameters:
url - path where to save the xml (eg "/directory/xmlFile.xml")
Returns:
returns whether successful
## addPart ##
public Part addPart(java.lang.String name)
Adds a part to your xml with a given name

Parameters:
name - the name of the part
Returns:
the added Part is returned and may be used for further adding parts
## addPart ##
public Part addPart(java.lang.String name,
> java.lang.String info)
Adds a final part to your xml with a given name and value

Parameters:
name - the name of the part
info - the value of the part
Returns:
the added Part is returned but shouldnt be used for further adding parts
## remPart ##
public boolean remPart(int i)
removes a part from the xml

Parameters:
i - number of the part to remove (call getParts() to know which one to remove)
Returns:
return whether successful
## getParts ##
public java.lang.String[.md](.md) getParts()
lists all the Parts added in a String array (without values)

Returns:
String array of names of all parts added