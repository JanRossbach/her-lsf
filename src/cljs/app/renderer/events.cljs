(ns app.renderer.events
  (:require
   [re-frame.core :as re-frame]
   [re-posh.core :as re-posh]
   [jtk-dvlp.re-frame.readfile-fx]
   [app.renderer.db :as database]))


(defn- col->array
  [col]
  (-> js/Array
      .-prototype
      .-slice
      (.call col)))

(re-posh/reg-event-ds
 ::initialize-db
 (fn [_ _]
   database/initial-db))

(re-frame/reg-event-fx
 ::xml-import-read-success
 (fn [_ [_ result]]
   (.log js/console "success" (clj->js result))))

(re-frame/reg-event-fx
 ::xml-import-read-error
 (fn [_ [_ result]]
   (.log js/console "error" (clj->js result))))

(re-frame/reg-event-fx
 ::import-xml
 (fn [_ [_ files]]
   {:readfile
    {:files files
     :charsets nil
     :on-success [::xml-import-read-success]
     :on-error [::xml-import-read-error]}}))
