# cdap-code-page-convert

This project is a CDAP User Defined Directive (UDD) that provides conversion from character data that
is encoded in a specific format to String data.

As an example, consider data that is contained in a file in EBCDIC format.  We want to process this
data as text.  If we try and simply use CDAP to read as text we will fail as it will attempt to read
as UTF8 (which it isn't).  What we want to do is read the data as blob (binary) and then ask to 
convert that data to a String representation.

The new directive is:

```
code-page-convert :source-column :target-column "sourceCodePage"
```

an example of use would be:

```
code-page-convert :body :myText "Cp1047"
```