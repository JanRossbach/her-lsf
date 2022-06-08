(ns app.core
  (:require
   [dk.ative.docjure.spreadsheet :as excel]
   [clojure.data.xml :refer [parse]]
   [clojure.pprint :refer [pprint]]
   [com.rpl.specter :as s]))

;;(excel/load-workbook "resources/db/GeheimeDaten.xlsx")


(def input-xml (slurp "/home/jan/School/GeheimeDaten.xml"))

(def filtered-xml (clojure.string/replace input-xml #"\n[ ]*|\r" "")) ;; remove whitespace

(pprint (apply str (take 200 filtered-xml)))

(def reader (java.io.StringReader. filtered-xml))

(def output-map (parse reader))

(pprint output-map)

(first (:content output-map))

(pprint filtered-xml)

(defn xmlmap->map
  "Recursively translates a given clojure map in xml format given by data.xml
  to a map that uses the tag as key and the content as value. Attributes are ignored."
  [xmlmap]
  (if (map? xmlmap)
    (let [{:keys [tag content]} xmlmap
          content-map (map xmlmap->map content)]
      {tag (if (= 1 (count content-map)) (first content-map) content-map)})
    xmlmap))

(def res (xmlmap->map output-map))

(pprint res)
