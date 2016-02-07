(ns {{namespace}}.handler.home
    (:require [ring.util.response :refer [response]]
              [selmer.parser :refer [render-file]]))

(defn index [request]
  (response (render-file "home/index.html" {:message "Hello World"})))

