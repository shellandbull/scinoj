(ns build-book
  (:require
   [aerial.hanami.common :as hc]
   [scicloj.clay.v2.api :as clay]))

(defn build []
  (swap! hc/_defaults
         assoc
         :BACKGROUND "white")
  (clay/make!
   {:show             false
    :run-quarto       true
    :format           [:quarto :html]
    :book             {:title "Scinoj | Bitcoin & optimal stopping theory"}
    :base-source-path "src"
    :base-target-path "docs"
    :subdirs-to-sync  ["src", "data"]
    :source-path      ["index.clj"]}))

(build)
(portal.runtime.jvm.launcher/stop)
