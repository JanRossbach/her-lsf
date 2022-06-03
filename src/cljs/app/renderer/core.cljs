(ns app.renderer.core
  (:require
   [re-posh.core :as re-posh]
   [reagent.dom :as rd]
   [app.renderer.views :refer [root-component]]
   [app.renderer.events :as events]))

(enable-console-print!)


(defn ^:dev/after-load start! []
  ;(re-posh/clear-subsription-cache!)
  (re-posh/dispatch-sync [::events/initialize-db])
  (rd/render
   [root-component]
   (js/document.getElementById "app-container")))
