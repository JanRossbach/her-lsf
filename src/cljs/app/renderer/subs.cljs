(ns app.renderer.subs
  (:require
   [re-posh.core :as re-posh]
   [re-frame.core :as re-frame]))

;; Home subs

(re-posh/reg-query-sub
 ::name
 '[:find ?name .
   :where [_ :application/name ?name]])

(re-frame/reg-sub
 ::application-name
 (fn [db _]
   (:application/name db)))

;; main-panel subs

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:application/active-panel db)))

;; veranstaltugen-panel-subs

(re-frame/reg-sub
 ::v-search-term
 (fn [db _]
   (:veranstaltungen/current-search-term db)))

(re-posh/reg-query-sub
 ::veranstaltungen
 '[:find ?id ?name
   :in $ ?search-term
   :where
   [?id :veranstaltung/name ?name]
   [(re-matches ?search-term ?name)]])

(re-posh/reg-sub
 ::veranstaltung-by-id
 (fn [_ [_ id]]
   {:type :pull
    :pattern '[:veranstaltung/name]
    :id id}))
