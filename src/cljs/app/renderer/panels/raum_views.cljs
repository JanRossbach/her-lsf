(ns app.renderer.panels.raum-views
  (:require
   [re-frame.core :as re-frame]
   [app.renderer.panels.common-components :as common]
   [re-com.core :as re-com]
   [app.renderer.subs :as subs]
   [app.renderer.events :as events]))

(defn r-item
  [[id name]]
  [re-com/hyperlink
   :label (str name)
   :on-click #()])

(defn raum-liste
  [raueme]
  [re-com/v-box
   :gap "10px"
   :class "container"
   :children
   [(for [r raueme]
      ^{:key r} [r-item r])]])

(defn raum-overview
  []
  (let [search-term @(re-frame/subscribe [::subs/search-term])
        raueme @(re-frame/subscribe [::subs/raueme search-term])]
    [re-com/v-box
     :gap "10px"
     :class "container"
     :children
     [[common/search-field]
      [raum-liste raueme]]]))
