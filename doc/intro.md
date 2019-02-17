# Introduction to warden

TODO: write [great documentation](http://jacobian.org/writing/what-to-write/)


# WebDAV annotations

Messages finish with two `\r\n`s, like in http

(WebDAV RFC, section 3)

 - Collections are resources that act as a list of resouces. Like a
   directory.
 - Internal members are children of collections.
 - Members are descendants of a collection
 - Properties: attributes of a resouce
  - Live Property: have a meaning for the server
  - Dead Property: does not have a meaning for the server, it only
    stores them.

## Initial handshake

C:
```
OPTIONS / HTTP/1.1
Connection: Keep-Alive
User-Agent: Microsoft-WebDAV-MiniRedir/10.0.17134
translate: f
Host: localhost:1900
```

S:
```
HTTP/1.1 200 OK
DAV: 1,2
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true
Access-Control-Expose-Headers: DAV, content-length, Allow
MS-Author-Via: DAV
Server: webdav-server/2.4.6
WWW-Authenticate: Digest realm="default realm", qop="auth", nonce="a33784404909d0283f0047f043ac041f", opaque="1c61398875bb6b58eeee22e47e5fcce0"
Allow: PROPPATCH,PROPFIND,OPTIONS,DELETE,UNLOCK,COPY,LOCK,MOVE
Date: Sat, 16 Feb 2019 18:08:36 GMT
Connection: keep-alive
Content-Length: 0


```

Then, the client queries the path specified
(if URL is http://localhost:9000/, the path is '/', if it is 
http://localhost:9000/root/, the path is '/root')

```
PROPFIND / HTTP/1.1
Connection: Keep-Alive
User-Agent: Microsoft-WebDAV-MiniRedir/10.0.17134
Depth: 0
translate: f
Content-Length: 0
Host: localhost:1900


```

```
HTTP/1.1 207 Multi-Status
DAV: 1,2
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true
Access-Control-Expose-Headers: DAV, content-length, Allow
MS-Author-Via: DAV
Server: webdav-server/2.4.6
WWW-Authenticate: Digest realm="default realm", qop="auth", nonce="0dedd9b40909922f01f7ef3ddfd4d3cd", opaque="4c244428d30a330b822df923f0ca552b"
Allow: PROPPATCH,PROPFIND,OPTIONS,DELETE,UNLOCK,COPY,LOCK,MOVE
Content-Type: application/xml;charset=utf-8
Content-Length: 744
Date: Sat, 16 Feb 2019 18:08:36 GMT
Connection: keep-alive
```

```xml
<?xml version="1.0" encoding="utf-8"?>
<D:multistatus xmlns:D="DAV:">
  <D:response>
    <D:href>http://localhost:1900/</D:href>
    <D:propstat>
      <D:status>HTTP/1.1 200 OK</D:status>
      <D:prop>
	<D:getlastmodified>Sat, 16 Feb 2019 18:08:21 GMT</D:getlastmodified>
	<D:lockdiscovery/>
	<D:supportedlock>
	  <D:lockentry>
	    <D:lockscope>
	      <D:exclusive/>
	    </D:lockscope>
	    <D:locktype>
	      <D:write/>
	    </D:locktype>
	  </D:lockentry>
	  <D:lockentry>
	    <D:lockscope>
	      <D:shared/>
	    </D:lockscope>
	    <D:locktype>
	      <D:write/>
	    </D:locktype>
	  </D:lockentry>
	</D:supportedlock>
	<D:creationdate>2019-02-16T18:08:21+02:00</D:creationdate>
	<D:resourcetype>
	  <D:collection/>
	</D:resourcetype>
	<D:displayname>
	</D:displayname>
	<D:getetag>"49607c43df64cc6ca7e2f4ed025177e3"</D:getetag>
      </D:prop>
    </D:propstat>
  </D:response>
</D:multistatus>
```
	
## PROPFIND

Query an object property. An object can be either directory or file.

Can be used to query the object itself

depth is how deep in the hierarchy you can go.
0 returns only the folder, 1 returns the folder and its files, 2
returns the folder, its children and grandchildren, and so on...


```
PROPFIND /AutoRun.inf HTTP/1.1
Connection: Keep-Alive
User-Agent: Microsoft-WebDAV-MiniRedir/10.0.17134
Depth: <depth>
translate: f
Content-Length: 0
Host: localhost:1900
```

(if not found)
```
HTTP/1.1 404 Not Found
DAV: 1,2
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true
Access-Control-Expose-Headers: DAV, content-length, Allow
MS-Author-Via: DAV
Server: webdav-server/2.4.6
WWW-Authenticate: Digest realm="default realm", qop="auth", nonce="a818853df6400110b993c2042cf8c77e", opaque="ab1f443ec794a72d1e6b4f82f9e8b30a"
Allow: OPTIONS,MKCOL,LOCK,POST,PUT
Date: Sat, 16 Feb 2019 18:08:37 GMT
Connection: keep-alive
Content-Length: 0
```

(if found, file)
```
HTTP/1.1 207 Multi-Status
DAV: 1,2
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true
Access-Control-Expose-Headers: DAV, content-length, Allow
MS-Author-Via: DAV
Server: webdav-server/2.4.6
WWW-Authenticate: Digest realm="default realm", qop="auth", nonce="78e9b0ea05687a7208577f07da53997e", opaque="837d5ec9642181a957a371580d2a31bd"
Allow: PROPPATCH,PROPFIND,OPTIONS,DELETE,UNLOCK,COPY,LOCK,MOVE,HEAD,POST,PUT,GET
Content-Type: application/xml;charset=utf-8
Content-Length: 1314
Date: Sat, 16 Feb 2019 18:24:58 GMT
Connection: keep-alive

<?xml version="1.0" encoding="utf-8"?>
<D:multistatus xmlns:D="DAV:">
  <D:response>
    <D:href>http://localhost:1900/Novo%20Documento%20de%20Texto.txt</D:href>
    <D:propstat>
      <D:status>HTTP/1.1 200 OK</D:status>
      <D:prop>
	<D:getlastmodified>Sat, 16 Feb 2019 18:24:58 GMT</D:getlastmodified>
	<D:lockdiscovery/>
	<D:supportedlock>
	  <D:lockentry>
	    <D:lockscope>
	      <D:exclusive/>
	    </D:lockscope>
	    <D:locktype>
	      <D:write/>
	    </D:locktype>
	  </D:lockentry>
	  <D:lockentry>
	    <D:lockscope>
	      <D:shared/>
	    </D:lockscope>
	    <D:locktype>
	      <D:write/>
	    </D:locktype>
	  </D:lockentry>
	</D:supportedlock>
	<D:creationdate>2019-02-16T18:24:58+02:00</D:creationdate>
	<D:resourcetype/>
	<D:displayname>Novo%20Documento%20de%20Texto.txt</D:displayname>
	<D:getetag>"5bea8bd2a0f23b80873f78ee453d840b"</D:getetag>
	<D:getcontentlength>0</D:getcontentlength>
	<D:getcontenttype>text/plain; charset=utf-8</D:getcontenttype>
	<a:Win32CreationTime xmlns:a="urn:schemas-microsoft-com:">Sat, 16 Feb 2019 18:24:58 GMT</a:Win32CreationTime>
	<a:Win32LastAccessTime xmlns:a="urn:schemas-microsoft-com:">Sat, 16 Feb 2019 18:24:58 GMT</a:Win32LastAccessTime>
	<a:Win32LastModifiedTime xmlns:a="urn:schemas-microsoft-com:">Sat, 16 Feb 2019 18:24:58 GMT</a:Win32LastModifiedTime>
	<a:Win32FileAttributes xmlns:a="urn:schemas-microsoft-com:">00000020</a:Win32FileAttributes>
      </D:prop>
    </D:propstat>
  </D:response>
</D:multistatus>
```

(if found, directory)

```
HTTP/1.1 207 Multi-Status
DAV: 1,2
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true
Access-Control-Expose-Headers: DAV, content-length, Allow
MS-Author-Via: DAV
Server: webdav-server/2.4.6
WWW-Authenticate: Digest realm="default realm", qop="auth", nonce="1acad802b7321da79377709cf94f89c0", opaque="578ac86749b9ef4a73fd73ee693985e3"
Allow: PROPPATCH,PROPFIND,OPTIONS,DELETE,UNLOCK,COPY,LOCK,MOVE
Content-Type: application/xml;charset=utf-8
Content-Length: 1974
Date: Sat, 16 Feb 2019 18:25:01 GMT
Connection: keep-alive

<?xml version="1.0" encoding="utf-8"?>
<D:multistatus xmlns:D="DAV:">
  <D:response>
    <D:href>http://localhost:1900/</D:href>
    <D:propstat>
      <D:status>HTTP/1.1 200 OK</D:status>
      <D:prop>
	<D:getlastmodified>Sat, 16 Feb 2019 18:08:21 GMT</D:getlastmodified>
	<D:lockdiscovery/>
	<D:supportedlock>
	  <D:lockentry>
	    <D:lockscope>
	      <D:exclusive/>
	    </D:lockscope>
	    <D:locktype>
	      <D:write/>
	    </D:locktype>
	  </D:lockentry>
	  <D:lockentry>
	    <D:lockscope>
	      <D:shared/>
	    </D:lockscope>
	    <D:locktype>
	      <D:write/>
	    </D:locktype>
	  </D:lockentry>
	</D:supportedlock>
	<D:creationdate>2019-02-16T18:08:21+02:00</D:creationdate>
	<D:resourcetype>
	  <D:collection/>
	</D:resourcetype>
	<D:displayname>
	</D:displayname>
	<D:getetag>"49607c43df64cc6ca7e2f4ed025177e3"</D:getetag>
      </D:prop>
    </D:propstat>
  </D:response>
  <D:response>
    <D:href>http://localhost:1900/Novo%20Documento%20de%20Texto.txt</D:href>
    <D:propstat>
      <D:status>HTTP/1.1 200 OK</D:status>
      <D:prop><D:getlastmodified>Sat, 16 Feb 2019 18:24:58 GMT</D:getlastmodified>
      <D:lockdiscovery/>
      <D:supportedlock>
	<D:lockentry>
	  <D:lockscope>
	    <D:exclusive/>
	  </D:lockscope>
	  <D:locktype>
	    <D:write/>
	  </D:locktype>
	</D:lockentry>
	<D:lockentry>
	  <D:lockscope>
	    <D:shared/>
	  </D:lockscope>
	  <D:locktype>
	    <D:write/>
	  </D:locktype>
	</D:lockentry>
      </D:supportedlock>
      <D:creationdate>2019-02-16T18:24:58+02:00</D:creationdate>
      <D:resourcetype/>
      <D:displayname>Novo%20Documento%20de%20Texto.txt</D:displayname>
      <D:getetag>"5bea8bd2a0f23b80873f78ee453d840b"</D:getetag>
      <D:getcontentlength>0</D:getcontentlength>
      <D:getcontenttype>text/plain; charset=utf-8</D:getcontenttype>
      <a:Win32CreationTime xmlns:a="urn:schemas-microsoft-com:">Sat, 16 Feb 2019 18:24:58 GMT</a:Win32CreationTime>
      <a:Win32LastAccessTime xmlns:a="urn:schemas-microsoft-com:">Sat, 16 Feb 2019 18:24:58 GMT</a:Win32LastAccessTime>
      <a:Win32LastModifiedTime xmlns:a="urn:schemas-microsoft-com:">Sat, 16 Feb 2019 18:24:58 GMT</a:Win32LastModifiedTime>
      <a:Win32FileAttributes xmlns:a="urn:schemas-microsoft-com:">00000020</a:Win32FileAttributes>
      </D:prop>
    </D:propstat>
  </D:response>
</D:multistatus>
```

## PUT

Create a resource

C:
```
PUT /Novo%20Documento%20de%20Texto.txt HTTP/1.1
Connection: Keep-Alive
User-Agent: Microsoft-WebDAV-MiniRedir/10.0.17134
translate: f
Content-Length: <content-size>
Host: localhost:1900

<content-data>
```

S:
```
HTTP/1.1 201 Created
DAV: 1,2
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true
Access-Control-Expose-Headers: DAV, content-length, Allow
MS-Author-Via: DAV
Server: webdav-server/2.4.6
WWW-Authenticate: Digest realm="default realm", qop="auth", nonce="fbddabd03ae12d6676c3df068bf20351", opaque="cc6f99ef654d5bb2435842a90808c91c"
Allow: OPTIONS,MKCOL,LOCK,POST,PUT
Date: Sat, 16 Feb 2019 18:24:58 GMT
Connection: keep-alive
Content-Length: 0


```

## LOCK

Locks a resource. Does not allow modifications to it

C:
```
LOCK /Novo%20Documento%20de%20Texto.txt HTTP/1.1
Cache-Control: no-cache
Connection: Keep-Alive
Pragma: no-cache
Content-Type: text/xml; charset="utf-8"
User-Agent: Microsoft-WebDAV-MiniRedir/10.0.17134
translate: f
Timeout: Second-3600
Content-Length: 204
Host: localhost:1900


<?xml version="1.0" encoding="utf-8" ?>
<D:lockinfo xmlns:D="DAV:">
  <D:lockscope>
    <D:exclusive/>
  </D:lockscope>
  <D:locktype>
    <D:write/>
  </D:locktype>
  <D:owner>
    <D:href>HOST\user</D:href>
  </D:owner>
</D:lockinfo>


```

S:
```
輅玪-�2�6糜mP蹾TTP/1.1 200 OK
DAV: 1,2
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true
Access-Control-Expose-Headers: DAV, content-length, Allow
MS-Author-Via: DAV
Server: webdav-server/2.4.6
WWW-Authenticate: Digest realm="default realm", qop="auth", nonce="44abaa7e5fdc14e91feb6407a2de0372", opaque="1a86bf14a82254fb92a51e8a7d7ca38a"
Allow: PROPPATCH,PROPFIND,OPTIONS,DELETE,UNLOCK,COPY,LOCK,MOVE,HEAD,POST,PUT,GET
Lock-Token: urn:uuid:083b2983-d67d-17c4-003a-0000da197fec
Content-Type: application/xml;charset=utf-8
Content-Length: 523
Date: Sat, 16 Feb 2019 18:24:58 GMT
Connection: keep-alive

<?xml version="1.0" encoding="utf-8"?>
<D:prop xmlns:D="DAV:">
  <D:lockdiscovery>
    <D:activelock>
      <D:locktype>
	<write/>
      </D:locktype>
      <D:lockscope>
	<exclusive/>
      </D:lockscope>
      <D:locktoken>
	<D:href>urn:uuid:083b2983-d67d-17c4-003a-0000da197fec</D:href>
      </D:locktoken>
      <D:lockroot>
	<D:href>http://localhost:1900/Novo%2520Documento%2520de%2520Texto.txt</D:href>
      </D:lockroot>
      <D:depth>infinity</D:depth>
      <D:owner>
	<a:href xmlns:a="DAV:">HOST\user</a:href>
      </D:owner>
      <D:timeout>Second-3600</D:timeout>
    </D:activelock>
  </D:lockdiscovery>
</D:prop>
```

## PROPPATCH

Change properties

C:
```
PROPPATCH /Novo%20Documento%20de%20Texto.txt HTTP/1.1
Cache-Control: no-cache
Connection: Keep-Alive
Pragma: no-cache
Content-Type: text/xml; charset="utf-8"
User-Agent: Microsoft-WebDAV-MiniRedir/10.0.17134
If: (<urn:uuid:083b2983-d67d-17c4-003a-0000da197fec>)
translate: f
Content-Length: 443
Host: localhost:1900

<?xml version="1.0" encoding="utf-8" ?>
<D:propertyupdate xmlns:D="DAV:" xmlns:Z="urn:schemas-microsoft-com:">
  <D:set>
    <D:prop>
      <Z:Win32CreationTime>Sat, 16 Feb 2019 18:24:58 GMT</Z:Win32CreationTime>
      <Z:Win32LastAccessTime>Sat, 16 Feb 2019 18:24:58 GMT</Z:Win32LastAccessTime>
      <Z:Win32LastModifiedTime>Sat, 16 Feb 2019 18:24:58 GMT</Z:Win32LastModifiedTime>
      <Z:Win32FileAttributes>00000020</Z:Win32FileAttributes>
    </D:prop>
  </D:set>
</D:propertyupdate>
```

S:
```
HTTP/1.1 207 Multi-Status
DAV: 1,2
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true
Access-Control-Expose-Headers: DAV, content-length, Allow
MS-Author-Via: DAV
Server: webdav-server/2.4.6
WWW-Authenticate: Digest realm="default realm", qop="auth", nonce="40f52a0fdf15872c74e01fbe0869d6e7", opaque="77fe27b511e05d73cc7546590050f666"
Allow: PROPPATCH,PROPFIND,OPTIONS,DELETE,UNLOCK,COPY,LOCK,MOVE,HEAD,POST,PUT,GET
Content-Type: application/xml;charset=utf-8
Content-Length: 509
Date: Sat, 16 Feb 2019 18:24:58 GMT
Connection: keep-alive


<?xml version="1.0" encoding="utf-8"?>
<D:multistatus xmlns:D="DAV:">
  <D:response>
    <D:href>http://localhost:1900/Novo%2520Documento%2520de%2520Texto.txt</D:href>
    <D:propstat>
      <D:prop>
	<a:Win32CreationTime xmlns:a="urn:schemas-microsoft-com:"/>
	<a:Win32LastAccessTime xmlns:a="urn:schemas-microsoft-com:"/>
	<a:Win32LastModifiedTime xmlns:a="urn:schemas-microsoft-com:"/>
	<a:Win32FileAttributes xmlns:a="urn:schemas-microsoft-com:"/>
      </D:prop>
      <D:status>HTTP/1.1 200 OK</D:status>
    </D:propstat>
  </D:response>
</D:multistatus>
```

## UNLOCK

Removes a lock

```
UNLOCK /Novo%20Documento%20de%20Texto.txt HTTP/1.1
Cache-Control: no-cache
Connection: Keep-Alive
Pragma: no-cache
User-Agent: Microsoft-WebDAV-MiniRedir/10.0.17134
translate: f
Lock-Token: <urn:uuid:083b2983-d67d-17c4-003a-0000da197fec>
Host: localhost:1900


```

```
HTTP/1.1 204 No Content
DAV: 1,2
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true
Access-Control-Expose-Headers: DAV, content-length, Allow
MS-Author-Via: DAV
Server: webdav-server/2.4.6
WWW-Authenticate: Digest realm="default realm", qop="auth", nonce="c52efb800b32e2ac35387c7681da8757", opaque="cbdd048321115bbb361f54efec1c1158"
Allow: PROPPATCH,PROPFIND,OPTIONS,DELETE,UNLOCK,COPY,LOCK,MOVE,HEAD,POST,PUT,GET
Lock-Token: <urn:uuid:083b2983-d67d-17c4-003a-0000da197fec>
Date: Sat, 16 Feb 2019 18:24:58 GMT
Connection: keep-alive


```

## MOVE

Moves a resouce

```
MOVE /Novo%20Documento%20de%20Texto.txt HTTP/1.1
Connection: Keep-Alive
User-Agent: Microsoft-WebDAV-MiniRedir/10.0.17134
Destination: http://localhost:1900/test.txt
Overwrite: F
translate: f
Content-Length: 0
Host: localhost:1900


```

```
HTTP/1.1 201 Created
DAV: 1,2
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true
Access-Control-Expose-Headers: DAV, content-length, Allow
MS-Author-Via: DAV
Server: webdav-server/2.4.6
WWW-Authenticate: Digest realm="default realm", qop="auth", nonce="4b24cd5cea6e3b07b0d217ca3add42b3", opaque="e46b66857a9962dd281020f5b8ba6aaa"
Allow: PROPPATCH,PROPFIND,OPTIONS,DELETE,UNLOCK,COPY,LOCK,MOVE,HEAD,POST,PUT,GET
Date: Sat, 16 Feb 2019 18:25:01 GMT
Connection: keep-alive
Content-Length: 0


```

## MKCOL

Creates a collection, aka folder

```
MKCOL /Nova%20pasta HTTP/1.1
Connection: Keep-Alive
User-Agent: Microsoft-WebDAV-MiniRedir/10.0.17134
translate: f
Content-Length: 0
Host: localhost:1900


```

```
HTTP/1.1 201 Created
DAV: 1,2
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true
Access-Control-Expose-Headers: DAV, content-length, Allow
MS-Author-Via: DAV
Server: webdav-server/2.4.6
WWW-Authenticate: Digest realm="default realm", qop="auth", nonce="692ce6029e120c0a8434fd1b494fbca8", opaque="78d8c87acdf784edc4a5d1a89def919f"
Allow: OPTIONS,MKCOL,LOCK,POST,PUT
Date: Sat, 16 Feb 2019 19:04:28 GMT
Connection: keep-alive
Content-Length: 0


```

## DELETE

Removes a resource

```
DELETE /test.txt HTTP/1.1
Connection: Keep-Alive
User-Agent: Microsoft-WebDAV-MiniRedir/10.0.17134
translate: f
Host: localhost:1900


```

```
HTTP/1.1 200 OK
DAV: 1,2
Access-Control-Allow-Origin: *
Access-Control-Allow-Credentials: true
Access-Control-Expose-Headers: DAV, content-length, Allow
MS-Author-Via: DAV
Server: webdav-server/2.4.6
WWW-Authenticate: Digest realm="default realm", qop="auth", nonce="760891024ec82391191eda77041cf331", opaque="5e066646106346a5a8226140bc5d28ee"
Allow: PROPPATCH,PROPFIND,OPTIONS,DELETE,UNLOCK,COPY,LOCK,MOVE,HEAD,POST,PUT,GET
Date: Sat, 16 Feb 2019 19:07:50 GMT
Connection: keep-alive
Content-Length: 0


```


# Test the following
 - How does it behave on errors?
 - Messages for authenticated users
