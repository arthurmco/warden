# warden

WebDAV server, own/nextCloud server like, written in Clojure.

The objective is to make a server up and running with minimal
configuration. You just point where to read, the valid users and run
the server.

For now, it will offer no graphical or web interface, but it might in
the future, if there is demand for that.

## Objectives

 - full WebDAV support
 - maybe LDAP support, for user authentication?
 - encryption
 - the server will run in all major OSs


## Usage

    $ java -jar warden-0.1.0-standalone.jar [args]

## Structures

Read https://tools.ietf.org/html/rfc4918

- User
- Directory
- File
- Permissions

A **file** is a single unit. It can have **data**. It has a
**size**. It might or might not have **locks**. It is called a
_collection member_ in the RFC.

A **directory** is a container for **files**. A directory is a type
of file that can contain multiple files. Thus, it can have every
property a file has. It is called a _collection_ in the RFC.

An **user** is someone who access a **file**. It has **permissions**
for a file. A file can be accessed, or cannot be, depending of the
**permissions**.

Files with a **lock** cannot be modified by anyone, only read. A lock
has an owner, who is an **user**. Each lock is identified by a token,
and has a lifetime. Locks can be refreshed.

--------

WebDAV is a XML-based protocol

## Planned Support

Lock support will not be enforced on first builds, but it will be
 later (see the RFC 4918, section 7.2) 
 
 > WebDAV servers that support locking can reduce the likelihood that
 > clients will accidentally overwrite each other's changes by requiring
 > clients to lock resources before modifying them.  Such servers would
 > effectively prevent HTTP 1.0 and HTTP 1.1 clients from modifying
 > resources.

