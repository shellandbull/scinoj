^:kindly/hide-code
(ns index
  (:require [scicloj.tableplot.v1.plotly :as plotly]
            [tablecloth.api :as tc]
            [tablecloth.column.api :as tcc]
            [tech.v3.tensor :as tensor]
            [scicloj.kindly.v4.kind :as kind]
            [scicloj.kindly.v4.api :as kindly]
            [scicloj.tableplot.v1.dag :as dag]
            [clojure.string :as str]
            [clj-http.client :as client]
            [clojure.data.json :as json]
            [aerial.hanami.common :as hc]
            [aerial.hanami.templates :as ht]
            [clojure.math :as math]))

^:kindly/hide-code
(import '[java.time Instant ZoneId ZonedDateTime])

^:kindly/hide-code
(def columns-mapping
  {
   :Timestamp :timestamp
   :Open      :open
   :High      :high
   :Low       :low
   :Close     :close
   :Volume    :volume
   })


(defn parse-date [timestamp-str]
  (let [timestamp (Double/parseDouble timestamp-str)
        instant (Instant/ofEpochSecond (long timestamp))
        zdt     (.atZone instant (ZoneId/of "UTC"))]
    {:year      (.getYear zdt)
     :month     (.getMonthValue zdt)
     :day       (.getDayOfMonth zdt)
     :hour      (.getHour zdt)
     :min       (.getMinute zdt)
     :as-string (-> instant
                    (.atZone (java.time.ZoneId/of "UTC"))
                    (.format (java.time.format.DateTimeFormatter/ofPattern "yyyy-MM-dd HH:mm:ss")))
     :original timestamp-str}))


(def bitcoin
  (-> "data/btcusd_1-min_data.csv"
      (tc/dataset {
                   :key-fn keyword
                   :parser-fn {:Timestamp [:object parse-date]}
                   })
      (tc/rename-columns columns-mapping)
      (tc/map-columns :year :timestamp :year)
      (tc/map-columns :month :timestamp :month)
      (tc/map-columns :day :timestamp :day)
      (tc/map-columns :hour :timestamp :hour)
      (tc/map-columns :min :timestamp :min)
      (tc/map-columns :date :timestamp :as-string)))

(tc/head bitcoin 10)

(-> bitcoin
;;    (tc/head 1000)
    (plotly/layer-line
     {:=x :date
      :=y :open
      :=mark-color "blue"}))
