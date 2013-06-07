(defproject
  chat
  "0.1.0-SNAPSHOT"
  :dependencies
  [[org.clojure/clojure "1.5.1"]
   [lib-noir "0.6.0"]
   [compojure "1.1.5"]
   [ring-server "0.2.8"]
   [clabango "0.5"]
   [com.taoensso/timbre "1.6.0"]
   [com.postspectacular/rotor "0.1.0"]
   [com.taoensso/tower "1.5.1"]
   [markdown-clj "0.9.25"]
   [org.clojure/java.jdbc "0.2.3"]
   [com.h2database/h2 "1.3.170"]
   [korma "0.3.0-RC5"]
   [clj-time "0.3.7"]
   [log4j
    "1.2.15"
    :exclusions
    [javax.mail/mail
     javax.jms/jms
     com.sun.jdmk/jmxtools
     com.sun.jmx/jmxri]]]
  :ring
  {:handler chat.handler/war-handler,
   :init chat.handler/init,
   :destroy chat.handler/destroy}
  :profiles
  {:production
   {:ring
    {:open-browser? false, :stacktraces? false, :auto-reload? false}},
   :dev
   {:dependencies [[ring-mock "0.1.3"] [ring/ring-devel "1.1.8"]]}}
  :url
  "http://localhost:8000/"
  :plugins
  [[lein-ring "0.8.5"]]
  :description
  "Simple chat with clojure and luminus"
  :min-lein-version "2.0.0")
