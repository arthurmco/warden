(defproject warden "0.1.0-SNAPSHOT"
  :description "easy-configurable WebDAV server"
  :url "http://github.com/arthurmco/warden/"
  :license {:name "MIT"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [http-kit "2.3.0"]]
  :main ^:skip-aot warden.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
