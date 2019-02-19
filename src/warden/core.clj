(ns warden.core
  (:gen-class)
  (:require [org.httpkit.server :refer [run-server]])
  (:require clojure.pprint)
  (:refer warden.handlers))

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
