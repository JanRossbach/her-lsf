(ns app.renderer.panels.veranstaltungs-views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com]
   [app.renderer.subs :as subs]
   [app.renderer.events :as events]))

(defn v-search-field
  []
  [re-com/input-text
   :model ""
   :on-change #(re-frame/dispatch [::events/set-v-search-term %])
   :placeholder "Search"
   :change-on-blur? true])


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

(defn veranstaltungen-view
  []
  (let [search-term @(re-frame/subscribe [::subs/v-search-term])
        pattern (re-pattern (str ".*" search-term ".*"))
        veranstaltungen @(re-frame/subscribe [::subs/veranstaltungen pattern])]
    [re-com/v-box
     :gap "10px"
     :class "container"
     :children
     [[v-search-field]
      (for [v veranstaltungen]
        ^{:key v} [v-item v])]]))
