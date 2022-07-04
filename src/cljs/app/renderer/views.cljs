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

(defn- col->array
  [col]
  (-> js/Array
      .-prototype
      .-slice
      (.call col)))

(defn import-xml-button
  []
  [:input
   {:type "file" :id "file" :name "file"
    :on-change
    #(re-frame/dispatch [::events/import-xml (-> % .-target .-files col->array js->clj)])}])

(defn button-row []
  [re-com/h-box
   :gap "10px"
   :children
   [[re-com/button
    :label "Add more mock data"
    :on-click #(re-frame/dispatch [::events/initialize-ds])]
  ;; [import-xml-button]
   ]])

(defn root-component []
  [re-com/v-box
   :gap "50px"
   :class "container"
   :children
   [[title]
    [main-panel]
    [button-row]]])
