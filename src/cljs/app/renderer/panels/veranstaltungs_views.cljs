(ns app.renderer.panels.veranstaltungs-views
  (:require
   [app.renderer.panels.common-components :as common]
   [re-frame.core :as re-frame]
   [re-com.core :as re-com]
   [app.renderer.subs :as subs]
   [app.renderer.events :as events]))



(defn v-item
  [[id]]
  (let [v @(re-frame/subscribe [::subs/veranstaltung-by-id id])
        name (:veranstaltung/name v)]
  [re-com/hyperlink
   :label (str name)
   :on-click
   #(re-frame/dispatch [::events/navigate [:veranstaltung id]])]))

(defn v-details-view
  [id]
  (let [v @(re-frame/subscribe [::subs/veranstaltung-by-id id])
        name (:veranstaltung/name v)]
    [re-com/title
     :label name
     :level :level2]))

(defn veranstaltungs-liste
  [veranstaltungen]
  [re-com/v-box
   :gap "10px"
   :class "container"
   :children
   [(for [v veranstaltungen]
      ^{:key v} [v-item v])]])

(defn veranstaltungen-view
  []
  (let [search-term @(re-frame/subscribe [::subs/search-term])
        veranstaltungen @(re-frame/subscribe [::subs/veranstaltungen search-term])]
    [re-com/v-box
     :gap "10px"
     :class "container"
     :children
     [[common/search-field]
      [veranstaltungs-liste veranstaltungen]
      ]]))
