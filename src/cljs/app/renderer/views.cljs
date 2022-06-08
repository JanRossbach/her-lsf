(ns app.renderer.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com]
   [app.renderer.subs :as subs]
   [app.renderer.events :as events]))

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
   :label "Save DB"
   :on-click #(re-frame/dispatch [::events/save-db])])

(defn root-component []
  [re-com/v-box
   :gap "50px"
   :class "container"
   :children
   [[title]
    [button]
    [re-com/border
     :border "1px dashed red"
     :child
     [:div.logos
      [:img.electron {:src "img/electron-logo.png"}]
      [:img.cljs {:src "img/cljs-logo.svg"}]
      [:img.reagent {:src "img/reagent-logo.png"}]]]
    [:input
     {:type "file" :id "file" :name "file"
      :on-change
      #(re-frame/dispatch [::events/import-xml (-> % .-target .-files col->array js->clj)])}]]])
