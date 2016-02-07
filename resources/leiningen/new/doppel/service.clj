(ns {{namespace}}.service
    (:require [environ.core :refer [env]]
              [io.pedestal.http :as server]
              [{{namespace}}.router :as router]))

(def service {:env :prod
              ::server/routes router/routes
              ::server/resource-path "/public"
              ::server/type :immutant
              ::server/port (env :port 8080)})
