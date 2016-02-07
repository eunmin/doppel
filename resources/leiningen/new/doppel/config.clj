(ns {{namespace}}.config
    (:require [clojure.java.io :as io]))

(defn config [filename]
  (try 
    (let [file (io/resource filename)
          config (slurp file)]
      (read-string config))
    (catch Exception e
      (throw (Exception. "Error reading config file:" filename)))))
