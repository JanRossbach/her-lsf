(ns app.renderer.events
  (:require
   [clojure.string :refer [replace]]
   [clojure.data.xml :as xml]
   [re-frame.core :as re-frame]
   [re-posh.core :as re-posh]
   [jtk-dvlp.re-frame.readfile-fx]
   [app.renderer.db :as database]))

;; ELECTRON STUFF

;; (def electron (js/require "electron"))
;; (def ipcRenderer (.-ipcRenderer electron))

;; (re-frame/reg-fx
;;  :ipc
;;  (fn [{:keys [event args]}]
;;    (.send ipcRenderer event args)))

;; (re-posh/reg-event-fx
;;  ::save-db
;;  [(re-posh/inject-cofx :ds)]
;;  (fn [{:keys [ds]} _]
;;    {:ipc
;;     {:event "save-db"
;;      :args (pr-str ds)}}))

;; (re-frame/reg-event-fx
;;  ::load-db
;;  (fn [_ [_ value]]
;;    (database/connect-db! value)))

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
   (database/gen-entities)))

(re-frame/reg-event-fx
 ::xml-import-read-success
 (fn [_ [_ result]]
   (-> result
       first
       :content
       (replace #"\n [ ]*|\r" "")
       xml/parse-str)))

(re-frame/reg-event-fx
 ::xml-import-read-error
 (fn [_ [_ result]]
   (.log js/console "Error while parsing xml file" (clj->js result))))

(re-frame/reg-event-fx
 ::import-xml
 (fn [_ [_ files]]
   {:readfile
    {:files files
     :charsets nil
     :on-success [::xml-import-read-success]
     :on-error [::xml-import-read-error]}}))

(re-frame/reg-event-db
 ::set-search-term
 (fn [db [_ st]]
   (assoc db :application/current-search-term st)))

(re-frame/reg-event-fx
 ::navigate
 (fn [{:keys [db]} [_ new-panel]]
   {:db (assoc db :application/active-panel new-panel)
    :dispatch [::set-search-term #".*.*"]}))
