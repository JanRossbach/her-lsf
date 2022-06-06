(ns app.renderer.events
  (:require
   [app.renderer.db :refer [connect-db!]]
   [re-frame.core :as re-frame]
   [re-posh.core :as re-posh]
   [jtk-dvlp.re-frame.readfile-fx]
   [app.renderer.db :as database]))

;; ELECTRON STUFF

(def electron (js/require "electron"))
(def ipcRenderer (.-ipcRenderer electron))

(re-frame/reg-fx
 :ipc
 (fn [{:keys [event args]}]
   (.send ipcRenderer event args)))

(re-posh/reg-event-fx
 ::save-db
 [(re-posh/inject-cofx :ds)]
 (fn [{:keys [ds]} _]
   {:ipc
    {:event "save-db"
     :args (pr-str ds)}}))

(re-frame/reg-event-fx
 ::load-db
 (fn [_ [_ value]]
   (connect-db! value)))

;; END


(defn- col->array
  [col]
  (-> js/Array
      .-prototype
      .-slice
      (.call col)))

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   database/initial-db))

(re-posh/reg-event-ds
 ::initialize-ds
 (fn [_ _]
   database/initial-ds))

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
