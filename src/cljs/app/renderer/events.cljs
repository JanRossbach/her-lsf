(ns app.renderer.events
  (:require
   [re-frame.core :as re-frame]
   [re-posh.core :as re-posh]
   [app.renderer.db :as database]))

(re-posh/reg-event-ds
 ::initialize-db
 (fn [_ _]
   database/initial-db))

(re-posh/reg-event-ds
 ::add-hello
 (fn [_ _]
   [[:db/add :application/name "Heinz"]]))

(re-frame/reg-event-fx
 :print-file
 (fn [_ [_ file]]
   {:readfile
    {:files [file]
     :charset nil
     :on-success [::add-hello]}}))
