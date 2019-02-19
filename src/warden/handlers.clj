(ns warden.handlers
  (:gen-class))

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
