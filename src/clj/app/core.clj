(ns app.core
  (:require
   [clojure.spec.alpha :as spec]
   [clojure.pprint :refer [pprint]]
   [datahike.api :as d]
   [com.rpl.specter :as s]
   [app.db :as db]
   [app.xml :refer [xml->entities]]))


(def cfg {:store {:backend :file
                  :path "resources/db/hike"}})

(def xml-src (slurp "/home/jan/School/GeheimeDaten.xml"))

(def entities (xml->entities xml-src))

(comment

  (d/create-database cfg)

  (def conn (d/connect cfg))

  (d/transact conn db/schema)

  (d/transact conn entities)

  (d/q '[:find ?id
         :where
         [?v :veranstaltung/name "Stochastik"]
         [?v :veranstaltung/id ?id]]
       @conn)

  )
