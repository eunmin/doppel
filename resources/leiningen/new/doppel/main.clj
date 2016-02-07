(ns {{namespace}}.main
    (:gen-class)
    (:require [clojure.java.io :as io]
              [io.pedestal.http :as server]
              [selmer.parser :as selmer]
              [{{namespace}}.service :refer [service]]))

(defn -main [& args]
  (println "\nCreating your server...")
  (selmer/cache-on!)
  (selmer/set-resource-path! (io/resource "templates"))
  (let [server (server/create-server service)]
    (server/start server)))
