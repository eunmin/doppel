(ns user
  (:require [clojure.java.io :as io]
            [environ.core :refer [env]]
            [io.pedestal.http :as server]
            [io.pedestal.service-tools.dev :refer [watch]]
            [prone-pedestal.interceptor.exceptions :refer [exceptions]]
            [ragtime.jdbc :as ragtime-jdbc]
            [ragtime.repl :as ragtime]
            [selmer.parser :as selmer]
            [{{namespace}}.config :refer [config]]
            [{{namespace}}.router :as router]
            [{{namespace}}.service :refer [service]]))

(defn- ragtime-config []
  (let [db (:db (config (env :config "config.edn")))]
    {:datastore (ragtime-jdbc/sql-database
                 (str "jdbc:mysql://" (:user db) "@" (:host db) ":" (:port db) "/" (:name db)))
     :migrations (ragtime-jdbc/load-resources "migrations")}))

(defn migrate []
  (ragtime/migrate (ragtime-config)))

(defn rollback []
  (ragtime/rollback (ragtime-config)))

(defn- exception-interceptor [service]
  (update-in service [::server/interceptors] #(vec (cons (exceptions) %))))

(defn run-dev [& args]
  (let [http (:http (config (env :config "config.edn")))
        start-msg (str "\nCreating your [DEV] server... http://" (:host http) ":" (:port http))]
      (println start-msg))
  (selmer/cache-off!)
  (selmer/set-resource-path! (io/resource "templates"))
  (watch)
  (-> service
      (merge {:env :dev
              ::server/join? false
              ::server/routes #(deref #'router/routes)
              ::server/allowed-origins {:creds true :allowed-origins (constantly true)}})
      server/default-interceptors
      (exception-interceptor)
      server/dev-interceptors
      server/create-server
      server/start))
