(ns app.main.db
  (:require
   ["fs" :as fs]))

(def db-file "resources/db/db.edn")

(defn save-db [event db-val]
  (.writeFileSync fs db-file db-val))

(defn load-db []
  (.readFileSync fs db-file))
