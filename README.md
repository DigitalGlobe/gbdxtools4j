======================================
gbdxtools4j: Java tools for using GBDX
======================================

gbdxtools4j is a package for ordering imagery and launching workflows on DigitalGlobe's GBDX platform.

In order to use gbdxtools4j, you need GBDX credentials. Email GBDX-Support@digitalglobe.com to get these.

Example scripts can be found under the src/test/java directory of this repo.

See the license file for license rights and limitations (MIT).

**IMPORTANT SAFTEY NOTE**: gbdxtools4j is very much in development right now.  There is much more work to
do but this is a start at getting a Java version of the excellent <a href="https://github.com/DigitalGlobe/gbdxtools">gbdxtools</a>
for the Python world.


## Installation

gbdxtools4j requires Java 7.  Maven 3 is also a prerequsite.  Once you have
that installed you can build gbdxtools4j.

Clone this repository and do a

    mvn clean install

Keep in mind that the master branch is constantly under development. 

## Setup

gbdxtools4j can read a configuration file in your home directory to help with authentication
to the GBDX system.  The file is named ".gbdx-config".  It is a very simple INI style
 file that looks like:
<pre>
    [gbdx]
        client_id = &lt;client id from the GBDX web application&gt;
        client_secret = &lt;client secret from the GBDX web application&gt;
        user_name = &lt;your GBDX user name&gt;
        user_password = &lt;your GBDX password&gt;
</pre>

All of these values come from the GBDX web application.

If the configuration file does not exist then the parameters can also come from
the operating system environment (i.e. <code>setenv</code>) and/or from Java system
parameters (i.e. -D parameters on the Java command line).  Even if the file exists
the parameters in it can be overridden on the command line or with environment variables.
The priority is system property, then environment variable and then the configuration file.

## Simple Examples

<ul>
<li> For workflows, take a look at the <a href="docs/workflow.md">Workflow</a> docs.
<li> For catalog, take a look at the <a href="docs/catalog.md">Catalog</a> docs.
</ul>


## Release
```
brew install gpg
gpg --list-keys
ln -s ./gpg /usr/local/bin/gpg2
export GPG_TTY=$(tty)
```

fix github url in release.properties
```
mvn release:prepare
mvn release:perform
```