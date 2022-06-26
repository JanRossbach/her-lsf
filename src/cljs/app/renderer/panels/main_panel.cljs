(ns app.renderer.panels.main-panel
  (:require
   [re-frame.core :as re-frame]
   [app.renderer.subs :as subs]
   [app.renderer.events :as events]
   [re-com.core :as re-com]
   [app.renderer.panels.veranstaltungs-views :as v]))

(defmulti active-panel first)

(def tab-definitions
  [{:id :veranstaltungen :label "Veranstaltungen"}])

(defn main-panel
  []
  (let [panel @(re-frame/subscribe [::subs/active-panel])]
    [re-com/v-box
     :gap "14px"
     :children
     [[re-com/horizontal-bar-tabs
       :model panel
       :tabs tab-definitions
       :on-change #(re-frame/dispatch [::events/navigate [%]])]
      [active-panel panel]]]))

(defmethod active-panel :veranstaltung [[_ id]] [v/v-details-view id])
(defmethod active-panel :veranstaltungen [_] [v/veranstaltungen-view])