(ns app.renderer.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com]
   [app.renderer.subs :as subs]
   [app.renderer.events :as events]
   [app.renderer.panels.main-panel :refer [main-panel]]))


(defn title []
  (let [name (re-frame/subscribe [::subs/application-name])]
    [re-com/title
     :label (str @name)
     :level :level1]))

(defn button []
  [re-com/button
   :label "Initialize ds-mock"
   :on-click #(re-frame/dispatch [::events/initialize-ds])])

(defn root-component []
  [re-com/v-box
   :gap "50px"
   :class "container"
   :children
   [[title]
    [main-panel]
    [button]]])
