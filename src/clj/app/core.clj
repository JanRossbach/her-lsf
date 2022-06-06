(ns app.core
  (:require
   [dk.ative.docjure.spreadsheet :as excel]
   [clojure.data.xml :refer [parse]]
   [clojure.pprint :refer [pprint]]
   [com.rpl.specter :as s]))

;;(excel/load-workbook "resources/db/GeheimeDaten.xlsx")


(def input-xml (slurp "resources/db/GeheimeDaten.xml"))

(def filtered-xml (clojure.string/replace input-xml #"\n[ ]*|\r" "")) ;; remove whitespace

(pprint (apply str (take 200 filtered-xml)))

(def reader (java.io.StringReader. filtered-xml))

(def output-map (parse reader))

(pprint output-map)

(first (:content output-map))

(pprint filtered-xml)
