(ns app.renderer.panels.main-panel
  (:require
   [re-frame.core :as re-frame]
   [app.renderer.subs :as subs]
   [re-com.core :as re-com]
   [app.renderer.panels.veranstaltungs-views :as v]))

(defmulti active-panel first)


(defn main-panel
  []
  (let [panel @(re-frame/subscribe [::subs/active-panel])]
    [active-panel panel]))


(defmethod active-panel :veranstaltung [[_ id]] [v/v-details-view id])

(defmethod active-panel :veranstaltungen [_] [v/veranstaltungen-view])
