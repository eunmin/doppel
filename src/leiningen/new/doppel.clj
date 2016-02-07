(ns leiningen.new.doppel
  (:require [clojure.java.io :as io]
            [leiningen.new.templates :refer [renderer year project-name
                                             ->files sanitize-ns name-to-path
                                             sanitize
                                             multi-segment]]
            [leiningen.core.main :as main]))

(def render (renderer "doppel"))

(defn resource [name]
  (io/input-stream (io/resource (str "leiningen/new/doppel/" name))))

(defn doppel
  [name]
  (let [main-ns (sanitize-ns name)
        data    {:raw-name    name
                 :name        (project-name name)
                 :sanitize-name (sanitize name)
                 :namespace   main-ns
                 :dirs        (name-to-path main-ns)
                 :year        (year)}] 
    (main/info "Generating fresh 'lein new' doppel project.")
    (->files data
             ["project.clj" (render "project.clj" data)]
             ["dev/user.clj" (render "user.clj" data)]
             ["src/{{dirs}}/main.clj" (render "main.clj" data)]
             ["src/{{dirs}}/service.clj" (render "service.clj" data)]
             ["src/{{dirs}}/db.clj" (render "db.clj" data)]
             ["src/{{dirs}}/config.clj" (render "config.clj" data)]
             ["src/{{dirs}}/router.clj" (render "router.clj" data)]
             ["src/{{dirs}}/handler/home.clj" (render "home.clj" data)]
             "src/{{dirs}}/repository"
             ["resources/config.edn" (render "config.edn" data)]
             ["resources/templates/home/index.html" (resource "index.html")]
             "resources/migrations"
             "resources/public")))
