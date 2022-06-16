(ns app.db
  (:require
   [clojure.spec.gen.alpha :as gen]
   [clojure.spec.alpha :as spec]
   [provisdom.spectomic.core :as spectomic]))

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
(spec/def :vzeit/wochentag string?)
(spec/def :vzeit/start-zeit string?)
(spec/def :vzeit/end-zeit string?)
(spec/def :vzeit/start-datum string?)
(spec/def :vzeit/end-datum string?)
(spec/def :vzeit/rhythmus string?)
(spec/def :vzeit/raum ::raum)

(spec/def ::vzeit (spec/keys :req [:vzeit/id]
                             :opt [:vzeit/start-datum
                                   :vzeit/end-datum
                                   :vzeit/rhythmus
                                   :vzeit/wochentag
                                   :vzeit/start-zeit
                                   :vzeit/raum
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


;; define schema from specs


(def veranstaltungs-schema
  (spectomic/datomic-schema
   [[:veranstaltung/id {:db/unique :db.unique/identity}]
    :veranstaltung/name
    :veranstaltung/SWS
    :veranstaltung/ECTS
    :veranstaltung/max-teilnehmer
    :veranstaltung/typ
    :veranstaltung/semester
    :veranstaltung/studiengang
    :veranstaltung/lehrpersonen
    :veranstaltung/vzeiten]))

(def vzeit-schema
  (spectomic/datascript-schema
   [[:vzeit/id {:db/unique :db.unique/identity}]
    :vzeit/wochentag
    :vzeit/start-zeit
    :vzeit/end-zeit
    :vzeit/start-datum
    :vzeit/end-datum
    :vzeit/rhythmus
    :vzeit/raum]))

(def lehrperson-schema
  (spectomic/datascript-schema
   [[:lehrperson/pers-id {:db/unique :db.unique/identity}]
    :lehrperson/name
    :lehrperson/vorname]))

(def raum-schema
  (spectomic/datascript-schema
   [[:raum/name {:db/unique :db.unique/identity}]
    :raum/form
    :raum/gebaeude]))

#:raum{:name #:db{:cardinality :db.cardinality/one, :unique :db.unique/identity}, :form #:db{:cardinality :db.cardinality/one}, :gebaeude #:db{:cardinality :db.cardinality/one}}

veranstaltungs-schema


(def schema
  (vec (concat
        veranstaltungs-schema
        vzeit-schema
        raum-schema
        lehrperson-schema)))

{:vzeit/end-zeit #:db{:cardinality :db.cardinality/one}, :raum/name #:db{:cardinality :db.cardinality/one, :unique :db.unique/identity}, :lehrperson/vorname #:db{:cardinality :db.cardinality/one}, :vzeit/end-datum #:db{:cardinality :db.cardinality/one}, :db/unique :db.unique/identity, :vzeit/id #:db{:cardinality :db.cardinality/one, :unique :db.unique/identity}, :db/valueType :db.type/ref, :lehrperson/pers-id #:db{:cardinality :db.cardinality/one, :unique :db.unique/identity}, :vzeit/wochentag #:db{:cardinality :db.cardinality/one}, :vzeit/start-datum #:db{:cardinality :db.cardinality/one}, :vzeit/raum #:db{:cardinality :db.cardinality/one, :valueType :db.type/ref}, :lehrperson/name #:db{:cardinality :db.cardinality/one}, :raum/form #:db{:cardinality :db.cardinality/one}, :vzeit/rhythmus #:db{:cardinality :db.cardinality/one}, :raum/gebaeude #:db{:cardinality :db.cardinality/one}, :db/cardinality :db.cardinality/many, :db/ident :veranstaltung/vzeiten, :vzeit/start-zeit #:db{:cardinality :db.cardinality/one}}


{:ident :veranstaltung/id, :valueType :db.type/long, :cardinality :db.cardinality/one, :unique :db.unique/identity}

[#:db #:db{:ident :veranstaltung/name, :valueType :db.type/string, :cardinality :db.cardinality/one} #:db{:ident :veranstaltung/SWS, :valueType :db.type/long, :cardinality :db.cardinality/one} #:db{:ident :veranstaltung/ECTS, :valueType :db.type/long, :cardinality :db.cardinality/one} #:db{:ident :veranstaltung/max-teilnehmer, :valueType :db.type/long, :cardinality :db.cardinality/one} #:db{:ident :veranstaltung/typ, :valueType :db.type/string, :cardinality :db.cardinality/one} #:db{:ident :veranstaltung/semester, :valueType :db.type/string, :cardinality :db.cardinality/one} #:db{:ident :veranstaltung/studiengang, :valueType :db.type/string, :cardinality :db.cardinality/one} #:db{:ident :veranstaltung/lehrpersonen, :cardinality :db.cardinality/many, :valueType :db.type/ref} #:db{:ident :veranstaltung/vzeiten, :cardinality :db.cardinality/many, :valueType :db.type/ref} [:vzeit/id #:db{:cardinality :db.cardinality/one, :unique :db.unique/identity}] [:vzeit/wochentag #:db{:cardinality :db.cardinality/one}] [:vzeit/start-zeit #:db{:cardinality :db.cardinality/one}] [:vzeit/end-zeit #:db{:cardinality :db.cardinality/one}] [:vzeit/start-datum #:db{:cardinality :db.cardinality/one}] [:vzeit/end-datum #:db{:cardinality :db.cardinality/one}] [:vzeit/rhythmus #:db{:cardinality :db.cardinality/one}] [:vzeit/raum #:db{:cardinality :db.cardinality/one, :valueType :db.type/ref}] [:raum/name #:db{:cardinality :db.cardinality/one, :unique :db.unique/identity}] [:raum/form #:db{:cardinality :db.cardinality/one}] [:raum/gebaeude #:db{:cardinality :db.cardinality/one}] [:lehrperson/pers-id #:db{:cardinality :db.cardinality/one, :unique :db.unique/identity}] [:lehrperson/name #:db{:cardinality :db.cardinality/one}] [:lehrperson/vorname #:db{:cardinality :db.cardinality/one}]]

;; (clojure.pprint/pprint (gen/generate (spec/gen ::entities)))
