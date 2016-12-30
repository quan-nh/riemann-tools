(ns riemann-tools.core
  (:gen-class)
  (:require [chime :refer [chime-at]]
            [clojure.string :as str]
            [clojure.tools.cli :refer [parse-opts]]
            [clj-time.core :as t]
            [clj-time.periodic :refer [periodic-seq]]
            [riemann-tools.solr :as solr])
  (:import (java.net InetAddress)))

(def cli-options
  [["-h" "--host HOST" "Riemann host" :default "127.0.0.1"]
   ["-p" "--port PORT" "Riemann port" :default 5555 :parse-fn #(Integer/parseInt %)]
   ["-e" "--event-host EVENT_HOST" "Event hostname" :default (InetAddress/getByName "localhost")]
   ["-s" "--solr-url SOLR_URL" "Solr url" :default "http://localhost:8983/solr"]
   ["-i" "--interval INTERVAL" "Minutes between updates" :default 5 :parse-fn #(Integer/parseInt %)]
   ["-m" "--timeout TIMEOUT" "Timeout (in seconds) when waiting for acknowledgements" :default 30 :parse-fn #(Integer/parseInt %)]
   [nil "--help" "Show this message"]])

(defn usage [options-summary]
  (->> [""
        "Usage: program-name [options] action"
        ""
        "Options:"
        options-summary
        ""
        "Actions:"
        "  solr    send Solr stats to Riemann"
        ""]
       (str/join \newline)))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (str/join \newline errors)))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)]
    ;; Handle help and error conditions
    (cond
      (:help options) (exit 0 (usage summary))
      (not= (count arguments) 1) (exit 1 (usage summary))
      errors (exit 1 (error-msg errors)))
    ;; Execute program with options
    (case (first arguments)
      "solr" (chime-at (periodic-seq (t/now)
                                     (-> (:interval options) t/minutes))
                       (fn [time]
                         (println "chime at" time)
                         (solr/status options)))
      (exit 1 (usage summary))))
  @(promise))
