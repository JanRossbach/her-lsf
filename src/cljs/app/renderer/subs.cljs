(ns app.renderer.subs
  (:require
   [re-posh.core :as re-posh]
))

(re-posh/reg-query-sub
 ::name
 '[:find ?name .
   :where [_ :application/name ?name]])

(re-posh/reg-sub
 ::label
 (fn [db]
   (:hello db)))

(re-posh/reg-sub
 ::question
 (fn [db _]
   (:question db)))
