(defproject {{raw-name}} "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [io.pedestal/pedestal.service "0.4.1"]
                 [io.pedestal/pedestal.immutant "0.4.1"]
                 [environ "1.0.2"]
                 [prone "1.0.2"]
                 [prone-pedestal "0.1.2"]
                 [ragtime "0.5.2"]
                 [korma "0.4.2"]
                 [org.clojure/java.jdbc "0.4.1"]
                 [mysql/mysql-connector-java "5.1.34"]
                 [selmer "1.0.0"]
                 [ch.qos.logback/logback-classic "1.1.3" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.12"]
                 [org.slf4j/jcl-over-slf4j "1.7.12"]
                 [org.slf4j/log4j-over-slf4j "1.7.12"]]
  :profiles {:dev {:aliases {"run-dev" ["trampoline" "run" "-m" "user/run-dev"]
                             "migrate" ["run" "-m" "user/migrate"]
                             "rollback" ["run" "-m" "user/rollback"]
                             "migrate-gen" "ragtime-gen"}
                   :repl-options {:init-ns user}
                   :source-paths ["dev"]
                   :plugins [[kwakbab/lein-ragtime-gen "0.0.1"]]
                   :dependencies [[io.pedestal/pedestal.service-tools "0.4.1"]]
                   :migration-path "resources/migrations"}
             :uberjar {:aot :all}}
  :uberjar-name "{{name}}.jar"
  :main ^{:skip-aot true} {{namespace}}.main)
