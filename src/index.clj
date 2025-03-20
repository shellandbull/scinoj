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

^:kindly/hide-code
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


^:kindly/hide-code
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

(defn bitcoin-1-row
  (-> bitcoin
      (tc/head 1)))

;; # A glimpse into the price of Bitcoin, a [scinoj data analysis](https://scicloj.github.io/docs/community/groups/scinoj-light/)


^:kindly/hide-code
(kind/image
 {:src "https://static.vecteezy.com/system/resources/previews/019/767/953/non_2x/bitcoin-logo-bitcoin-icon-transparent-free-png.png"
  })

(-> bitcoin
    (tc/head 1000)
    (plotly/layer-line
     {:=x :date
      :=y :open
      :=mark-color "blue"}))

;; This notebook was built using the [noj stack on Clojure](https://scicloj.github.io/noj/)

;; [tablecloth](https://github.com/scicloj/tablecloth) was used to manipulate the dataset, which was downloaded from [Kaggle](https://www.kaggle.com/datasets/mczielinski/bitcoin-historical-data)

;; # Why Bitcoin?
;;
;; Bitcoin's price history *follows a hockey stick pattern*, characterized by long periods of
;; stability followed by sudden, dramatic increases. This unique behavior makes it an
;; intriguing subject for economic study, offering insights into market psychology,
;; technological adoption, and the dynamics of emerging asset classes.
;;
;; As a *borderless* currency, Bitcoin transcends traditional financial boundaries,
;; making it a compelling case study in global economics. Its price movements reflect
;; a complex interplay of international factors, from regulatory changes to geopolitical
;; events, providing a rich dataset for analyzing cross-border financial flows and the
;; evolving nature of money in a digital age.
;;
;; Bitcoin's *private* nature, while not absolute, offers a degree of anonymity that
;; sets it apart from conventional financial systems. Studying its price can shed light
;; on how privacy features influence asset valuation and adoption, as well as the
;; broader implications for financial privacy in an increasingly digital world.

;; ## Is it worth it?

;; There isn't a deterministic answer. However we will borrow some principles from [decision theory](https://en.wikipedia.org/wiki/Decision_theory) to work out if it's *mostly* worth it.

;; We will plot the decision by basing it through the lense of [the secretary problem](https://en.wikipedia.org/wiki/Secretary_problem). A famous scenario that demonstrates [optimal stopping theory](https://en.wikipedia.org/wiki/Optimal_stopping)

;; - *One-time purchase*: You're limited to a single bitcoin buy.
;; - *Minute-by-minute decisions*: Each minute offers a buy-or-pass choice.
;; - *No time travel*: Once a moment passes, it's gone for good.
;; - *Unpredictable future*: The number of upcoming price changes is unknown.
;; - *No crystal ball*: Future prices can't be foreseen.
;; - *Hindsight only*: Decisions must rely on past pricing data, not future info.
;; - *Probability over perfection*: The aim is to maximize the chance of picking
;;   the right moment to buy, not necessarily nabbing the absolute best price.

;; ## The plan

;; To create the study I am going to add a few columns to the current dataset, these will help me
;; plot a good foundation. If it looks good we will overlay the theorem onto the existing dataset

;; - What percentage of our data points would have led to profit if we had:
;;   - Bought and held for:
;;     - 1 day
;;     - 1 month
;;     - 3 months
;;     - 6 months
;;     - 12 months
;;     - 14 months
;;
;; - We'll calculate profit margins for each scenario.
;;
;; - We'll also touch on optimal stopping theory (without modeling it).
;;

(defn find-by-future-time-in-minutes
  "Locates a future row in the timeseries based on a given time delta.

  Parameters:
  - row: A tablecloth row representing the current point in time.
  - minutes: The number of minutes to look ahead in the timeseries.

  Returns:
  A tablecloth row corresponding to the future time point.

  Example:
  (find-by-future-time-in-minutes current-row 60)
  ;; Returns the row 1 hour in the future from the current row.

  Note:
  This function assumes the timeseries data is sorted chronologically
  and contains a timestamp column. If no matching future row is found,
  it may return nil or throw an exception (implementation-dependent)."
  [dataset]
  (fn [row minutes] (
                     ;; TODO
                     )))

(defn add-row-as-column
  "Adds row as a column by the name of label"
  [label row])

(defn add-profit-as-column-to-row
  "
    Adds a column named `label` to `row` by computing the difference between the column
    `open` and the column inside `column-selector`.`open`
  "
  [label row column-selector])

;; We now proceed to extend our dataset to append the right columns. We'll do a small run with a single row as a sample
