(ns app.renderer.panels.veranstaltungs-views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com]
   [app.renderer.subs :as subs]
   [app.renderer.events :as events]))

(defn v-item
  [[id]]
  [re-com/hyperlink
   :label (str id)
   :on-click
   #(re-frame/dispatch [::events/navigate [:veranstaltung id]])])

(defn v-details-view
  [id]
  (let [v @(re-frame/subscribe [::subs/veranstaltung-by-id id])
        name (:veranstaltung/name v)]
    [re-com/title
     :label name
     :level :level2]))

(defn veranstaltungen-view
  []
  (let [veranstaltungs-ids @(re-frame/subscribe [::subs/veranstaltungen])]
    [re-com/v-box
     :gap "10px"
     :class "container"
     :children
     [(for [v-id veranstaltungs-ids]
        ^{:key v-id} [v-item v-id])]]))
