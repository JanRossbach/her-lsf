(ns app.core
  (:require
   [clojure.spec.alpha :as spec]
   [clojure.pprint :refer [pprint]]
   [datahike.api :as d]
   [com.rpl.specter :as s]
   [clojure.data.xml :refer [parse]]
   [app.db :as db]
   [app.xml :refer [xml->entities xmlmap->map]]))


(def cfg {:store {:backend :file
                  :path "resources/db/hike"}})

(def conn (d/connect cfg))

(def xml-src (slurp "/home/jan/School/GeheimeDaten.xml"))

(def data (xmlmap->map (parse (java.io.StringReader. (clojure.string/replace xml-src #"\n[ ]*|\r" "")))))

(def entities (xml->entities xml-src))


(def veranstaltung-ids
  '[:find ?id
    :where
    [?n :veranstaltung/id ?id]])


(comment
  (spit "initial_transaction.edn" entities)

  (d/create-database cfg)

  (d/transact conn db/schema)

  (d/transact conn entities)

  (d/q veranstaltung-ids @conn)

  (d/q '[:find ?id
         :where
         [?v :lehrperson/name "Klau"]
         [?v :lehrperson/pers-id ?id]]
       @conn)

  (count (d/q '[:find ?n ?m ?time ?tag ?s
                :where
                [?zeit :vzeit/start-zeit ?time]
                [?zeit2 :vzeit/start-zeit ?time]
                [?zeit :vzeit/wochentag ?tag]
                [?zeit2 :vzeit/wochentag ?tag]
                [?v :veranstaltung/vzeiten ?zeit]
                [?w :veranstaltung/vzeiten ?zeit2]
                [(not= ?v ?w)]
                [?v :veranstaltung/studiengang ?s]
                [?w :veranstaltung/studiengang ?s]
                [?v :veranstaltung/name ?n]
                [?w :veranstaltung/name ?m]]
              @conn)))
