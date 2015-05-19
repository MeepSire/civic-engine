from Javadoc:

utils.Part

# Field Detail #

## part ##
public Part[.md](.md) part
The parts the xml file has

## name ##
public java.lang.String name
name of the part

## info ##
public java.lang.String info
info the part contains (value of the part)

# Constructor Detail #

## Part ##
protected Part(java.lang.String name,
> int d)
Constructor

Parameters:
name - name of the part
d - deepness of the part (how many parts are above this one)
## Part ##
protected Part(java.lang.String name,
> java.lang.String info,
> int d)
Constructor

Parameters:
name - name of the part
info - value of the part
d - deepness of the part (how many parts are above this one)

# Method Detail #

## addPart ##
public Part addPart(java.lang.String name)
adds another Part to this one

Parameters:
name - name of the Part to add
Returns:
returns the added Part
## addPart ##
public Part addPart(java.lang.String name,
> java.lang.String info)
adds another Part to this one

Parameters:
name - name of the Part to add
info - value of the Part to add
Returns:
returns the added Part
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
## save ##
public java.lang.String save()
used to create a String from all subParts and this one (usually only called by an EasyXml or another Part)

Returns:
the String created