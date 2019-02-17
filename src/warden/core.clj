(ns warden.core
  (:gen-class)
  (:require [org.httpkit.server :refer [run-server]])
  (:require clojure.pprint))

(defn create-file
  "Create a WebDAV file"
  [name]
  {:name name
   :type :file})

(defn create-folder
  "Create a WebDAV folder"
  [name]
  {:name name
   :type :collection
   :children []})

(defn get-operation-name
  "Gets the operation from the keyword"
  [op]
  (op {:get "GET"
       :put "PUT"
       :delete "DELETE"
       :get-metadata "PROPFIND"
       :put-metadata "PROPPATCH"
       :lock "LOCK"
       :unlock "UNLOCK"
       :copy "COPY"
       :move "MOVE"
       :create-folder "MKCOL"}))

(defn handler-options [req]
  "Handler for the OPTIONS method.

  Just show a default message for now.
  We might need to check if the referred file exists or not"
  {:status 200
   :headers {"DAV" "1.2"
             "Server" "warden-0.1.0"
             "Allow" "PROPPATCH,PROPFIND,OPTIONS,DELETE,UNLOCK,COPY,LOCK,MOVE"}})

(defn handler-propfind [req]
  "Handler for the PROPFIND method

  Just a placeholder"
  (println "Called PROPFIND")
  {:status 405
   :body "Haha I lied to you"})

(defn handler-get [req]
  "Handler for the GET method

  Just a placeholder"
  (println "Called GET")
  {:status 405
   :body "Haha I lied to you"})

(defn handler-put [req]
  "Handler for the PUT method

  Just a placeholder"
  (println "Called PUT")
  {:status 405
   :body "Haha I lied to you"})

(defn handler-delete [req]
  "Handler for the DELETE method

  Just a placeholder"
  (println "Called DELETE")
  {:status 405
   :body "Haha I lied to you"})

(defn handler-proppatch [req]
  "Handler for the PROPPATCH method

  Just a placeholder"
  (println "Called PROPPATCH")
  {:status 405
   :body "Haha I lied to you"})

(defn handler-lock [req]
  "Handler for the LOCK method

  Just a placeholder"
  (println "Called LOCK")
  {:status 405
   :body "Haha I lied to you"})

(defn handler-unlock [req]
  "Handler for the UNLOCK method

  Just a placeholder"
  (println "Called UNLOCK")
  {:status 405
   :body "Haha I lied to you"})

(defn handler-copy [req]
  "Handler for the COPY method

  Just a placeholder"
  (println "Called COPY")
  {:status 405
   :body "Haha I lied to you"})

(defn handler-move [req]
  "Handler for the MOVE method

  Just a placeholder"
  (println "Called MOVE")
  {:status 405
   :body "Haha I lied to you"})

(defn handler-mkcol [req]
  "Handler for the MKCOL method

  Just a placeholder"
  (println "Called MKCOL")
  {:status 405
   :body "Haha I lied to you"})


(defn get-method-handler-map
  "Get a handler based on the http method"
  [m]
  (m {:get handler-get
      :put handler-put
      :delete handler-delete
      :propfind handler-propfind
      :proppatch handler-proppatch
      :lock handler-lock
      :unlock handler-unlock
      :copy handler-copy
      :move handler-move
      :options handler-options
      :mkcol handler-mkcol}))

(defn process-message [req]
  "Process an incoming network message. Returns an http-kit response"
  ((get-method-handler-map (:request-method req)) req))

(defn app [req]
  (clojure.pprint/pprint req)
  (process-message req))

(defn start-server []
  (let [server (run-server #'app {:port 9090})]
    (println "Server started on port 9090")
    server))

(defn -main
  [& args]
  (start-server))
