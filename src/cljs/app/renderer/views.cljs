(ns app.renderer.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com]
   [app.renderer.subs :as subs]
   [app.renderer.events :as events]))

(defmulti main-panel identity)

(defn- col->array
  [col]
  (-> js/Array
      .-prototype
      .-slice
      (.call col)))

(defn title []
  (let [name (re-frame/subscribe [::subs/application-name])]
    [re-com/title
     :label (str "Welcome to " @name)
     :level :level1]))

(defn button []
  [re-com/button
   :label "Initialize db"
   :on-click #(re-frame/dispatch [::events/initialize-ds])])

(defn root-component []
  (let [panel @(re-frame/subscribe [::subs/active-panel])]
    [re-com/v-box
     :gap "50px"
     :class "container"
     :children
     [[title]
      [button]
      [re-com/border
       :border "1px dashed red"
       :child
       [main-panel panel]]
      [:input
       {:type "file" :id "file" :name "file"
        :on-change
        #(re-frame/dispatch [::events/import-xml (-> % .-target .-files col->array js->clj)])}]]]))

(defn v-item
  [[id]]
  [re-com/title
   :label (str id)])

(defn veranstaltungen-view []
  (let [veranstaltungen @(re-frame/subscribe [::subs/veranstaltungen])]
    [re-com/v-box
     :gap "10px"
     :class "container"
     :children
     [(for [v veranstaltungen]
        [v-item v])]]))

(defmethod main-panel :veranstaltungen [_] [veranstaltungen-view])
