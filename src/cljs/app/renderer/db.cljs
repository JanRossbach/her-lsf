(ns app.renderer.db
  (:require
   [clojure.spec.gen.alpha :as gen]
   [clojure.test.check.generators]
   [clojure.spec.alpha :as spec]
   [datascript.core :as datascript]
   [re-posh.core :as re-posh]))



;; specs

(spec/def :raum/name string?)
(spec/def :raum/form string?)
(spec/def :raum/gebaeude string?)

(spec/def ::raum (spec/keys :req [:raum/name :raum/form :raum/gebaeude]))


(spec/def :lehrperson/pers-id (spec/and int? #(> % 0)))
(spec/def :lehrperson/name string?)
(spec/def :lehrperson/vorname string?)

(spec/def ::lehrperson (spec/keys :req [:lehrperson/pers-id]
                                  :opt [:lehrperson/name
                                        :lehrperson/vorname]))


;; Vorlesungzeit

(spec/def :vzeit/id (spec/and int? #(> % 0)))
(spec/def :vzeit/wochentag #{"Montag" "Dienstag" "Mittwoch" "Donnerstag" "Freitag" "Samstag" "Sonntag"})
(spec/def :vzeit/start-zeit string?)
(spec/def :vzeit/end-zeit string?)
(spec/def :vzeit/start-datum string?)
(spec/def :vzeit/end-datum string?)
(spec/def :vzeit/rhythmus string?)
(spec/def :vzeit/raum ::raum)
(spec/def :vzeit/markiert? boolean?)

(spec/def ::vzeit (spec/keys :req [:vzeit/id]
                             :opt [:vzeit/start-datum
                                   :vzeit/end-datum
                                   :vzeit/rhythmus
                                   :vzeit/wochentag
                                   :vzeit/start-zeit
                                   :vzeit/raum
                                   :vzeit/markiert?
                                   :vzeit/end-zeit]))


(spec/def :veranstaltung/id (spec/and int? #(> % 0)))
(spec/def :veranstaltung/name string?)
(spec/def :veranstaltung/SWS (spec/and int? #(> % 0)))
(spec/def :veranstaltung/ECTS (spec/and int? #(> % 0)))
(spec/def :veranstaltung/max-teilnehmer (spec/and int? #(> % 1)))
(spec/def :veranstaltung/typ string?)
(spec/def :veranstaltung/semester string?)
(spec/def :veranstaltung/studiengang string?)
(spec/def :veranstaltung/lehrpersonen (spec/coll-of ::lehrperson))
(spec/def :veranstaltung/vzeiten (spec/coll-of ::vzeit))

(spec/def ::veranstaltung (spec/keys :req
                                     [:veranstaltung/id
                                      :veranstaltung/name
                                      :veranstaltung/typ
                                      :veranstaltung/semester
                                      :veranstaltung/lehrpersonen
                                      :veranstaltung/studiengang
                                      :veranstaltung/vzeiten]

                                     :opt
                                     [:veranstaltung/SWS
                                      :veranstaltung/ECTS
                                      :veranstaltung/max-teilnehmer
                                      ]))


(spec/def ::entity (spec/or :raum ::raum
                            :lehrperson ::lehrperson
                            :vzeit ::vzeit
                            :veranstaltung ::veranstaltung))

(spec/def ::entities (spec/coll-of ::entity))

;; Initialize databases

(def schema {
             :raum/name {:db/cardinality :db.cardinality/one, :db/unique :db.unique/identity},
             :vzeit/id {:db/cardinality :db.cardinality/one, :db/unique :db.unique/identity},
             :lehrperson/pers-id {:db/cardinality :db.cardinality/one, :unique :db.unique/identity},
             :vzeit/raum {:db/cardinality :db.cardinality/one, :db/valueType :db.type/ref},
             :veranstaltung/vzeiten {:db/cardinality :db.cardinality/many :db/valueType :db.type/ref}
             :veranstaltung/lehrpersonsn {:db/cardinality :db.cardinality/many}
             })

(def initial-db
  {:application/name "HER-LSF"
   :application/active-panel [:veranstaltungen]
   :application/current-search-term #".*.*"})

(defn gen-entities []
  (gen/generate (spec/gen ::entities)))

;; (def initial-ds (gen-entities))

(def conn (datascript/create-conn schema))
(re-posh/connect! conn)

;; Util Functions

(defn read-db [db]
  (cljs.reader/read-string db))

(defn connect-db!
  [db]
  (let [conn (datascript/conn-from-db (read-db db))]
    (re-posh/connect! conn)))
