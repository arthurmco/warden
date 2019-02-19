(ns warden.filesystem
  (:gen-class))


(defn get-file-raw-metadata
  "From a string path, get file raw metadata, in form of a
  java.nio.file.attribute.BasicFileAttributes

  After this, the metadata will be transformed in a hash"
  [path]
  (java.nio.file.Files/readAttributes
   (java.nio.file.Paths/get path (into-array [""]))
   java.nio.file.attribute.BasicFileAttributes
   (into-array java.nio.file.LinkOption [])))

(defn get-metadata
  "Get metadata from a file

  Returns a hash with some useful information"
  [path]
  (let [attr (get-file-raw-metadata path)]
    (when (nil? attr)
      nil)

    {
     :path path
     :size (. attr size)
     :type (if (true? (. attr isDirectory))
               :directory
               :file)
     :mimetype "application/octet-stream"
     :lastAccessTime (. attr lastAccessTime)
     :lastModifiedTime (. attr lastModifiedTime)
     :etag (+ (. (. attr creationTime) toMillis)
              (. (. attr lastModifiedTime) toMillis))
     :creationTime (. attr creationTime)}))

(defn retrieve-file
  "Retrieve a file from a given URI"
  [uri]
  uri)

(defn retrieve-dir
  "Retrieve a directory from a given URI"
  [uri]
  uri)

(defn put-file
  "Put a file into a given URI"
  [uri]
  uri)

(defn put-dir
  "Put a directory into a given URI"
  [uri]
  uri)
