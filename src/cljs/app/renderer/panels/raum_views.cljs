(ns app.renderer.panels.raum-views
  (:require
   [re-frame.core :as re-frame]
   [app.renderer.common :as common]
   [re-com.core :as re-com]
   [app.renderer.subs :as subs]
   [app.renderer.events :as events]))

(defn r-item
  [[id name]]
  [re-com/hyperlink
   :label (str name)
   :on-click #(re-frame/dispatch [::events/navigate [:raum id]])])

(defn r-details
  [id]
  (let [r @(re-frame/subscribe [::subs/raum id])
        belegt? (:raum/belegt? r)
        name (:raum/name r)]
    [re-com/v-box
     :gap common/gap-size
     :class common/box-class
     :children
     [[re-com/title
       :label (str name)
       :level :level2]
      [re-com/title
       :label (if belegt?
                "Der Raum ist zur Zeit belegt :("
                "Der Raum ist derzeit frei :)")
       :level :level3]
      [common/back-button [:raeume]]
      ]]))

(defn raum-liste
  [raueme]
  [re-com/v-box
   :gap common/gap-size
   :class common/box-class
   :children
   [(for [r raueme]
      ^{:key r} [r-item r])]])

(defn raum-overview
  []
  (let [search-term @(re-frame/subscribe [::subs/search-term])
        raueme @(re-frame/subscribe [::subs/raueme search-term])]
    [re-com/v-box
     :gap common/gap-size
     :class common/box-class
     :children
     [[common/search-field]
      [raum-liste raueme]]]))
