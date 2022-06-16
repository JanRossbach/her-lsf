(ns app.renderer.core
  (:require
   [re-posh.core :as re-posh]
   [re-frame.core :as re-frame]
   [reagent.dom :as rd]
   [app.renderer.views :refer [root-component]]
   [app.renderer.subs :as subs]
   [app.renderer.events :as events]))

(enable-console-print!)

;; (def electron (js/require "electron"))
;; (def ipcRenderer (.-ipcRenderer electron))

(defonce init
  (do
    ;; (.on ipcRenderer "load-db" (fn [_ value]
    ;;                              (re-frame/dispatch [::events/load-db value])))
    (re-frame/dispatch-sync [::events/initialize-db])
    (re-posh/dispatch-sync [::events/initialize-ds])
    ))


(defn ^:dev/after-load start! []
  ;(re-posh/clear-subsription-cache!)
  (rd/render
   [root-component]
   (js/document.getElementById "app-container")))
