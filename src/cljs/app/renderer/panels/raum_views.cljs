(ns app.renderer.panels.raum-views
  (:require
   [re-posh.core :as re-posh]
   [re-frame.core :as re-frame]
   [app.renderer.panels.raum-calender :refer [gantt-chart-demo]]
   [app.renderer.common :as common]
   [re-com.core :as re-com]
   [app.renderer.subs :as subs]
   [app.renderer.events :as events]))

(defn r-item
  [[id name]]
  [re-com/hyperlink
   :label (str name)
   :on-click #(re-frame/dispatch [::events/navigate [:raum id]])])

(defn z-item
  [id]
  (let [zeit @(re-frame/subscribe [::subs/vzeit id])
        wochentag (:vzeit/wochentag zeit)
        start (:vzeit/start-zeit zeit)
        markiert (:vzeit/markiert? zeit)]
    [re-com/p (str "Belegt am " wochentag " um " start (if markiert " check" " uncheck"))]))

(defn vzeiten-list
  [zeiten-ids]
  [re-com/h-box
   :gap common/gap-size
   :class common/box-class
   :children
   [
    (for [z zeiten-ids]
      ^{:key z} [z-item z])]])

(defn r-details
  [id]
  (let [r @(re-frame/subscribe [::subs/raum id])
        zeiten @(re-frame/subscribe [::subs/raum-zeiten id])
        name (:raum/name r)]
    [re-com/v-box
     :gap common/gap-size
     :class common/box-class
     :children
     [[re-com/title
       :label (str name)
       :level :level2]
      ;[vzeiten-list zeiten]
      [gantt-chart-demo]
      [re-com/gap :size common/big-gap-size]
      [common/back-button [:raeume]]]]))

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
      [re-com/gap :size common/big-gap-size]
      [raum-liste raueme]]]))
