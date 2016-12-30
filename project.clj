(defproject riemann-tools "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [clj-http "3.4.1"]
                 [cheshire "5.6.3"]
                 [clj-time "0.12.2"]
                 [jarohen/chime "0.2.0"]
                 [riemann-clojure-client "0.4.2"]
                 [com.google.protobuf/protobuf-java "3.1.0"]]
  :main riemann-tools.core)
