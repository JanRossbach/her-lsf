(ns app.renderer.subs
  (:require
   [re-posh.core :as re-posh]
   [re-frame.core :as re-frame]))

(re-posh/reg-query-sub
 ::name
 '[:find ?name .
   :where [_ :application/name ?name]])

(re-frame/reg-sub
 ::application-name
 (fn [db _]
   (:application/name db)))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:application/active-panel db)))

(re-posh/reg-query-sub
 ::veranstaltungen
 '[:find ?id
   :where [?id :veranstaltung/id _]])
