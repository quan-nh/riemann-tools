(ns riemann-tools.solr
  (:require [clj-http.client :as http]
            [riemann.client :as r]))

(defn cores-status [solr-url]
  (-> (http/get (str solr-url "/admin/cores?action=STATUS&wt=json")
                {:as :json})
      (get-in [:body :status])
      (vals)))

(defn ->event [index host]
  [{:host host :service (str "solr_" (:name index) "_numDocs") :metric (get-in index [:index :numDocs]) :state "ok" :tags ["solr"]}
   {:host host :service (str "solr_" (:name index) "_maxDocs") :metric (get-in index [:index :maxDoc]) :state "ok" :tags ["solr"]}
   {:host host :service (str "solr_" (:name index) "_delDocs") :metric (get-in index [:index :deletedDocs]) :state "ok" :tags ["solr"]}
   {:host host :service (str "solr_" (:name index) "_sizeInBytes") :metric (get-in index [:index :sizeInBytes]) :state "ok" :tags ["solr"]}])

(defn ->events [cores host]
  (mapcat #(->event % host) cores))

(defn status
  "Send Solr Cores status to riemann server"
  [{:keys [host port event-host solr-url timeout]}]
  (let [c (r/tcp-client {:host host :port port})
        cores (cores-status solr-url)]
    (-> c (r/send-events (conj (->events cores event-host)
                               {:host event-host :service "solr_numCores" :state "ok" :metric (count cores) :tags ["solr"]}))
        (deref (* timeout 1000) ::timeout))
    (r/close! c)))
